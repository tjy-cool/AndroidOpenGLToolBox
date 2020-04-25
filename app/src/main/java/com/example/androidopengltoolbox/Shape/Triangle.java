package com.example.androidopengltoolbox.Shape;

import android.content.Context;
import android.opengl.GLES20;

import com.example.androidopengltoolbox.R;
import com.example.androidopengltoolbox.glUtil.IGLProgram;
import com.example.androidopengltoolbox.glUtil.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * 三角形，纯色
 */
public class Triangle extends IGLProgram {
    private FloatBuffer vertexBuffer;

    // 顶点着色器
    private final String VERTEX_SHADER =
            "attribute vec4 vPosition;\n" +
            "void main(){\n" +
            "    gl_Position = vPosition;\n" +
            "}";

    // 片元着色器
    private final String FRAGMENT_SHADER =
            "precision mediump float;\n" +
            "uniform vec4 vColor;\n" +
            "void main(){\n" +
            "    gl_FragColor = vColor;\n" +
            "}\n";

    //每一次取点的时候取几个点
    static final int COORDS_PER_VERTEX = 3;
    //顶点个数
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    //顶点之间的偏移量
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 每个顶点四个字节

    static float triangleCoords[] = {
            0.5f,  0.5f, 0.0f, // top
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f  // bottom right
    };

    // Set color with red, green, blue and alpha (opacity) values
    float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};


    public Triangle() {

    }

    public Triangle(Context context) {
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
    }

    @Override
    public void buildProgram() {
        //根据shader代码和fragment代码 获取到一个渲染程序
        if (mContext != null) {
            mProgram = ShaderUtil.buildProgram(ShaderUtil.readRawTxt(mContext, R.raw.triangle_vertex_shader),
                    ShaderUtil.readRawTxt(mContext, R.raw.triangle_fragment_shader));
        }
        else{
            mProgram = ShaderUtil.buildProgram(VERTEX_SHADER, FRAGMENT_SHADER);
        }

        checkGLError("buildProgram");
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
            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
            checkGLError("mColorHandle");

        }
    }

    @Override
    public void surfaceChange(int width, int height) {
        //宽高
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void drawFrame() {
        //使用渲染程序
        GLES20.glUseProgram(mProgram);

        // 使顶点属性数组有效
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // 为顶点属性赋值
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // 设置颜色
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // 绘制图形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // 禁用顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }


}
