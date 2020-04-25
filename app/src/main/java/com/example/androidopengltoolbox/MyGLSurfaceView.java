package com.example.androidopengltoolbox;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class MyGLSurfaceView extends GLSurfaceView {
    private Renderer myRenderer;

    public MyGLSurfaceView(Context context, Renderer renderer) {
        super(context);
        setEGLContextClientVersion(2);
        myRenderer = renderer;
        setRenderer(renderer);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs, Renderer renderer) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        myRenderer = renderer;
        setRenderer(myRenderer);
    }
}
