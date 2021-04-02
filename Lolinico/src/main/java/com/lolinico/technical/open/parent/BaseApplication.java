package com.lolinico.technical.open.parent;

import android.app.Application;

/**
 * Created: devin.feng
 * 2020-01-13
 * Email: devin.feng@zeasn.com
 * Descripe:
 */
public class BaseApplication extends Application {

    public static BaseApplication instance;

    public boolean isWaveOpen = true;

    public static BaseApplication getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}

