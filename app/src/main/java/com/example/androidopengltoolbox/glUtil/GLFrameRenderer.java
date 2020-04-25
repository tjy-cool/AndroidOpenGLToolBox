package com.example.androidopengltoolbox.glUtil;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLFrameRenderer implements GLSurfaceView.Renderer {
    private final Context mContext;
    private final GLShape mGLShape;
    private IGLProgram glProgram;

   public GLFrameRenderer(Context context, GLShape glShape) {
       mContext = context;
       mGLShape = glShape;
   }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        if (glProgram == null){
            glProgram = GLProgramFactory.createGLProgram(mContext, mGLShape);
        }

        if (!glProgram.isProgramBuild()) {
            glProgram.createBuffers();
            glProgram.buildProgram();
            glProgram.getAttr();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glProgram.surfaceChange(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
       // GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);        // 设置背景色为黑色
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT| GLES20.GL_DEPTH_BUFFER_BIT);
       glProgram.drawFrame();
    }
}
