package com.lolinico.technical.open.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.leanback.widget.VerticalGridView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lolinico.technical.open.adapter.MenuRootAdapter;
import com.lolinico.technical.open.utils.WidgetUtils;
import com.steelbar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rico on 2017/12/26.
 */

public abstract class CustomMenuView extends LinearLayout {
    RelativeLayout mRelBackGround;
    VerticalGridView mRecyelerView;
    MenuRootAdapter mMenuParentAdapter;
    List<MenuRootAdapter.DataTree> dataTrees;
    SlidingProcessView.OnCustomKeyListener customKeyListener;

    public CustomMenuView(Context context, SlidingProcessView.OnCustomKeyListener listener) {
        super(context);
        this.dataTrees = new ArrayList<>();
        this.customKeyListener = listener;
        init();
    }

    @SuppressLint("RestrictedApi")
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.menu_view, this);
        mRelBackGround = (RelativeLayout)findViewById(R.id.content_view_bg);
        mRecyelerView = (VerticalGridView)findViewById(R.id.recyclerview);
        mRecyelerView.setHasFixedSize(true);
        WidgetUtils.setViewMarginsParams(getContext(), mRecyelerView, 0, 0,0,0.09,0,0.09);

        mMenuParentAdapter = getMenuParentAdapter();
        mMenuParentAdapter.setCustomKeyListener(customKeyListener);
        mMenuParentAdapter.setDataTreeList(dataTrees);
        mRecyelerView.setAdapter(mMenuParentAdapter);
    }


    public List<MenuRootAdapter.DataTree> getDataTrees() {
        if (mMenuParentAdapter != null)
            return mMenuParentAdapter.getDataTreeList();
        return null;
    }

    protected void setBackGroundColor(int resId) {
        if (mRelBackGround != null)
            mRelBackGround.setBackgroundColor(resId);
    }

    protected void setBackGroundDrawable(Drawable drawable) {
        if (mRelBackGround != null)
            mRelBackGround.setBackground(drawable);
    }

    protected void setDataTrees() {
        if (mMenuParentAdapter != null)
            mMenuParentAdapter.setDataTreeList(dataTrees);
    }

    public void setMenuRequest() {
        if (mRecyelerView != null)
            mRecyelerView.requestFocusFromTouch();
    }

    public abstract MenuRootAdapter getMenuParentAdapter();

}
