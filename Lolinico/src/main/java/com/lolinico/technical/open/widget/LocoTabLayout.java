package com.lolinico.technical.open.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.widget.HorizontalGridView;
import androidx.recyclerview.widget.RecyclerView;

import com.lolinico.technical.open.parent.BaseFragment;
import com.lolinico.technical.open.utils.RLog;
import com.steelbar.R;

import java.io.InputStream;

/**
 * Created by Rico on 2019/6/26.
 * recyclerview + framelayout
 */
public class LocoTabLayout extends FrameLayout {
    FrameLayout mFrameLayout;
    HorizontalGridView mHorizontalGridView;
    RecyclerAdapter mRecyclerAdapter;
    int tabCount;
    int addCount;
    int mFocusPosition;
    int mLastFocusPosition = -1;
    Fragment mCurrentFragment;
    SparseArray<Fragment> mFragmentArray = new SparseArray();

    OnBindFragmentCallBack mOnBindFragmentCallBack;

    public LocoTabLayout(@NonNull Context context) {
        this(context, null);
    }


    public LocoTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("ResourceType")
    public LocoTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LocoTabLayout);

        mHorizontalGridView = new HorizontalGridView(context);
        mRecyclerAdapter = new RecyclerAdapter();
        mHorizontalGridView.setAdapter(mRecyclerAdapter);
        mFrameLayout = new FrameLayout(context);
        mFrameLayout.setId(0x1001);
        addView(mFrameLayout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mHorizontalGridView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Drawable tablayoutDrawable = a.getDrawable(R.styleable.LocoTabLayout_tablayout_background);
        if (tablayoutDrawable != null)
            mHorizontalGridView.setBackground(tablayoutDrawable);
        a.recycle();
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        super.requestChildFocus(child, focused);
        if (child == null || focused == null)
            return;
        if (mOnBindFragmentCallBack != null)
            mOnBindFragmentCallBack.requestChildFocus(child, focused);
    }

    public void setTabLayoutBackground(int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = getContext().getResources().openRawResource(resId);
        mHorizontalGridView.setBackground(new BitmapDrawable(BitmapFactory.decodeStream(is, null, opt)));
    }

    public HorizontalGridView getRcyclerLayout() {
        return mHorizontalGridView;
    }

    public RecyclerView.Adapter getAdapter() {
        return mRecyclerAdapter;
    }

    /**
     * scrollToPosition
     *
     * @param position
     */
    public void scrollToPosition(int position) {
        mHorizontalGridView.scrollToPosition(position);
    }

    /**
     * 一共添加多少fragment
     *
     * @return
     */
    public int getTabCount() {
        return addCount;
    }

    /**
     * 获取mHorizontalGridView当前聚焦child
     *
     * @return
     */
    public View getRecyclerFocusChild() {
        return mHorizontalGridView.getFocusedChild();
    }

    /**
     * 导航栏聚焦
     */
    public void setRecyclerLayoutRequestFocus() {
        RLog.v("FOCUS_HOME setRecyclerLayoutRequestFocus....");
        mHorizontalGridView.requestFocusFromTouch();
    }

    /**
     * 当然item位置
     *
     * @return
     */
    public int getCurrentItem() {
        for (int i = 0; i < mFragmentArray.size(); i++) {
            if (mCurrentFragment == mFragmentArray.valueAt(i))
                return mFragmentArray.keyAt(i);
        }
        return mFocusPosition;
    }

    /**
     * 获取所有fragmentlsit
     *
     * @return
     */
    public SparseArray getFragmentList() {
        return mFragmentArray;
    }

    /**
     * 当前fragment
     *
     * @return
     */
    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public RecyclerView.ViewHolder findViewHolderForAdapterPosition(int position) {
        return mHorizontalGridView.findViewHolderForAdapterPosition(position);
    }

    /**
     * 绑定数据
     *
     * @param count
     */
    public void setPlayList(int count, OnBindFragmentCallBack mOnBindFragmentCallBack) {
        this.mOnBindFragmentCallBack = mOnBindFragmentCallBack;
        if (count == 0)
            return;
        if (mOnBindFragmentCallBack == null) {
            return;
        }
        for (int i = 0; i < count; i++) {
            /**是否需要绑定fragment至viewpager**/
            if (mOnBindFragmentCallBack.isBindGroupFragment(i))
                mFragmentArray.put(i, mOnBindFragmentCallBack.bindGroupFragment(i));
        }
        tabCount = count;
        mRecyclerAdapter.notifyDataSetChanged();
    }

    final int MSG_TAG = 0;
    final int MSG_SELECTED = 1;
    final long MSG_DELAY_TIME = 650;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (((AppCompatActivity) getContext()).isDestroyed())
                return;
            switch (msg.what) {
                case MSG_TAG:
                    changeFragment(mLastFocusPosition < 0 ? null : mFragmentArray.get(mLastFocusPosition), mFragmentArray.get(mFocusPosition));
                    break;
                case MSG_SELECTED:
                    if (mLastFocusPosition >= 0 && mFragmentArray.get(mLastFocusPosition) instanceof BaseFragment)
                        ((BaseFragment) mFragmentArray.get(mLastFocusPosition)).onDismissSelected();
                    if (mFragmentArray.get(mFocusPosition) instanceof BaseFragment)
                        ((BaseFragment) mFragmentArray.get(mFocusPosition)).onFragmentSelected();
                    mOnBindFragmentCallBack.onFragmentSelected(mFocusPosition, mLastFocusPosition);
                    mLastFocusPosition = mFocusPosition;
                    break;
            }
        }
    };

    /**
     * 切换fragment
     *
     * @param fromFragment
     * @param toFragment
     */
    void changeFragment(Fragment fromFragment, Fragment toFragment) {
        try {
            if (((AppCompatActivity) getContext()).isFinishing() || ((AppCompatActivity) getContext()).isDestroyed())
                return;
            if (mCurrentFragment != toFragment)
                mCurrentFragment = toFragment;
            FragmentTransaction ft = ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction();
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
        }catch (Exception e){
            e.printStackTrace();
            RLog.e(e.toString());
        }
    }

    /**
     * 隐藏fragment
     *
     * @param ft
     */
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

    public class RecyclerAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return mOnBindFragmentCallBack.setChildViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
            holder.itemView.setTag(R.id.itemview_holder, holder);
            holder.itemView.setTag(R.id.itemview_position, position);
            mOnBindFragmentCallBack.onBindGroupHolder(this, holder, position, getItemViewType(position));
            holder.itemView.setOnFocusChangeListener(onFocusChangeListener);
        }

        @Override
        public int getItemCount() {
            if (mOnBindFragmentCallBack == null)
                return 0;
            return tabCount;
        }

        @Override
        public int getItemViewType(int position) {
            if (mOnBindFragmentCallBack != null)
                return mOnBindFragmentCallBack.getItemViewType(position, tabCount);
            return super.getItemViewType(position);
        }
    }

    /**
     * 自定义onFocusChangeListener
     */
    View.OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(android.view.View v, boolean hasFocus) {
            int position = (int) v.getTag(R.id.itemview_position);
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag(R.id.itemview_holder);
            mOnBindFragmentCallBack.onFocusChangeAction(holder, position, hasFocus);
            if (hasFocus)
                mFocusPosition = position;
            if (hasFocus && mFrameLayout != null && mOnBindFragmentCallBack.isBindGroupFragment(position)) {
                if (mCurrentFragment != mFragmentArray.get(position)) {
                    if (handler.hasMessages(MSG_TAG))
                        handler.removeMessages(MSG_TAG);
                    handler.sendMessageDelayed(handler.obtainMessage(MSG_TAG), mFragmentArray.get(position) instanceof BaseFragment
                            && ((BaseFragment) mFragmentArray.get(position)).isInitView ? 0 : MSG_DELAY_TIME);
                }
            }
        }
    };

    public interface OnBindFragmentCallBack {
        void requestChildFocus(View child, View focused);

        boolean isBindGroupFragment(int position);

        Fragment bindGroupFragment(int position);

        RecyclerView.ViewHolder setChildViewHolder(ViewGroup parent, int viewType);

        int getItemViewType(int position, int itemCount);

        void onBindGroupHolder(RecyclerAdapter recyclerAdapter, RecyclerView.ViewHolder holder, int position, int viewType);

        void onFocusChangeAction(RecyclerView.ViewHolder holder, int position, boolean hasFocus);

        void onFragmentSelected(int currentItemPosition, int lastItemPosition);
    }

}
