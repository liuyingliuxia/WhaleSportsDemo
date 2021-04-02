package com.lolinico.technical.open.widget;

import android.app.Activity;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.lolinico.technical.open.adapter.MenuRootAdapter;
import com.lolinico.technical.open.utils.WidgetUtils;
import com.steelbar.R;

import java.util.List;

/**
 * Created by Rico on 2017/12/26.
 */

public abstract class SlidingProcessView extends SlidingMenu {
    CustomMenuView mCustomMenuView;
    final long DELAY = 30;

    public SlidingProcessView(Context context) {
        super(context);
        init(context);
    }

    public abstract MenuRootAdapter setMenuAdapter();

    public abstract int setSlidingContent();

    public abstract double setMenuWidthPreX();

    public abstract void notifyMenuData(RecyclerView recyclerView, List<MenuRootAdapter.DataTree> dataTreeList);

    private void init(Context context) {
        /**设置可以左右滑动的菜单**/
        setMode(SlidingMenu.LEFT);
        /**设置滑动阴影的宽度**/
        setShadowWidthRes(R.dimen.shadow_width);
        /**设置滑动菜单阴影的图像资源**/
        setShadowDrawable(null);
        /**设置渐入渐出效果的值**/
        setFadeDegree(0.35f);
        /**设置触摸屏幕的模式,这里设置为全屏**/
        setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        /**设置下方视图的在滚动时的缩放比例**/
        setBehindScrollScale(0.0f);
        /**设置滑动菜单视图的宽度**/
        mCustomMenuView = new CustomMenuView(getContext(), new OnCustomKeyListener() {
            @Override
            public void doAction(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showContent();
                        }
                    }, DELAY);

                }
            }
        }) {
            @Override
            public MenuRootAdapter getMenuParentAdapter() {
                return setMenuAdapter();
            }
        };
        notifyMenuData(mCustomMenuView.mRecyelerView, mCustomMenuView.getDataTrees());
        setMenu(mCustomMenuView);
        setContent(setSlidingContent());
        WidgetUtils.setViewParams(getContext(), mCustomMenuView, setMenuWidthPreX(), 0);
        setBehindOffsetRes(WidgetUtils.getDm(getContext()).widthPixels - WidgetUtils.getXY(getContext(), setMenuWidthPreX(), 0)[0]);
        ((ViewGroup) ((Activity) context).getWindow().getDecorView()).addView(this);
    }

    public CustomMenuView getCustomMenuView() {
        return mCustomMenuView;
    }

    public interface OnCustomKeyListener {
        void doAction(View v, int keyCode, KeyEvent event);
    }

    public interface onSlidingStateListener {
        void onSlidingStateFocusListener(boolean isClose);
    }
}
