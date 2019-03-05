package com.tuda.whatagame;

import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayoutView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initDatas(Bundle saveInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityAnim(MainActivity.class,null);
            }
        },1500);
    }

    @Override
    protected void getData() {

    }
}
