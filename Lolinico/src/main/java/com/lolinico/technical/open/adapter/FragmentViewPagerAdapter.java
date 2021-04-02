package com.lolinico.technical.open.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.lolinico.technical.open.widget.CustomVerticalViewPager;

import java.util.List;

public class FragmentViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments; // 每个Fragment对应一个Page
    private FragmentManager fragmentManager;
    CustomVerticalViewPager mCustomVerticalViewPager;

    public FragmentViewPagerAdapter(CustomVerticalViewPager pager, FragmentManager fragmentManager, List<Fragment> fragments) {
        super(fragmentManager);
        this.fragments = fragments;
        this.mCustomVerticalViewPager = pager;
        this.fragmentManager = fragmentManager;
    }

    public FragmentViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
        super(fragmentManager);
        this.fragments = fragments;

        this.fragmentManager = fragmentManager;
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        RLog.v("container==" + container.toString() + "\nobject==" + object + "\nposition==" + position);
//    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
