package com.lolinico.technical.open.parent;

import android.app.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lolinico.technical.open.entity.Event;
import com.lolinico.technical.open.manager.ActivityManager;
import com.lolinico.technical.open.utils.EventBusUtil;
import com.steelbar.R;
import com.zeasn.c.download.hdl.HandlersManager;
import com.zeasn.c.download.hdl.IHandleMessageCallBack;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.SoftReference;

import butterknife.ButterKnife;

/**
 * Created by Rico on 2018/2/8.
 */

public abstract class BaseActivity<T extends BasePresenter> extends Activity implements IHandleMessageCallBack {
    public static final int APPLICATION_LEV_MSG = 980010;
    private static final String TAG = "BaseActivity";

    public T mPresenter;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        doSavedInstanceState(savedInstanceState);
        ActivityManager.getManager().addActivity(this);
        if (isRegisterEventBus())
            EventBusUtil.register(this);
        HandlersManager.getInstance().registHandlerListener(this, new ActivityHandlerMsgListener(this));
        mPresenter = bindPresenter();
        setPresenterTag();
        initBinder();
        initView();
        requestDataAction();
    }

    private static class ActivityHandlerMsgListener extends HandlersManager.SimpleHandlerProxyMsgListener<BaseActivity> {
        public ActivityHandlerMsgListener(BaseActivity activity) {
            super(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (getRefrence() == null) return;
            getRefrence().handleMessage(msg);
        }
    }

    public Handler obtainMsgHandler() {
        try {
            return HandlersManager.getInstance().obtainHandler(this);
        } catch (HandlersManager.NotReigstHandlerListener notReigstHandlerListener) {
            notReigstHandlerListener.printStackTrace();
            HandlersManager.getInstance().registHandlerListener(this, new ActivityHandlerMsgListener(this));
            try {
                return HandlersManager.getInstance().obtainHandler(this);
            } catch (HandlersManager.NotReigstHandlerListener notReigstHandlerListener1) {
                notReigstHandlerListener1.printStackTrace();
                return null;
            }
        }
    }

    public void removeMsgFromQueue(int what) {
        obtainMsgHandler().removeMessages(what);
    }

    public Message obtainMsgWithFlag(int what) {
        return obtainMsgHandler().obtainMessage(what);
    }

    public void sendMsg(Message message) {
        message.sendToTarget();
    }

    public void sendMsgDelay(Message message, long delay) {
        obtainMsgHandler().sendMessageDelayed(message, delay);
    }

    public void doSavedInstanceState(Bundle savedInstanceState) {
    }

    protected void onCreate(Bundle savedInstanceState, boolean flag) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mPresenter = bindPresenter();
        requestDataAction();
    }

    public Context getmContext() {
        return mContext;
    }

    public BasePresenter getPresenter() {
        return mPresenter;
    }

    public void initBinder() {
        ButterKnife.bind(this);
    }

    void setPresenterTag() {
        (getWindow().getDecorView()).setTag(R.id.TAG_PRESENTER, mPresenter);
        (getWindow().getDecorView()).setTag(R.id.TAG_ROOTVIEW, this);
    }

    @Override
    public void handleMessage(Message message) {
        switch (message.what) {
            case APPLICATION_LEV_MSG:
                if (message.obj instanceof String) {
                    String msg = (String) message.obj;
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    /**
     * prsenter绑定view 必重写
     **/
    public abstract T bindPresenter();

    public abstract int getContentId();

    protected boolean isRegisterEventBus() {
        return false;
    }

    public abstract void initView();

    public abstract void requestDataAction();

    protected static class BaseGlideRequestListener implements RequestListener<Drawable> {

        private SoftReference<ImageView> imageViewSoftReference;

        public BaseGlideRequestListener(ImageView imageView) {
            this.imageViewSoftReference = new SoftReference<>(imageView);
        }

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            // 图片加载失败了，使用默认图显示
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            this.imageViewSoftReference.get().setBackground(resource);
            return false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null) {
            onEventDoAction(event);
        }
    }

    /**
     * 通过Event code分发事件
     *
     * @param event
     */
    protected void onEventDoAction(Event event) {
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.onDestroy();
        if (isRegisterEventBus())
            EventBusUtil.unregister(this);
        HandlersManager.getInstance().unRegistHandlerListener(this);
        ActivityManager.getManager().removeActivity(this);
        super.onDestroy();
    }
}

