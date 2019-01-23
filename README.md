### AR for the WEB with GWT.

We have no wearable AR devices at the moment but it doesn’t mean we don't need to prepare for 
the new age. Fortunately, we already have some technologies that could help us to get ready, namely WebGL or WebRTC and several libraries like three.js, artoolkit and ar.js.

Javascript is a very popular and fast-growing language, but I prefer Java and GWT (Google Web Toolkit) as a framework for web development. Since 2.8 it contains a really cool ability to interop with Javascript without significant performance reduction and is suitable for wrapping existing Javascript libraries.

The example below shows how easy it could be to adapt existing technologies for GWT and create simple AR applications based on pattern matching. Under the hood it uses the Three4g library as a wrapper for three.js objects in Java classes, ColladaLoader to load pre-build scene, ARToolKit to recognize the pattern and THREEx libraries to perform camera actions. Many thanks to all the authors of these amazing libraries!

The example is based on an great article “How to code an augmented reality marker” by Mark Shufflebottom: https://www.creativebloq.com/how-to/how-to-code-an-augmented-reality-marker .

You could give it a try, there are only few steps:

1) in chrome for android and in safari for ios open the link https://env-9441834.cloud.unispace.io
2) Agree to use the Camera, don't worry, please, it sends nothing to me..
3) once it get started, look through the camera at the marker
                 https://github.com/treblereel/gwt-ar-demo/blob/master/pattern/pattern.pdf
4) magic 

Don't worry, please, it sends nothing to me..

Or you could run it on you PC, just clone the repository and run :

 > mvn clean gwt:run

After that you will only need to open the link from gwt starter application in your browser.
