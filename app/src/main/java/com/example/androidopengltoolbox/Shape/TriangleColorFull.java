package com.example.androidopengltoolbox.Shape;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.androidopengltoolbox.glUtil.IGLProgram;
import com.example.androidopengltoolbox.glUtil.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * 三角形，彩色
 */
public class TriangleColorFull extends IGLProgram {

    private FloatBuffer vertexBuffer, colorBuffer;
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
            "uniform mat4 vMatrix;"+
            "varying  vec4 vColor;"+
            "attribute vec4 aColor;"+
            "void main() {" +
            "  gl_Position = vMatrix*vPosition;" +
            "  vColor=aColor;"+
            "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
            "varying vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}";

    static final int COORDS_PER_VERTEX  = 3;
    static float triangleCoords[] = {
            0.0f,  0.0f, 0.0f, // top
            -1.0f, -2.0f, 0.0f, // bottom left
            1.0f, -2.0f, 0.0f  // bottom right
    };

    //设置颜色
    float color[] = {
            0.0f, 1.0f, 0.0f, 1.0f ,
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f
    };

    //顶点个数
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    // 顶点之间的偏移量, 每个顶点四个字节
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    private float[] mViewMatrix=new float[16];
    private float[] mProjectMatrix=new float[16];
    private float[] mMVPMatrix=new float[16];

    @Override
    public void createBuffers() {
        vertexBuffer = ByteBuffer
                .allocateDirect(triangleCoords.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(triangleCoords);
        vertexBuffer.position(0);

        colorBuffer = ByteBuffer
                .allocateDirect(color.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(color);
        colorBuffer.position(0);
    }

    @Override
    public void buildProgram() {
        mProgram = ShaderUtil.buildProgram(vertexShaderCode, fragmentShaderCode);
        if (mProgram > 0) {
            setProgramBuilt(true);
        }
        else {
            setProgramBuilt(false);
        }
    }

    @Override
    public void getAttr() {
        // 获取顶点着色器的vPosition成员句柄
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        checkGLError("1 getAttr");

        // 获取片元着色器的vColor成员的句柄
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
        checkGLError("2 getAttr");

        // 获取变换矩阵vMatrix成员句柄
        mMatrixHandle = GLES20.glGetUniformLocation(mProgram, "vMatrix");
        checkGLError("3 getAttr");

    }

    @Override
    public void surfaceChange(int width, int height) {
        //计算宽高比
        float ratio=(float)width/height;
        //设置透视投影
        Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio, -1, 1, 2, 8);
        //设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 7.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix,0, mProjectMatrix,0,mViewMatrix,0);
    }

    @Override
    public void drawFrame() {
        //将程序加入到OpenGLES2.0环境
        GLES20.glUseProgram(mProgram);

        //指定vMatrix的值
        GLES20.glUniformMatrix4fv(mMatrixHandle,1,false,mMVPMatrix,0);

        // 启用三角形顶点的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        // 准备三角形坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle,
                COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,
                false,
                vertexStride,
                vertexBuffer);

        //设置绘制三角形的颜色
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(mColorHandle,4,
                GLES20.GL_FLOAT,false,
                0,colorBuffer);

        //绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        //禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
