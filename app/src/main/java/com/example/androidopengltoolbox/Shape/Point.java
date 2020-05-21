package com.example.androidopengltoolbox.Shape;

import android.content.Context;
import android.opengl.GLES20;

import com.example.androidopengltoolbox.glUtil.IGLProgram;
import com.example.androidopengltoolbox.glUtil.ShaderUtil;

public class Point extends IGLProgram {


    final String VERTEX_SHADER = "void main() { \n " +
//            "gl_Position = vec4(0.0f, 0.5f, 0.0f, 1.0f); \n" +
            "gl_Position = vec4(0.0,0.0,0.0,1.0); \n" +
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

    public Point(Context context) {
        mContext = context;
    }

    @Override
    public void createBuffers() {

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

        // 绘制图形
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 10);

    }
}
