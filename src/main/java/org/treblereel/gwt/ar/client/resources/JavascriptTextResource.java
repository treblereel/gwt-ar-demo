package org.treblereel.gwt.ar.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import org.treblereel.gwt.three4g.resources.ThreeJsTextResource;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 * Created by treblereel on 3/26/18.
 */
public interface JavascriptTextResource extends ClientBundle {

    JavascriptTextResource IMPL = GWT.create(JavascriptTextResource.class);

    @Source("js/ColladaLoader.js")
    TextResource getColladaLoader();

    @Source("js/threex-artoolkitcontext.js")
    TextResource getThreexArtoolkitcontext();

    @Source("js/threex-armarkercontrols.js")
    TextResource getThreexArmarkercontrols();

    @Source("js/threex-artoolkitprofile.js")
    TextResource getThreexArtoolkitprofile();

    @Source("js/threex-artoolkitsource.js")
    TextResource getThreexArtoolkitsource();

    @Source("js/artoolkit.api.js")
    TextResource getArtoolkitApi();

    @Source("js/artoolkit.debug.js")
    TextResource getArtoolkitDebug();

    @Source("js/artoolkit.min.js")
    TextResource getArtoolkitMin();

    @Source("js/artoolkit.three.js")
    TextResource getArtoolkitThree();

    default void load(String script) {
        ScriptInjector.fromString(script).setWindow(ScriptInjector.TOP_WINDOW).inject();
    }
}
