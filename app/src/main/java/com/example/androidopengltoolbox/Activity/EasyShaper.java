package com.example.androidopengltoolbox.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.androidopengltoolbox.R;
import com.example.androidopengltoolbox.glUtil.GLES20Support;
import com.example.androidopengltoolbox.glUtil.GLManager;
import com.example.androidopengltoolbox.glUtil.GLShape;

public class EasyShaper extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnPoint;
    private Button mBtnLine;
    private Button mBtnTriangle;
    private Button mBtnTriangleWithCamera;
    private Button mBtnTriangleColorFull;
    private Button mBtnSquare;
    private Button mBtnSquareColorFull;

    private FrameLayout mFrameLayout;
    private GLManager glManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_shaper);

        initView();
    }

    private void initView() {
        //检查设备是否支持OpenGL ES 2.0
        if (!GLES20Support.detectOpenGLES20(this)) {
            GLES20Support.getNoSupportGLES20Dialog(this);
        }

        mFrameLayout = findViewById(R.id.frameLayout);
        glManager = new GLManager(EasyShaper.this, mFrameLayout);

        mBtnPoint = findViewById(R.id.btnPoint);
        mBtnLine = findViewById(R.id.btnLine);
        mBtnTriangle = findViewById(R.id.btnTriangle);
        mBtnTriangle = findViewById(R.id.btnTriangle);
        mBtnTriangleWithCamera = findViewById(R.id.btnTriangleWithCamera);
        mBtnTriangleColorFull = findViewById(R.id.btnTriangleColorFull);
        mBtnSquare = findViewById(R.id.btnSquare);
        mBtnSquareColorFull = findViewById(R.id.btnSquareColorFull);


        mBtnPoint.setOnClickListener(this);
        mBtnLine.setOnClickListener(this);
        mBtnTriangle.setOnClickListener(this);
        mBtnTriangleWithCamera.setOnClickListener(this);
        mBtnTriangleColorFull.setOnClickListener(this);
        mBtnSquare.setOnClickListener(this);
        mBtnSquareColorFull.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPoint:
                glManager.createGLSurfaceView(GLShape.Point);
                break;

            case R.id.btnLine:
                glManager.createGLSurfaceView(GLShape.Line);
                break;

            case R.id.btnTriangle:
                glManager.createGLSurfaceView(GLShape.Triangle);
                break;

            case R.id.btnTriangleWithCamera:
                glManager.createGLSurfaceView(GLShape.TriangleWithCamera);
                break;

            case R.id.btnTriangleColorFull:
                glManager.createGLSurfaceView(GLShape.TriangleColorFull);
                break;

            case R.id.btnSquare:
                glManager.createGLSurfaceView(GLShape.Square);
                break;

            case R.id.btnSquareColorFull:
                glManager.createGLSurfaceView(GLShape.SquareColorFull);
                break;

            default:
                break;
        }
    }
}
