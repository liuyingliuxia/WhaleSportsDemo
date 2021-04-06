package com.zeasn.whale.sportlive.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import androidx.leanback.widget.VerticalGridView;

/**
 * Created by Rico on 2018/10/22.
 */
public class SelectedAllVerticalView extends VerticalGridView {
    OnDispatchKeyListener onDispatchKeyListener;
    OnChildFocusListener onChildFocusListener;

    public SelectedAllVerticalView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setOnDispatchKeyListener(OnDispatchKeyListener onDispatchKeyListener) {
        this.onDispatchKeyListener = onDispatchKeyListener;
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        super.requestChildFocus(child, focused);
        if (focused == this)
            return;
        if (onChildFocusListener != null)
            onChildFocusListener.OnChildFocus(focused, getLayoutManager().getPosition(focused));
    }

    public void setOnChildFocusListener(OnChildFocusListener onChildFocusListener) {
        this.onChildFocusListener = onChildFocusListener;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP)
            if (onDispatchKeyListener != null)
                return onDispatchKeyListener.dispatchKeyEvent(event);
        return super.dispatchKeyEvent(event);
    }

    public interface OnDispatchKeyListener {
        boolean dispatchKeyEvent(KeyEvent event);
    }

    public interface OnChildFocusListener {
        void OnChildFocus(View view, int position);
    }
}
