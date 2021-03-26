package com.zeasn.whale.sportlive.widget;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DensityUtil {
    public static int getWindowWidth(Context context) {
        // 获取屏幕宽度
        if (Build.VERSION.SDK_INT >= 30) {
            WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
            return wm.getCurrentWindowMetrics().getBounds().width();
        } else {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return metrics.widthPixels;
        }
    }

    public static int getWindowHeight(Context context) {
        // 获取屏幕高度
        if (Build.VERSION.SDK_INT >= 30) {
            WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
            return wm.getCurrentWindowMetrics().getBounds().height();
        } else {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return metrics.heightPixels;
        }
    }
}

