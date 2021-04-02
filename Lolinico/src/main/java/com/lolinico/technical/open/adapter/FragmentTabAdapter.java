package com.lolinico.technical.open.adapter;

import android.app.Activity;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.recyclerview.widget.RecyclerView;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lolinico.technical.open.parent.BaseFragment;
import com.lolinico.technical.open.utils.RLog;

//import com.zeasn.whale.open.launcher.technical.parent.BaseFragment;


/**
 * Created by Rico on 2019/6/12.
 */
public abstract class FragmentTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    public static final int VIEW_TYPE_WEATHER = -2;
    public static final int VIEW_TYPE_ACCOUNT = -3;
    public static final int VIEW_TYPE_DATE = -4;
    int addCount;
    int itemCount;
    int mFocusPosition;
    int mLastFocusPosition = -1;
    Fragment mCurrentFragment;
    SparseArray<Fragment> mFragmentArray = new SparseArray();
    FrameLayout mFrameLayout;

    public FragmentTabAdapter(Context context, FrameLayout frameLayout) {
        mContext = context;
        mFrameLayout = frameLayout;
    }

    public void setObList(int count) {
        if (count == 0)
            return;
        for (int i = 0; i < count; i++) {
            /**是否需要绑定fragment至viewpager**/
            if (isBindGroupFragment(i))
                mFragmentArray.put(i, bindGroupFragment(i));
        }
        itemCount = count;
        notifyDataSetChanged();
    }

    public abstract RecyclerView.ViewHolder setChildViewHolder(ViewGroup parent, int viewType);

    public abstract Fragment bindGroupFragment(int position);

    public abstract boolean isBindGroupFragment(int position);

    public abstract void onFocusChangeAction(RecyclerView.ViewHolder holder, int position, boolean hasFocus);

    public abstract void onBindGroupHolder(FragmentTabAdapter customTabAdapter, RecyclerView.ViewHolder holder, int position, int viewType);

    public abstract void onFragmentSelected(int currentItemPosition, int lastItemPosition);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return setChildViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        onBindGroupHolder(this, holder, position, getItemViewType(position));
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                onFocusChangeAction(holder, position, b);
                if (b)
                    mFocusPosition = position;
                if (b && mFrameLayout != null && isBindGroupFragment(position)) {
                    if (mCurrentFragment != mFragmentArray.get(position)) {
                        if (handler.hasMessages(MSG_TAG))
                            handler.removeMessages(MSG_TAG);
                        handler.sendMessageDelayed(handler.obtainMessage(MSG_TAG), MSG_DELAY_TIME);
                    }
                }
            }
        });
    }

    public int getAddCount() {
        return addCount;
    }

    public int getFocusPosition() {
        for (int i = 0; i < mFragmentArray.size(); i++) {
            if (mCurrentFragment == mFragmentArray.valueAt(i))
                return mFragmentArray.keyAt(i);
        }
        return mFocusPosition;
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1)
            return VIEW_TYPE_DATE;
        else if (position == getItemCount() - 2)
            return VIEW_TYPE_ACCOUNT;
        else if (position == getItemCount() - 3)
            return VIEW_TYPE_WEATHER;
        return super.getItemViewType(position);
    }

    final int MSG_TAG = 0;
    final int MSG_SELECTED = 1;
    final long MSG_DELAY_TIME = 650;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_TAG:
                    changeFragment(mLastFocusPosition < 0 ? null : mFragmentArray.get(mLastFocusPosition), mFragmentArray.get(mFocusPosition), mFocusPosition, mLastFocusPosition);
                    break;
                case MSG_SELECTED:
                    if (mLastFocusPosition >= 0 && mFragmentArray.get(mLastFocusPosition) instanceof BaseFragment)
                        ((BaseFragment) mFragmentArray.get(mLastFocusPosition)).onDismissSelected();
                    if (mFragmentArray.get(mFocusPosition) instanceof BaseFragment)
                        ((BaseFragment) mFragmentArray.get(mFocusPosition)).onFragmentSelected();
                    onFragmentSelected(mFocusPosition, mLastFocusPosition);
                    mLastFocusPosition = mFocusPosition;
                    break;
            }
        }
    };

    void changeFragment(Fragment fromFragment, Fragment toFragment, int focusPosition, int lastFocusPosition) {
        try {

            if (mCurrentFragment != toFragment)
                mCurrentFragment = toFragment;
            FragmentTransaction ft = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();
            if (toFragment != null && !toFragment.isAdded()) {
                if (fromFragment == null)
                    ft.add(mFrameLayout.getId(), toFragment).commit();
                else {
                    hidenFragment(ft);
                    ft.add(mFrameLayout.getId(), toFragment).commit();
                }
                addCount++;
            } else {
                hidenFragment(ft);
                ft.show(toFragment).commit();
            }
            if (handler.hasMessages(MSG_SELECTED))
                handler.removeMessages(MSG_SELECTED);
            handler.sendMessageDelayed(handler.obtainMessage(MSG_SELECTED), 100);
        } catch (Exception e) {
            RLog.e("FragmentTabAdapter " + e.toString());
        }
    }

    void hidenFragment(FragmentTransaction ft) {
        for (int i = 0; i < mFragmentArray.size(); i++) {
            if (mFragmentArray.valueAt(i).isVisible())
                ft.hide(mFragmentArray.valueAt(i));
        }
    }


    /**
     * 移除MSG
     */
    public void removeHandlerMsg() {
        if (handler.hasMessages(MSG_TAG))
            handler.removeMessages(MSG_TAG);
    }
}
