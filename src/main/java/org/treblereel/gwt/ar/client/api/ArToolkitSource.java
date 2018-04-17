package org.treblereel.gwt.ar.client.api;

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsType;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 * Created by treblereel on 4/17/18.
 */
@JsType(isNative = true, namespace = "THREEx")
public class ArToolkitSource {

    public HTMLElement domElement;
    public boolean ready;

    @JsConstructor
    public ArToolkitSource(){

    }

    @JsConstructor
    public ArToolkitSource(String sourceType){

    }

    public native void init(OnReady onReady);

    public native void onResize(HTMLElement domElement);


}
