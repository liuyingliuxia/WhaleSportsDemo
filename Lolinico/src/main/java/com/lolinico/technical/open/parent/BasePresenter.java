package com.lolinico.technical.open.parent;

import android.content.Context;

/**
 * Created by Rico on 2018/2/8.
 */

public class BasePresenter {
    protected BaseParentActivity mBaseParentActivity;
    public Context context;

    public BasePresenter(BaseParentActivity mBaseParentActivity) {
        initPresenter(mBaseParentActivity);
    }
    public void initPresenter(BaseParentActivity activity) {
        mBaseParentActivity = activity;
        activity.setContentView(activity.getContentId());
        context = activity.getmContext();
    }

    protected void onDestroy() {
    }
}
