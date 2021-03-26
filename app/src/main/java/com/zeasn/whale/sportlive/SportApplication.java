package com.zeasn.whale.sportlive;

import android.app.Application;

public class SportApplication extends Application {
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
