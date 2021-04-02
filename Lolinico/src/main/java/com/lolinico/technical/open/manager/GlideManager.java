package com.lolinico.technical.open.manager;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.lolinico.technical.open.utils.RLog;

/**
 * Created by Rico on 2018/12/28.
 */
public class GlideManager {
    public static void displayImage(ImageView imageView, Object url, RequestOptions requestOptions) {
        try {
            if (requestOptions == null)
                Glide.with(imageView.getContext()).load(url).into(imageView);
            else {
                requestOptions = requestOptions.format(DecodeFormat.PREFER_RGB_565);
                Glide.with(imageView.getContext()).load(url).apply(imageView.getWidth() <= 0 || imageView.getHeight() <= 0 ?
                        requestOptions : requestOptions.override(imageView.getWidth(), imageView.getHeight())).into(imageView);
            }
        } catch (Exception e) {
            RLog.v("displayImage error === " + e.toString());
            e.printStackTrace();
        }
    }

    public static void displayImage(ImageView imageView, Object url) {
        RequestOptions mOptions = new RequestOptions().format(DecodeFormat.PREFER_RGB_565).centerCrop().priority(Priority.HIGH);
        Glide.with(imageView.getContext()).load(url).apply(mOptions).into(imageView);
    }

    public static void displayImage(ImageView imageView, Object url, int width, int height) {
        RequestOptions mOptions = new RequestOptions().format(DecodeFormat.PREFER_RGB_565).centerCrop().priority(Priority.HIGH)
                .override(width, height);
        Glide.with(imageView.getContext()).load(url).apply(mOptions).into(imageView);
    }

    public static void displayImage(ImageView imageView, Object url, int defaultDrawableId) {
        RequestOptions mOptions = new RequestOptions().format(DecodeFormat.PREFER_RGB_565).centerCrop().priority(Priority.HIGH)
                .placeholder(defaultDrawableId)
                .error(defaultDrawableId);
        Glide.with(imageView.getContext()).load(url).apply(mOptions).into(imageView);
    }

    public static void displayImage(ImageView imageView, String url, int defaultDrawableId, int width, int height) {
        RequestOptions mOptions = new RequestOptions().centerCrop().format(DecodeFormat.PREFER_RGB_565).priority(Priority.HIGH)
                .placeholder(defaultDrawableId)
                .error(defaultDrawableId).override(width, height);
        Glide.with(imageView.getContext()).load(url).apply(mOptions).into(imageView);
    }

}
