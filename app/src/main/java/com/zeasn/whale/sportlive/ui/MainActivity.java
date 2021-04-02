package com.zeasn.whale.sportlive.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.leanback.widget.HorizontalGridView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.lolinico.technical.open.parent.BaseParentActivity;
import com.lolinico.technical.open.parent.BasePresenter;
import com.zeasn.whale.sportlive.R;
import com.zeasn.whale.sportlive.presenter.AllPresenter;
import com.zeasn.whale.sportlive.ui.dialog.AddTeamDialog;
import com.zeasn.whale.sportlive.ui.dialog.AlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseParentActivity {

    @BindView(R.id.ivSetting)
    ImageView ivSetting;
    @BindView(R.id.ivHome)
    ImageView ivHome;//需要有三种状态


    @Override
    public BasePresenter bindPresenter() {
        return new AllPresenter(this);
    }

    @Override
    public int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        
    }


    @Override
    public void requestDataAction() {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_DPAD_RIGHT:
//                ivHome.findFocus();
//                break;
//        }


        return super.onKeyDown(keyCode, event);
    }
}