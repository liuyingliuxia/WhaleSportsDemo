package com.lolinico.technical.open.parent;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lolinico.technical.open.utils.RLog;
import com.zeasn.c.download.hdl.HandlersManager;
import com.zeasn.c.download.hdl.IHandleMessageCallBack;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Rico on 2017/8/4.
 */
public abstract class BaseFragment extends Fragment implements IHandleMessageCallBack {
    public View view;
    Unbinder unbinder;
    String TAG;
    boolean isVisible;
    public boolean isInitView;
    boolean isInitBinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = setContent(inflater);
        unbinder = initBinder(view);
        if (getArguments() != null)
            getInstanceBundle(getArguments());
        init();
        return view;
    }


    public void onKeyDown(int keyCode, KeyEvent event) {
    }

    private static class FragmentHandlerMsgListener extends HandlersManager.SimpleHandlerProxyMsgListener<BaseFragment> {
        public FragmentHandlerMsgListener(BaseFragment fragment) {
            super(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            if (getRefrence() == null) return;
            getRefrence().handleMessage(msg);
        }
    }

    protected Handler obtainMsgHandler() {
        try {
            return HandlersManager.getInstance().obtainHandler(this);
        } catch (HandlersManager.NotReigstHandlerListener notReigstHandlerListener) {
            notReigstHandlerListener.printStackTrace();
        }
        return null;
    }

    protected void removeMsgFromQueue(int what) {
        obtainMsgHandler().removeMessages(what);
    }

    protected Message obtainMsgWithFlag(int what) {
        return obtainMsgHandler().obtainMessage(what);
    }

    protected Message obtainMsgWithFlag(int what, Object o) {
        return obtainMsgHandler().obtainMessage(what, o);
    }

    protected void sendMsg(Message message) {
        message.sendToTarget();
    }

    protected void sendMsgDelay(Message message, long delay) {
        obtainMsgHandler().sendMessageDelayed(message, delay);
    }

    @Override
    public void handleMessage(Message message) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        TAG = this.toString();
        isVisible = isVisibleToUser;
        init();
    }

    void init() {
        if (isInitBinder && !isInitView && (TAG != null && isVisible || TAG == null && !isVisible)) {
            HandlersManager.getInstance().registHandlerListener(this, new FragmentHandlerMsgListener(this));
                Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                    @Override
                    public boolean queueIdle() {
                        RLog.v("init =" + this);
                        isInitView = true;
                        initView(view);
                        requestDataAction();
                        return false;
                    }
                });
        }
    }

    public void getInstanceBundle(Bundle bundle) {
    }

    public abstract View setContent(LayoutInflater inflater);

    public abstract void initView(View view);

    Unbinder initBinder(View view) {
        isInitBinder = true;
        return ButterKnife.bind(this, view);
    }

    /**
     * 配合VIEWPAGER切页监听
     */
    public void onFragmentSelected() {
    }

    /***
     * 上一个被选择页面
     */
    public void onDismissSelected() {
    }

    /**
     * 相关网络请求
     */
    public void requestDataAction() {
    }

    @Override
    public void onDestroyView() {
        isInitView = false;
        HandlersManager.getInstance().unRegistHandlerListener(this);
        view = null;
        if (unbinder != null)
            unbinder.unbind();
        super.onDestroyView();
    }
}
