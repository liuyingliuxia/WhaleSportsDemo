package com.lolinico.technical.open.widget;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.lolinico.technical.open.utils.RLog;

import java.util.HashMap;

/**
 * Created by Devin.F on 2018/12/7.
 */
public class AccountFrameLayout extends FrameLayout {
    FragmentManager manager;
    FragmentTransaction transaction;
    HashMap<String, Fragment> cacheFragments = new HashMap<>();
    HashMap<String, Boolean> cacheBoolean = new HashMap<>();

    public AccountFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Fragment getSingleFragment(Fragment fragment) {
        Fragment needCallBackFragment = null;
        if (null == cacheFragments.get(fragment.getClass().getName())) {
            RLog.v("添加fragmentName>>>" + fragment.getClass().getName());
            cacheFragments.put(fragment.getClass().getName(), fragment);
        } else {
            RLog.v("不添加fragmentName====" + fragment.getClass().getName());
            needCallBackFragment = cacheFragments.get(fragment.getClass().getName());
        }
        return null == needCallBackFragment ? fragment : needCallBackFragment;
    }


    public boolean isExist(Fragment fragment) {
        boolean isExist = null != cacheBoolean.get(fragment.getClass().getName());
        if (!isExist)
            cacheBoolean.put(fragment.getClass().getName(), true);
        return isExist;
    }

    public void setTransaction(FragmentTransaction transaction) {
        this.transaction = transaction;
    }

    public FragmentTransaction getTransaction() {
        return transaction;
    }

    public FragmentManager getManager() {
        return manager;
    }

    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }
}
