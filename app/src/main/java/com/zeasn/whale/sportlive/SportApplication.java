package com.zeasn.whale.sportlive;

import android.annotation.SuppressLint;

import com.lolinico.technical.open.parent.BaseApplication;

public class SportApplication extends BaseApplication {
    @SuppressLint("StaticFieldLeak")
    public static SportApplication instance;

    public static SportApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
