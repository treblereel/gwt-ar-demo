package org.treblereel.gwt.ar.client.api;

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;
import org.treblereel.gwt.three4g.math.Matrix4;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 * Created by treblereel on 4/17/18.
 */
@JsType(isNative = true, namespace = "THREEx")
public class ArToolkitContext {

    public String cameraParametersUrl;

    public String detectionMode;

    public int maxDetectionRate;

    public int canvasWidth;

    public int canvasHeight;

    public boolean debug;

    public native void init(OnCompleted onCompleted);

    public native Matrix4 getProjectionMatrix();

    public native boolean update(HTMLElement domElement);

    @JsConstructor
    public ArToolkitContext(ArToolkitContextProperty arToolkitContextProperty){

    }
}
