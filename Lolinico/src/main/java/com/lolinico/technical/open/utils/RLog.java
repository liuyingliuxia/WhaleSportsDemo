package com.lolinico.technical.open.utils;

import android.util.Log;

/**
 * Created by Rico on 2017/5/15.
 */
public class RLog {
    public static String TAG = "lolinico";
    public static boolean isTag = true;

    public static void v(String text) {
        if (isTag)
            Log.v(TAG, text);
    }

    public static void e(String text) {
        if (isTag)
            Log.e(TAG, text);
    }


}
