package com.lolinico.technical.open.widget;

import androidx.fragment.app.Fragment;
import android.content.Context;
import androidx.leanback.widget.HorizontalGridView;
import android.util.AttributeSet;

import com.lolinico.technical.open.adapter.CustomTabAdapter;
import com.lolinico.technical.open.adapter.CustomTabLayoutAdapter;
import com.lolinico.technical.open.adapter.FragmentTabAdapter;


/**
 * Created by Rico on 2018/2/9.
 */

public class CustomTabLayout extends HorizontalGridView {

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHasFixedSize(true);
    }

    public void setPlayList(CustomTabAdapter adapter, int itemCount) {
        setAdapter(adapter);
        adapter.setObList(itemCount);
    }

    public void setPlayList(CustomTabLayoutAdapter adapter, int itemCount) {
        setAdapter(adapter);
        adapter.setObList(itemCount);
    }


    public void setPlayList(FragmentTabAdapter adapter, int itemCount) {
        setAdapter(adapter);
        adapter.setObList(itemCount);
    }

    public int getTabCount() {
        if (getAdapter() == null)
            return 0;
        return ((FragmentTabAdapter) getAdapter()).getAddCount();
    }

    public int getCurrentItem() {
        if (getAdapter() == null)
            return 0;
        return ((FragmentTabAdapter) getAdapter()).getFocusPosition();
    }

    public Fragment getCurrentFragment() {
        if (getAdapter() == null)
            return null;
        return ((FragmentTabAdapter) getAdapter()).getCurrentFragment();
    }


}
