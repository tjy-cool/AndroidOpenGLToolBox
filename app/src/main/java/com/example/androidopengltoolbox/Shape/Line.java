package com.example.androidopengltoolbox.Shape;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.example.androidopengltoolbox.glUtil.IGLProgram;
import com.example.androidopengltoolbox.glUtil.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Line extends IGLProgram {

    private FloatBuffer vertexBuffer;

//    static float LineCoords[] = {
//        -0.3f, 0.3f, 0.0f, 0.0f,
//         0.1f, 0.6f, 0.0f, 0.0f,
//        -0.5f, -0.2f, 0.0f, 0.0f,
//        0.7f, -0.6f, 0.0f, 0.0f,
//        0.1f, 0.6f, 0.0f, 0.0f,
//    };

    static float LineCoords[] = {
            -0.3f, 0.3f, 0.0f,
            0.1f, 0.6f, 0.0f,
            0.7f, 0.6f, 0.0f,
            0.5f, -0.2f, 0.0f,
            0.1f, -0.5f, 0.0f,
            -0.4f, -0.5f, 0.0f
    };

    final String VERTEX_SHADER = "attribute vec4 vPosition;\n" +
            "void main() { \n " +
//            "gl_Position = vec4(0.0f, 0.5f, 0.0f, 1.0f); \n" +
//            "gl_Position = vec4(0.0,0.0,0.0,1.0); \n" +
            "gl_Position = vPosition; \n" +
            "gl_PointSize = 50.0; \n" +
            "} \n" ;

    // 片元着色器
    private final String FRAGMENT_SHADER =
                    "void main(){\n" +
//                    "    gl_FragColor = vec4(1.0f, 1.0f, 0.0f, 1.0f);\n" +

                            "    if(gl_FragCoord.x < 300.0){\n" +

                            "      gl_FragColor = vec4(1.0,0.0,0.0,1.0);\n" +
                            "    }else if (gl_FragCoord.x <= 400.0) {\n" +

                            "      gl_FragColor = vec4(0.0,1.0,0.0,1.0);\n" +
                            "    }else {\n" +

                            "      gl_FragColor = vec4(0.0,0.0,1.0,1.0);\n" +
                            "}\n" +

                    "}\n";


    public Line(Context context) {
        mContext = context;
    }

    @Override
    public void createBuffers() {
        //为坐标分配本地内存地址
        vertexBuffer = ByteBuffer
                .allocateDirect(LineCoords.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(LineCoords);
        vertexBuffer.position(0);
    }

    @Override
    public void buildProgram() {
        mProgram = ShaderUtil.buildProgram(VERTEX_SHADER, FRAGMENT_SHADER);
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
            mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
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

        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glVertexAttribPointer(mPositionHandle,
                3,
                GLES20.GL_FLOAT,
                false,
                12,
                vertexBuffer);

        GLES20.glLineWidth(20);


        GLES20.glDrawArrays(GLES20.GL_LINES, 0, 6);

//        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 6);
//        GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, 6);
//        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, 6);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 6);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);


        // 绘制图形
//        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 10);

        GLES20.glDisableVertexAttribArray(mPositionHandle);

    }
}
