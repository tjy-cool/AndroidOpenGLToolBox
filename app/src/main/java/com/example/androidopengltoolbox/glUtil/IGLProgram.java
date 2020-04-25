package com.example.androidopengltoolbox.glUtil;

import android.content.Context;
import android.opengl.GLES20;

public abstract class IGLProgram {
    private boolean isProgramBuilt;
    protected Context mContext;
    protected int mProgram;
    protected int mPositionHandle;
    protected int mColorHandle;
    protected int mMatrixHandle;

    public boolean isProgramBuild() {
        return isProgramBuilt;
    }

    public void setProgramBuilt(boolean programBuilt) {
        isProgramBuilt = programBuilt;
    }

    /**
     * 分配内存
     */
    public abstract void createBuffers();

    /**
     * 创建
     */
    public abstract void buildProgram();

    public abstract void getAttr();

    public abstract void surfaceChange(int width, int height);

    public abstract void drawFrame();

    /**
     * 检查错误，即获取gl的报错
     * @param msg 消息
     */
    public void checkGLError(String msg) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            throw new RuntimeException(msg + ": glError " + error);
        }
    }

}
