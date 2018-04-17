package org.treblereel.gwt.ar.client.api;

import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.JsType;
import org.treblereel.gwt.three4g.loaders.OnLoadCallback;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 * Created by treblereel on 4/17/18.
 */
@JsType(isNative = true, namespace = "THREE")
public class ColladaLoader {

    public native void load(String url, OnLoadCallback<JavaScriptObject> onLoad);

}
