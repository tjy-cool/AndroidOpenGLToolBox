package com.example.androidopengltoolbox.Shape;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.androidopengltoolbox.R;
import com.example.androidopengltoolbox.glUtil.IGLProgram;
import com.example.androidopengltoolbox.glUtil.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * 四边形，彩色
 */
public class SquareColorFull extends IGLProgram {
    private FloatBuffer vertexBuffer, colorBuffer;
    private ShortBuffer indexBuffer;
    private final String VERTEX_SHADER =
            "attribute vec4 vPosition;" +
            "uniform mat4 vMatrix;"+
            "varying  vec4 vColor;"+
            "attribute vec4 aColor;"+
            "void main() {" +
            "  gl_Position = vMatrix*vPosition;" +
            "  vColor=aColor;"+
            "}";

    private final String FRAGMENT_SHADER =
            "precision mediump float;" +
            "varying vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}";

    // 顶点坐标
    static float triangleCoords[] = {
            -0.5f,  0.5f, 0.0f, // top left
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f, // bottom right
            0.5f,  0.5f, 0.0f  // top right
    };

    static short index[]={
            0,1,2,0,2,3
    };

    //每一次取点的时候取几个点
    static final int COORDS_PER_VERTEX = 3;
    //顶点个数
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    //顶点之间的偏移量
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 每个顶点四个字节

    private int mMatrixHandler;

    private float[] mViewMatrix=new float[16];
    private float[] mProjectMatrix=new float[16];
    private float[] mMVPMatrix=new float[16];

    //设置颜色
    float color[] = {
            0.0f, 1.0f, 0.0f, 1.0f ,
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f
    };
    //设置颜色，依次为红绿蓝和透明通道
//    float color[] = { 1.0f, 1.0f, 1.0f, 1.0f };

    public SquareColorFull(){

    }

    public SquareColorFull(Context context){
        mContext = context;
    }


    @Override
    public void createBuffers() {
        //为坐标分配本地内存地址
        vertexBuffer = ByteBuffer
                .allocateDirect(triangleCoords.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(triangleCoords);
        vertexBuffer.position(0);

        indexBuffer = ByteBuffer
                .allocateDirect(index.length * 3)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(index);
        indexBuffer.position(0);

        colorBuffer = ByteBuffer
                .allocateDirect(color.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(color);
        colorBuffer.position(0);
    }

    @Override
    public void buildProgram() {
        if (mContext != null) {
            mProgram = ShaderUtil.buildProgram(ShaderUtil.readRawTxt(mContext, R.raw.square_vertex_shader),
                    ShaderUtil.readRawTxt(mContext, R.raw.square_fragment_shader));
            checkGLError("1 buildProgram");
        } else {

            mProgram = ShaderUtil.buildProgram(VERTEX_SHADER, FRAGMENT_SHADER);
            checkGLError("2 buildProgram");
        }

        if (mProgram > 0) {
            setProgramBuilt(true);
        }
        else {
            setProgramBuilt(false);
        }
    }

    @Override
    public void getAttr() {
        if(isProgramBuild()) {
            //获取vertex shader的属性vPosition 的地址
            mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
            checkGLError("mPositionHandle");

            //获取fragment shader的属性vColor 的地址
//            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
            // 获取片元着色器的vColor成员的句柄
            mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
            checkGLError("mColorHandle");

            //获取变换矩阵vMatrix成员句柄
            mMatrixHandler= GLES20.glGetUniformLocation(mProgram,"vMatrix");
            checkGLError("mMatrixHandler");
        }
    }

    @Override
    public void surfaceChange(int width, int height) {
        //计算宽高比
        float ratio=(float)width/height;
        //设置透视投影
        Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio, -1, 1, 1, 2);
        //设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 2.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix,0,mProjectMatrix,0,mViewMatrix,0);
    }

    @Override
    public void drawFrame() {
        //使用渲染程序
        GLES20.glUseProgram(mProgram);

        //指定vMatrix的值
        GLES20.glUniformMatrix4fv(mMatrixHandler,1,false, mMVPMatrix,0);

        // 使顶点属性数组有效
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // 为顶点属性赋值
        GLES20.glVertexAttribPointer(mPositionHandle,
                COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,
                false,
                vertexStride, vertexBuffer);

        // 设置颜色
//        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        //设置绘制三角形的颜色
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(mColorHandle,4,
                GLES20.GL_FLOAT,false,
                0,colorBuffer);

        // 绘制图形
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, index.length, GLES20.GL_UNSIGNED_SHORT, indexBuffer);

        // 禁用顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);


    }
}
