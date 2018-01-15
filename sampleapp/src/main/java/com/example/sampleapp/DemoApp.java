package com.example.sampleapp;

import android.app.Application;

import com.memo.sdk.MemoTVCastSDK;

public class DemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MemoTVCastSDK.init(this);
    }
}
