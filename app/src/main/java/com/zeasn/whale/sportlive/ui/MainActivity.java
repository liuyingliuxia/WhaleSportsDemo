package com.zeasn.whale.sportlive.ui;

import androidx.appcompat.app.AppCompatActivity;

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
    private static final int SHOW_ADD_SPORT_DIALOG = 0;
    private static final int SHOW_ALERT_DIALOG = 1;

    @BindView(R.id.ivSetting)
    ImageView ivSetting;
    @BindView(R.id.ivHome)
    ImageView ivHome;
    @BindView(R.id.flContainer)
    FrameLayout flContainer;
    @BindView(R.id.btnOK)
    Button btnOk;
    @BindView(R.id.llAddTeam)
    LinearLayout llAddTeam;
    @BindView(R.id.llTab)
    LinearLayout llTab;

    AlertDialog alertDialog;

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
        initDialog(SHOW_ADD_SPORT_DIALOG);
        llTab.setFocusable(true);
        llTab.setFocusableInTouchMode(true);
        llTab.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                llTab.setWeightSum(1.5f);
            }else {
                llTab.setWeightSum(1.0f);
            }
        });
    }

    @Override
    public void requestDataAction() {

    }

    public void initDialog(int dialogNum) {
        if (dialogNum == SHOW_ALERT_DIALOG && null == alertDialog)
            alertDialog = new AlertDialog(this);
//        else if (dialogNum == SHOW_ADD_SPORT_DIALOG && null == addTeamDialog)
//            addTeamDialog = new AddTeamDialog(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null && alertDialog.isShowing())
            alertDialog.dismiss();
    }

    @OnClick(R.id.ivSetting)
    public void setIvSettingClicked() {
        initDialog(SHOW_ALERT_DIALOG);
        alertDialog.doShowAction();
    }

    @OnClick(R.id.btnOK)
    public void setBtnOkClicked() {
        llAddTeam.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                ivHome.findFocus();
                break;
        }


        return super.onKeyDown(keyCode, event);
    }
}