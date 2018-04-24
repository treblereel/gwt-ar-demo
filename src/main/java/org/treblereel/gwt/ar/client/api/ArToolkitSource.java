package org.treblereel.gwt.ar.client.api;

import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLVideoElement;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsType;
import jsinterop.base.JsPropertyMap;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 * Created by treblereel on 4/17/18.
 */
@JsType(isNative = true, namespace = "THREEx")
public class ArToolkitSource {

    public HTMLVideoElement domElement;
    public boolean ready;

    @JsConstructor
    public ArToolkitSource(){

    }

    @JsConstructor
    public ArToolkitSource(JsPropertyMap parameters){

    }

    public native void init(OnReady onReady);

    public native void onResize(HTMLElement domElement);


}
