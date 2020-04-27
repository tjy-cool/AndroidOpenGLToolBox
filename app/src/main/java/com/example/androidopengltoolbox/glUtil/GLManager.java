package com.example.androidopengltoolbox.glUtil;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class GLManager {
    private final FrameLayout mFrameLayout;
    private final Context mContext;
    private GLSurfaceView mGLSurfaceView;
    private GLFrameRenderer mGLRenderer;
    private GLShape mGLshape;

    public GLManager(Context context, FrameLayout frameLayout) {
        mContext = context;
        mFrameLayout = frameLayout;
    }

    public void createGLSurfaceView(GLShape glShape) {
        if (glShape == mGLshape) {
            return;
        }
        if (mGLSurfaceView != null) {
            mFrameLayout.removeView(mGLSurfaceView);
        }
        mGLshape = glShape;
        GLSurfaceView glSurfaceView = new GLSurfaceView(mContext);
        initView(glSurfaceView, glShape);
        mFrameLayout.addView(mGLSurfaceView);
    }

    private void initView(final GLSurfaceView glSurfaceView, final GLShape glShape) {
        mGLSurfaceView = glSurfaceView;
        if (mGLSurfaceView != null ) {
            ViewTreeObserver vto2 = mGLSurfaceView.getViewTreeObserver();
            vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mGLSurfaceView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    mGLSurfaceView.setEGLContextClientVersion(2);
                    mGLRenderer = new GLFrameRenderer(mContext, glShape);
                    mGLSurfaceView.setRenderer(mGLRenderer);
                    mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
                }
            });
        }
    }

}
