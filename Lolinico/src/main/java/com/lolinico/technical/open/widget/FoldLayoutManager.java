package com.lolinico.technical.open.widget;

import android.graphics.Rect;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.bumptech.glide.Glide;
import com.lolinico.technical.open.utils.RLog;


public class FoldLayoutManager extends RecyclerView.LayoutManager {
    int lastDy = 0;
    int maxScroll = -1;
    int lastItemCount = 0;
    int mFocusPosition = 0;
    int verticalScrollOffset = 0;
    boolean isNeedAutoFocusable;
    boolean isNeedRestParamsable;
    boolean isNeedSaveState;
    boolean needSnap = false;
    DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(4f);
    DecelerateInterpolator quickyDecelerateInterpolator = new DecelerateInterpolator(22f);
    SparseArray<Rect> locationRects = new SparseArray<>();
    SparseArray<View> locationViews = new SparseArray<>();
    SparseBooleanArray attachedItems = new SparseBooleanArray();

    View mFocusChildView;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.Recycler recycler;
    OnCompleteLayoutListener onCompleteLayoutListener;

    boolean isNotifyState;

    public interface OnCompleteLayoutListener {
        void onCompleteLayout();

        void onEmptyLayout();
    }

    public FoldLayoutManager(final RecyclerView view) {
        this.recyclerView = view;
        setAutoMeasureEnabled(true);
        addRecyclerScrollListener();
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void setOnCompleteLayoutListener(OnCompleteLayoutListener onCompleteLayoutListener) {
        this.onCompleteLayoutListener = onCompleteLayoutListener;
    }

    public void setNeedAutoFocusable(boolean needAutoFocusable) {
        isNeedAutoFocusable = needAutoFocusable;
    }

    public void setNeedRestParamsable(boolean needRestParamsable) {
        isNeedRestParamsable = needRestParamsable;
    }

    public void setNeedSaveState(boolean needSaveState) {
        isNeedSaveState = needSaveState;
    }

    @Override
    public void onAdapterChanged(RecyclerView.Adapter oldAdapter, RecyclerView.Adapter newAdapter) {
        super.onAdapterChanged(oldAdapter, newAdapter);
        this.adapter = newAdapter;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.recycler = recycler;
        if (state.getItemCount() == 0 || state.isPreLayout()) {
            removeAndRecycleAllViews(recycler);
            if (onCompleteLayoutListener != null)
                onCompleteLayoutListener.onEmptyLayout();
            return;
        }
        notifyResetParams(state);
        if (getItemCount() != lastItemCount) {
            long startTime = System.currentTimeMillis();
            detachAndScrapAttachedViews(recycler);
            calculateChildrenSite();
            recycleAndFillView();
            computeMaxScroll();
            setChildFocus(state);
            lastItemCount = getItemCount();
            RLog.v("  onLayoutChildren============ " + (System.currentTimeMillis() - startTime));
            if (onCompleteLayoutListener != null)
                onCompleteLayoutListener.onCompleteLayout();
        }
    }

    /**
     * recyclerView Scroll滚动监听
     */
    void addRecyclerScrollListener() {
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        Glide.with(recyclerView.getContext()).resumeRequests();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        Glide.with(recyclerView.getContext()).pauseRequests();
                        break;
                }
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < 0 && getChildAt(0) != null)
                /**向上时持续保持聚焦**/
                    if (getChildAt(0) instanceof ViewGroup) {
                        if (((ViewGroup) getChildAt(0)).getFocusedChild() == null)
                            getChildAt(0).requestFocusFromTouch();
                    } else
                        getChildAt(0).requestFocusFromTouch();
            }
        });
    }

    /***
     * 删除后 如果整行没数据 则往前滚一行
     * @param recyclerView
     */
    @Override
    public void onItemsChanged(RecyclerView recyclerView) {
        super.onItemsChanged(recyclerView);
        if (isNeedAutoFocusable && mFocusPosition >= getItemCount()) {
            verticalScrollOffset = verticalScrollOffset - (locationRects.get(mFocusPosition).bottom - locationRects.get(mFocusPosition).top);
            mFocusPosition = mFocusPosition - 1;
        }
    }

    /**
     * NotifyDataChange后需重置高度及聚焦
     *
     * @param state
     */
    void notifyResetParams(RecyclerView.State state) {
        if (state.didStructureChange() && !state.willRunSimpleAnimations() && !state.willRunPredictiveAnimations() && !state.isPreLayout()) {
            lastItemCount = 0;
            if (isNeedRestParamsable) {
                verticalScrollOffset = 0;
                mFocusPosition = 0;
                mFocusChildView = null;
            }
        }
    }

    /***
     * 布局后设置焦点
     */
    void setChildFocus(RecyclerView.State state) {
        if (isNeedAutoFocusable) {
            /**是否需要往上滚动**/
            if (mFocusPosition < getItemCount())
                findViewByPosition(mFocusPosition).requestFocusFromTouch();
        }
    }

    /**
     * 2018-5-7 10:16:04
     * 设置last Child聚焦
     */
    public void requestChlidFocus() {
        if (mFocusChildView != null)
            mFocusChildView.requestFocusFromTouch();
        else if (getChildAt(0) != null)
            getChildAt(0).requestFocusFromTouch();
    }


    /***
     * 初始化时计量ITEM位置
     */
    void calculateChildrenSite() {
        locationRects.clear();
        attachedItems.clear();
        if (adapter == null)
            adapter = recyclerView.getAdapter();
        int tempPosition = getPaddingTop();
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            long startTime = System.currentTimeMillis();
            Rect rect = new Rect();
            View itemView = recycler.getViewForPosition(i);
            locationViews.put(i, itemView);
            measureChildWithMargins(itemView, 0, 0);
            int itemHeight = getDecoratedMeasuredHeight(itemView);
            rect.left = getPaddingLeft();
            rect.top = tempPosition;
            rect.right = getWidth() - getPaddingRight();
            rect.bottom = rect.top + itemHeight;
            locationRects.put(i, rect);
            attachedItems.put(i, false);
            tempPosition = tempPosition + itemHeight;
            RLog.v(i + "  calculateTim============ " + (System.currentTimeMillis() - startTime));
        }
    }

    /**
     * 开始layout子View
     */
    void recycleAndFillView() {
        int itemCount = getItemCount();
        Rect displayRect = new Rect(0, verticalScrollOffset, getWidth(), getHeight() + verticalScrollOffset);
        for (int i = 0; i < itemCount; i++) {
            Rect thisRect = locationRects.get(i);
            if (Rect.intersects(displayRect, thisRect)) {
                View childView = locationViews.get(i);
                addView(childView);
                measureChildWithMargins(childView, View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                layoutItem(childView, locationRects.get(i));
                attachedItems.put(i, true);
            }
        }
    }

    /**
     * 计算可滑动的最大值
     * (itemcount-1)*item高度
     */
    void computeMaxScroll() {
        int itemCount = getItemCount();
        int screenFilledHeight = 0;
        int lastViewHeight = 0;
        for (int i = itemCount - 1; i >= 0; i--) {
            Rect rect = locationRects.get(i);
            if (i == itemCount - 1)
                lastViewHeight = rect.bottom - rect.top;
            screenFilledHeight = screenFilledHeight + (rect.bottom - rect.top);
        }
        maxScroll = screenFilledHeight - lastViewHeight;
    }

    /**
     * 2018-5-4 18:07:58
     * 滚动时 layout子View
     */
    void layoutItemsOnScroll() {
        /**items所在的滚动范围区域**/
        Rect displayRect = new Rect(0, verticalScrollOffset, getWidth(), getHeight() + verticalScrollOffset);
        int firstVisiblePosition = -1, lastVisiblePosition = -1;
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (child == null)
                continue;
            /**真实位置**/
            int position = getPosition(child);
            /**不在滚动范围区域内则移除  但顶部一定保留2个不可见 VIEW**/
            if (!Rect.intersects(displayRect, locationRects.get(position))) {
                removeAndRecycleView(child, recycler);
                attachedItems.put(position, false);
            } else {
                /**该屏幕内可见的最后一个item的真实位置**/
                if (lastVisiblePosition < 0)
                    lastVisiblePosition = position;
                /**该屏幕内可见的第一item 滚动时需时时更新**/
                if (firstVisiblePosition < 0)
                    firstVisiblePosition = position;
                else
                    firstVisiblePosition = Math.min(firstVisiblePosition, position);
                /**更新还在区域内的Item位置**/
                layoutItem(child, locationRects.get(position));
            }
        }
        /**firstVisiblePosition往前的item判断是否在范围区域内  如果在 则布局出来**/
        if (firstVisiblePosition > 0) {
            for (int i = firstVisiblePosition - 1; i >= 0; i--) {
                if (Rect.intersects(displayRect, locationRects.get(i)) &&
                        !attachedItems.get(i)) {
                    notifyOthers(i, true);
                }
            }
        }
        /**lastVisiblePosition往后的item 判断是否在范围区域内 如果在 则布局出来**/
        for (int i = lastVisiblePosition + 1; i < getItemCount(); i++) {
            if (Rect.intersects(displayRect, locationRects.get(i)) &&
                    !attachedItems.get(i)) {
                notifyOthers(i, false);
            }
        }
    }

    /***
     * 做上键处理
     * @param event
     * @return
     */
    long mLastUpTime;
    final long MAX_SRCOLL_G = 0;

    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if (getItemCount() > 0) {
                    long mNowTime = System.currentTimeMillis();
                    if ((mNowTime - mLastUpTime) > MAX_SRCOLL_G) {
                        mLastUpTime = System.currentTimeMillis();
                        if (mFocusPosition == 0 || getPosition(getChildAt(0)) == 0)
                            return false;
                        else {
                            int fristVisPosition = getPosition(getChildAt(0));
                            final int dy = locationRects.get(fristVisPosition - 1).top - locationRects.get(fristVisPosition - 1).bottom;
                            recyclerView.smoothScrollBy(0, dy, quickyDecelerateInterpolator);
                            return true;
                        }
                    } else
                        return true;
                }
        }
        return false;
    }

    @Override
    public void onScrollStateChanged(int state) {
        if (state == RecyclerView.SCROLL_STATE_DRAGGING)
            needSnap = true;
        super.onScrollStateChanged(state);
    }

    /**
     * 复用position对应的View
     */
    void notifyOthers(int position, boolean addViewFromTop) {
        View scrap = recycler.getViewForPosition(position);
        if (addViewFromTop) {
            addView(scrap, 0);
        } else {
            addView(scrap);
        }
        measureChildWithMargins(scrap, 0, 0);
        layoutItem(scrap, locationRects.get(position));
        attachedItems.put(position, true);
    }

    /***
     * layout该item
     * @param child
     * @param rect
     */
    void layoutItem(View child, Rect rect) {
        int topDistance = verticalScrollOffset - rect.top;
        int itemHeight = rect.bottom - rect.top;
        if (topDistance < itemHeight && topDistance > 0) {
            float rate1 = (float) topDistance / itemHeight;
            float rate3 = 1 - rate1 * rate1;
            child.setAlpha(rate3);
            child.setTranslationY(topDistance);
        } else {
            child.setAlpha(1);
            child.setTranslationY(0);
        }
        layoutDecoratedWithMargins(child, rect.left, rect.top - verticalScrollOffset, rect.right, rect.bottom - verticalScrollOffset);
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    /***
     * 重点计算垂直累计偏移量
     * 并实时布局items
     * @param dy
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0 || dy == 0)
            return 0;
        int travel = dy;
        if (dy + verticalScrollOffset < 0) {
            travel = -verticalScrollOffset;
        } else if (dy + verticalScrollOffset > maxScroll) {
            travel = maxScroll - verticalScrollOffset;
        }
        verticalScrollOffset += travel;
        lastDy = dy;
        if (!state.isPreLayout() && getChildCount() > 0) {
            layoutItemsOnScroll();
        }
        return dy;
    }

    /**
     * 需要对上下物理键做处理
     * 子view聚焦时做smoothScroll动作
     * 确保偏移量
     *
     * @param parent
     * @param child
     * @param focused
     * @return
     */
    @Override
    public boolean onRequestChildFocus(RecyclerView parent, final View child, final View focused) {
        scrollToView(child);
        return true;
    }

    public int getmFocusPosition() {
        return mFocusPosition;
    }

    /**
     * 聚焦时滚动处理
     *
     * @param view
     */
    boolean isDrapDownDirection;

    void scrollToView(View view) {
        if (view == null) {
            return;
        }
        int newFocusPosition = getPosition(view);
        if (newFocusPosition != mFocusPosition) {
            isDrapDownDirection = isDrapDownDirection(newFocusPosition);
            if (newFocusPosition == 0 && isDrapDownDirection) {
                return;
            }
            mFocusPosition = newFocusPosition;
            mFocusChildView = view;
            final int dy = isDrapDownDirection ? view.getTop() : view.getTop();
            recyclerView.smoothScrollBy(0, dy, decelerateInterpolator);
        }
    }

    public void restSmooth() {
        verticalScrollOffset = 0;
        layoutItemsOnScroll();
    }

    /**
     * 方向: 上或下
     *
     * @param newFocusPosition
     * @return
     */
    boolean isDrapDownDirection(int newFocusPosition) {
        if (newFocusPosition > mFocusPosition)
            return true;
        else
            return false;
    }

    /**
     * StartSnapHelper
     * 获取Y轴高度
     *
     * @return
     */
    public int getSnapHeight() {
        if (!needSnap) {
            return 0;
        }
        needSnap = false;
        Rect displayRect = new Rect(0, verticalScrollOffset, getWidth(), getHeight() + verticalScrollOffset);
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            Rect itemRect = locationRects.get(i);
            if (displayRect.intersect(itemRect)) {
                if (lastDy > 0) {
                    // scroll变大，属于列表往下走，往下找下一个为snapView
                    if (i < itemCount - 1) {
                        Rect nextRect = locationRects.get(i + 1);
                        return nextRect.top - displayRect.top;
                    }
                }
                return itemRect.top - displayRect.top;
            }
        }
        return 0;
    }

    /***
     * StartSnapHelper
     * 需要做处理的VIEW
     * * @return
     */
    public View findSnapView() {
        if (getChildCount() > 0) {
            return getChildAt(0);
        }
        return null;
    }

}