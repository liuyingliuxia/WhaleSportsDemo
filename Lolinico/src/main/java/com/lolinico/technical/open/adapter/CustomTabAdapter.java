package com.lolinico.technical.open.adapter;

import androidx.fragment.app.Fragment;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.lolinico.technical.open.widget.CustomVerticalViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Rico on 2018/2/9.
 */

public abstract class CustomTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_FRIST = -1;
    public static final int VIEW_TYPE_WEATHER = -2;
    public static final int VIEW_TYPE_ACCOUNT = -3;
    public static final int VIEW_TYPE_DATE = -4;
    int itemCount;
    int mLastItemPosition = -1;
    int mFocusPosition;
    Context mContext;
    CustomVerticalViewPager customVerticalViewPager;
    List<OnPageSelectedListener> onPageSelectedListenerList;
    HashMap<Integer, RecyclerView.ViewHolder> memoryPositionMap;
    boolean isWhaleTV;

    public CustomTabAdapter(Context context, CustomVerticalViewPager pager, boolean isWhale) {
        isWhaleTV = true;
        mContext = context;
        customVerticalViewPager = pager;
        initPagerChangeListener();
        memoryPositionMap = new HashMap<>();
        onPageSelectedListenerList = new ArrayList<>();
    }

    void initPagerChangeListener() {
        if (customVerticalViewPager != null)
            customVerticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if (onPageSelectedListenerList != null && onPageSelectedListenerList.size() > 0)
                        onPageSelectedListenerList.get(position).onPageSelectedAction(position);
                    mLastItemPosition = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
    }

    public void setObList(int count) {
        if (count == 0)
            return;
        List fragmentList = new ArrayList();
        customVerticalViewPager.getList().clear();
        for (int i = 0; i < count; i++) {
            /**是否需要绑定fragment至viewpager**/
            if (isBindGroupFragment(i))
                fragmentList.add(bindGroupFragment(i));
            if (i == count - 1) {
                if (customVerticalViewPager != null)
                    customVerticalViewPager.setPlayList(mContext, fragmentList);
            }
        }
        itemCount = count;
        customVerticalViewPager.setCustomTabAdapter(this);
        notifyDataSetChanged();
    }


    public abstract RecyclerView.ViewHolder setChildViewHolder(ViewGroup parent, int viewType);

    public abstract Fragment bindGroupFragment(int position);

    public abstract boolean isBindGroupFragment(int position);

    public abstract void onFocusChangeAction(RecyclerView.ViewHolder holder, int position, boolean hasFocus);

    public abstract void onBindGroupHolder(CustomTabAdapter customTabAdapter, RecyclerView.ViewHolder holder, int position, int viewType);

    public abstract void onPageSelected(RecyclerView.ViewHolder holder, int currentItemPosition, int lastItemPosition);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return setChildViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        onBindGroupHolder(this, holder, position, getItemViewType(position));
        if (memoryPositionMap.get(position) == null) {
            onPageSelectedListenerList.add(new OnPageSelectedListener() {
                @Override
                public void onPageSelectedAction(int currentItemPosition) {
                    onPageSelected(holder, currentItemPosition, mLastItemPosition);
                }
            });
            memoryPositionMap.put(position, holder);
        }
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                onFocusChangeAction(holder, position, b);
                if (b)
                    mFocusPosition = position;
                if (customVerticalViewPager != null)
                    if (isBindGroupFragment(position) && b && customVerticalViewPager.getCurrentItem() != position) {
                        if (handler.hasMessages(MSG_TAG))
                            handler.removeMessages(MSG_TAG);
                        handler.sendMessageDelayed(handler.obtainMessage(MSG_TAG), MSG_DELAY_TIME);
                    }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0)
//            return VIEW_TYPE_FRIST;
        if (isWhaleTV)
            if (position == getItemCount() - 1)
                return VIEW_TYPE_DATE;
            else if (position == getItemCount() - 2)
                return VIEW_TYPE_ACCOUNT;
            else if (position == getItemCount() - 3)
                return VIEW_TYPE_WEATHER;
        return super.getItemViewType(position);
    }


    public interface OnPageSelectedListener {
        void onPageSelectedAction(int currentItemPosition);
    }


    final int MSG_TAG = 0;
    final long MSG_DELAY_TIME = 650;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_TAG:
                    if (customVerticalViewPager != null)
                        customVerticalViewPager.setCurrentItem(mFocusPosition);
                    break;
            }
        }
    };

    /**
     * 移除MSG
     */
    public void removeHandlerMsg() {
        if (handler.hasMessages(MSG_TAG))
            handler.removeMessages(MSG_TAG);
    }
}
