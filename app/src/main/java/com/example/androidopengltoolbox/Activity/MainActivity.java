package com.example.androidopengltoolbox.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.androidopengltoolbox.ActivityUtils.BaseActivity;
import com.example.androidopengltoolbox.R;
import com.example.androidopengltoolbox.glUtil.GLES20Support;
import com.example.androidopengltoolbox.glUtil.GLManager;
import com.example.androidopengltoolbox.glUtil.GLShape;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG = "MainActivity";


    private Button mBtnEasyShaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mBtnEasyShaper = findViewById(R.id.btnEasyShaperActivity);
        mBtnEasyShaper.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEasyShaperActivity:
                startEasyShaperActivity();
                break;
            default:
                break;

        }
    }

    private void startEasyShaperActivity() {
        Intent intent = new Intent(MainActivity.this, EasyShaper.class);
        startActivity(intent);
    }
}
