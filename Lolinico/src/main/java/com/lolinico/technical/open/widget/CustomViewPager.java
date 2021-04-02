package com.lolinico.technical.open.widget;


import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lolinico.technical.open.adapter.FragmentViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rico on 2018/2/12.
 */

public class CustomViewPager extends ViewPager {
    FragmentViewPagerAdapter fragmentViewPagerAdapter;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        fragmentViewPagerAdapter = new FragmentViewPagerAdapter(
                ((FragmentActivity) context).getSupportFragmentManager(), new ArrayList<Fragment>());
        setAdapter(fragmentViewPagerAdapter);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);//表示切换的时候，不需要切换时间。
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }


    public Fragment getIndexFragment(int position) {
        return fragmentViewPagerAdapter.getFragments().get(position);
    }

    public List getList() {
        return fragmentViewPagerAdapter.getFragments();
    }

    public Object getCurrentFragment() {
        return fragmentViewPagerAdapter.getFragments().get(getCurrentItem());
    }

}
