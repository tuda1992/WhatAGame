package com.tuda.whatagame;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends BaseActivity {

    private AdView mAdView;
    private Toolbar mToolbar;
    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        initToolBar();
        initAdmob();

        moveToStart();
    }

    private void moveToStart() {
        StartFragment startFragment = new StartFragment();
        replaceFragment(startFragment, startFragment.getClass().getName());
    }

    private void moveToGame() {
        GameFragment gameFragment = new GameFragment();
        replaceFragment(gameFragment, gameFragment.getClass().getName());
    }

    private void initToolBar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initDatas(Bundle saveInstanceState) {

    }

    @Override
    protected void getData() {

    }

    private void initAdmob() {

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);
    }
}
