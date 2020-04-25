package com.example.androidopengltoolbox.Activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.example.androidopengltoolbox.ActivityUtils.BaseActivity;
import com.example.androidopengltoolbox.MyGLSurfaceView;
import com.example.androidopengltoolbox.R;
import com.example.androidopengltoolbox.glUtil.GLES20Support;
import com.example.androidopengltoolbox.glUtil.GLFrameRenderer;
import com.example.androidopengltoolbox.glUtil.GLShape;

public class MainActivity extends BaseActivity {

    private MyGLSurfaceView myGLSurfaceView;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //检查设备是否支持OpenGL ES 2.0
        if (!GLES20Support.detectOpenGLES20(this)) {
            GLES20Support.getNoSupportGLES20Dialog(this);
        }

//        GLFrameRenderer glFrameRenderer = new GLFrameRenderer(MainActivity.this, GLShape.TriangleWithCamera);
//        GLFrameRenderer glFrameRenderer = new GLFrameRenderer(MainActivity.this, GLShape.Triangle);
//        GLFrameRenderer glFrameRenderer = new GLFrameRenderer(MainActivity.this, GLShape.Square);
        GLFrameRenderer glFrameRenderer = new GLFrameRenderer(MainActivity.this, GLShape.SquareColorFull);

        myGLSurfaceView = new MyGLSurfaceView(this, glFrameRenderer);
        myGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        setContentView(myGLSurfaceView);
    }
}
