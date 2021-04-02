package com.lolinico.technical.open.widget;

import android.annotation.SuppressLint;
import android.view.animation.BaseInterpolator;

import com.lolinico.technical.open.utils.RLog;

/**
 * Created by Rico on 2018/8/14.
 */
@SuppressLint("NewApi")
public class VInterpolator extends BaseInterpolator {

    public VInterpolator() {
    }

    @Override
    public float getInterpolation(float input) {
        RLog.v("input " + input);
        if (input <= 0.5) {
            return (float) Math.pow(input + 0.05, 60f);
        } else
            return (float) Math.pow(input, 7.0f);
    }
}
