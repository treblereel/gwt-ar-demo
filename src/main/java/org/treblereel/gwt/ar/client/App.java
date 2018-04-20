package org.treblereel.gwt.ar.client;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;
import org.treblereel.gwt.ar.client.api.ArMarkerControls;
import org.treblereel.gwt.ar.client.api.ArToolkitContext;
import org.treblereel.gwt.ar.client.api.ArToolkitContextProperty;
import org.treblereel.gwt.ar.client.api.ArToolkitSource;
import org.treblereel.gwt.ar.client.api.ColladaLoader;
import org.treblereel.gwt.ar.client.api.OnCompleted;
import org.treblereel.gwt.ar.client.api.OnReady;
import org.treblereel.gwt.ar.client.resources.JavascriptTextResource;
import org.treblereel.gwt.three4g.Constants;
import org.treblereel.gwt.three4g.cameras.Camera;
import org.treblereel.gwt.three4g.core.Color;
import org.treblereel.gwt.three4g.core.Object3D;
import org.treblereel.gwt.three4g.lights.AmbientLight;
import org.treblereel.gwt.three4g.lights.DirectionalLight;
import org.treblereel.gwt.three4g.lights.Light;
import org.treblereel.gwt.three4g.loaders.OnLoadCallback;
import org.treblereel.gwt.three4g.materials.Material;
import org.treblereel.gwt.three4g.objects.Group;
import org.treblereel.gwt.three4g.objects.Mesh;
import org.treblereel.gwt.three4g.renderers.WebGLRenderer;
import org.treblereel.gwt.three4g.renderers.WebGLRendererParameters;
import org.treblereel.gwt.three4g.scenes.Scene;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import static elemental2.dom.DomGlobal.document;
import static elemental2.dom.DomGlobal.window;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class App implements EntryPoint {

    private Logger logger = Logger.getLogger(App.class.getSimpleName());

    private WebGLRenderer renderer;
    private Scene scene;
    private Camera camera;
    private ArToolkitSource  arToolkitSource;
    private ArToolkitContext arToolkitContext;
    private ArMarkerControls artoolkitMarker;
    private Group markerRoot;
    private Object3D tube1, tube2, mid, details;
    private Scene model;
    private Double pulse;
    private Double lastTimeMsec, nowMsec;
    private List<Function> onRenderFcts;


    public void onModuleLoad() {
        loadScripts();

        ColladaLoader loader = new ColladaLoader();
        loader.load("model/scene.dae", new OnLoadCallback<JavaScriptObject>() {
            @Override
            public void onLoad(JavaScriptObject object) {
                JsPropertyMap result = Js.uncheckedCast(object);
                model = Js.uncheckedCast(result.get("scene"));

                model.scale.x = model.scale.y = model.scale.z = 0.1f;

                details = model.getObjectByName("details");
                tube1 = model.getObjectByName("tube1");

                Mesh mesh_1 = Js.uncheckedCast(tube1.children[0]);
                Material a = mesh_1.material;
                a.transparent = true;
                a.side = Constants.BackSide;
                a.blending = Constants.AdditiveBlending;
                a.opacity = 0.9f;

                tube2 = model.getObjectByName("tube2");
                Mesh mesh_2 = Js.uncheckedCast(tube2.children[0]);
                Material c = mesh_2.material;
                c.transparent = true;
                c.side = Constants.BackSide;
                c.blending = Constants.AdditiveBlending;
                c.opacity = 0.9f;

                mid = model.getObjectByName("mid");
                Mesh mesh_3 = Js.uncheckedCast(mid.children[0]);
                Material b = mesh_3.material;
                b.transparent = true;
                b.blending = Constants.AdditiveBlending;
                b.opacity = 0.9f;

                init();

            }
        });

    }

    private void init() {

        WebGLRendererParameters parameters = new WebGLRendererParameters();
        parameters.alpha = true;

        renderer = new WebGLRenderer(parameters);
        renderer.setClearColor(new Color("lightgrey"), 0);
        renderer.setSize(window.innerWidth, window.innerHeight);

        document.body.appendChild(renderer.domElement);

        // array of functions for the rendering loop

        onRenderFcts = new ArrayList<>();

        // init scene and camera
        scene = new Scene();
        //lights
        Light ambient = new AmbientLight(0x666666);
        scene.add(ambient);
        Light directionalLight = new DirectionalLight(0x4e5ba0);
        directionalLight.position.set(-1, 1, 1).normalize();
        scene.add(directionalLight);

        //////// Inititalize Basic Camera /////////
        camera = new Camera();
        scene.add(camera);

        //////// Handle ARToolkitSource /////////
        arToolkitSource = new ArToolkitSource("webcam");

        arToolkitSource.init(new OnReady() {
            @Override
            public void onReady() {
                arToolkitSource.onResize(renderer.domElement);
            }
        });

        // handle resize
        window.addEventListener("resize", new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                arToolkitSource.onResize(renderer.domElement);

            }
        });

        //////// Initialize ArToolkitContext /////////
        // create atToolkitContext

        ArToolkitContextProperty arToolkitContextProperty = new ArToolkitContextProperty();
        arToolkitContextProperty.debug = false;

        arToolkitContext = new ArToolkitContext(arToolkitContextProperty);
        arToolkitContext.cameraParametersUrl = "data/camera_para.dat";
        arToolkitContext.detectionMode = "mono";
        arToolkitContext.maxDetectionRate = 30;
        arToolkitContext.canvasWidth = 80 * 3;
        arToolkitContext.canvasHeight = 60 * 3;

        // initialize it
        arToolkitContext.init(new OnCompleted() {
            @Override
            public void onCompleted() {
                camera.projectionMatrix.copy(arToolkitContext.getProjectionMatrix());

            }
        });
        // initialize it

        // update artoolkit on every frame
        onRenderFcts.add((o1) -> {
            if (arToolkitSource.ready == false) return false;
            return arToolkitContext.update(arToolkitSource.domElement);
        });

        //////// Create ArMarkerControls /////////
        markerRoot = new Group();
        scene.add(markerRoot);

        JsPropertyMap args = JsPropertyMap.of();
        args.set("type", "pattern");
        args.set("patternUrl", "data/patt.hiro");

        artoolkitMarker = new ArMarkerControls(arToolkitContext, markerRoot, args);

        //////// Add object to scene via the marker /////////
        markerRoot.add(model);

        onRenderFcts.add((o) -> {
            tube1.rotation.y -= 0.01;
            tube2.rotation.y += 0.005;
            mid.rotation.y -= 0.008;
            details.position.y = (float)(5 + 3 * Math.sin(1.2 * pulse));
            return true;
        });


        // render the scene
        onRenderFcts.add((o) -> {
            renderer.render(scene, camera);
            return true;
        });
        // run the rendering loop
        lastTimeMsec = null;

        animate();

    }

    private void animate() {
        pulse = new Date().getTime() * 0.0009;
        // keep looping
        AnimationScheduler.get().requestAnimationFrame(timestamp -> {
            if (renderer.domElement.parentNode != null) {
                onRenderFcts.forEach( e -> e.apply(null));
                animate();
            }
        });

    }

    private void loadScripts() {
        JavascriptTextResource.IMPL.load(JavascriptTextResource.IMPL.getColladaLoader().getText());
        JavascriptTextResource.IMPL.load(JavascriptTextResource.IMPL.getArtoolkitMin().getText());
        JavascriptTextResource.IMPL.load(JavascriptTextResource.IMPL.getArtoolkitApi().getText());
        JavascriptTextResource.IMPL.load(JavascriptTextResource.IMPL.getThreexArtoolkitsource().getText());
        JavascriptTextResource.IMPL.load(JavascriptTextResource.IMPL.getThreexArtoolkitcontext().getText());
        JavascriptTextResource.IMPL.load(JavascriptTextResource.IMPL.getThreexArmarkercontrols().getText());
    }
}
