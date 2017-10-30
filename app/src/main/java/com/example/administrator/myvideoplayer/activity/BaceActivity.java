package com.example.administrator.myvideoplayer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public abstract class BaceActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
        initListener();
        initData();
        
    }
    protected abstract int getLayout();
    protected abstract void initView();
    protected abstract void initListener();
    protected abstract void initData();






}
