package com.lolinico.technical.open.adapter;

import android.os.Parcelable;
import android.view.View;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> views;
    private int mChildCount = 0;

    public ViewPagerAdapter(List<View> views) {
        this.views = views;
    }

    public void setViews(List<View> views) {
        this.views = views;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        if (views != null && arg1 < views.size())
            ((ViewPager) arg0).removeView(views.get(arg1));
    }

    @Override
    public void finishUpdate(View arg0) {

    }

    @Override
    public int getCount() {
        return views.size();
    }

    @NotNull
    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(views.get(arg1), 0);
        return views.get(arg1);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {

    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {

    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }
}
