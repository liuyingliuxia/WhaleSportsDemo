package com.lolinico.technical.open.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.lolinico.technical.open.adapter.CustomTabAdapter;
import com.lolinico.technical.open.adapter.FragmentViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 * in androidx
 */
public class CustomVerticalViewPager extends VerticalViewPager {
    FragmentViewPagerAdapter fragmentViewPagerAdapter;
    CustomTabAdapter customTabAdapter;
    private boolean noScroll = true; //true 代表不能滑动 //false 代表能滑动

    public CustomVerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        fragmentViewPagerAdapter = new FragmentViewPagerAdapter(this, ((FragmentActivity) context).getSupportFragmentManager(), new ArrayList<Fragment>());
        setAdapter(fragmentViewPagerAdapter);
    }

    public void setPlayList(Context context, List list) {
        fragmentViewPagerAdapter = new FragmentViewPagerAdapter(this,
                ((FragmentActivity) context).getSupportFragmentManager(), list);
        setAdapter(fragmentViewPagerAdapter);
    }

    public CustomVerticalViewPager(Context context) {
        super(context);
    }

    /**
     * 扩展
     **/
    public void setCustomTabAdapter(CustomTabAdapter customTabAdapter) {
        this.customTabAdapter = customTabAdapter;
    }

    public CustomTabAdapter getCustomTabAdapter() {
        return customTabAdapter;
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        if (noScroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);//表示切换的时候，不需要切换时间。
    }

    public Fragment getIndexFragment(int position) {
        return ((FragmentViewPagerAdapter) getAdapter()).getFragments().get(position);
    }

    public List getList() {
        return ((FragmentViewPagerAdapter) getAdapter()).getFragments();
    }

    public Object getCurrentFragment() {
        List<Fragment> fragments = ((FragmentViewPagerAdapter) getAdapter()).getFragments();
        if (fragments.isEmpty())
            return null;
        if (getCurrentItem() >= fragments.size() && fragments.size() > 0) {
            return fragments.get(fragments.size() - 1);
        }
        return fragments.get(getCurrentItem());
    }

}
