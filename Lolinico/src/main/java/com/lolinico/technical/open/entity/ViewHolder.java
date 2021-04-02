package com.lolinico.technical.open.entity;

import android.content.Context;
import android.graphics.drawable.Drawable;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lolinico.technical.open.manager.ImageLoadManager;
import com.lolinico.technical.open.utils.RLog;

import java.io.File;

public class ViewHolder extends RecyclerView.ViewHolder {

    public int viewType;
    private Context mContext;
    private View mConvertView;
    private SparseArray<View> mViews;

    public ViewHolder(View view) {
        super(view);
        mContext = view.getContext();
        mConvertView = view;
        mViews = new SparseArray<View>();
    }

    /**
     * 通过控件ID获取对应控件
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }

    public ViewHolder setImageBytes(int viewId, byte[] bytes, int defaultDrawableId, int errorDrawableId) {
        ImageView iv = getView(viewId);
        ImageLoadManager.getInstance().displayImage(mContext, bytes, iv, defaultDrawableId, errorDrawableId);

        return this;
    }

    public ViewHolder setImageByURL(int viewId, final String url) {
        ImageView iv = getView(viewId);
//        ImageLoadManager.getInstance().displayImage(mContext, url, iv);
        Glide.with(mContext)
                .load(url)
                .apply(new RequestOptions().placeholder(iv.getDrawable()))
                .into(iv);
        return this;
    }

    public ViewHolder setImageByDrawable(int viewId, final Drawable drawable, int errorDrawableId) {
        ImageView iv = getView(viewId);
        ImageLoadManager.getInstance().displayImage(mContext, iv, drawable, errorDrawableId);
        return this;
    }

    public ViewHolder setImageByURL(int viewId, final String url, int defaultDrawableId, int errorDrawableId) {
        ImageView iv = getView(viewId);
        RLog.v("url>>>>"+url);
        ImageLoadManager.getInstance().displayImage(mContext, url, iv, defaultDrawableId, errorDrawableId);
        return this;
    }

    public ViewHolder setImageFile(int viewId, File file, int defultDrawableId, int errorDrawableId) {
        ImageView iv = getView(viewId);
        ImageLoadManager.getInstance().displayImage(mContext,file,iv,defultDrawableId, errorDrawableId);
        return this;
    }
}
