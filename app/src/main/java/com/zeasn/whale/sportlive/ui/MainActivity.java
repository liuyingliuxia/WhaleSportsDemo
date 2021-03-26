package com.zeasn.whale.sportlive.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.zeasn.whale.sportlive.R;
import com.zeasn.whale.sportlive.ui.dialog.AlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ivSetting)
    ImageView ivSetting;
    @BindView(R.id.ivHome)
    ImageView ivHome;
    @BindView(R.id.tbSort)
    TabLayout tbSort;
    @BindView(R.id.flContainer)
    FrameLayout flContainer;

    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    public void initDialog() {
        if (null == alertDialog) {
            alertDialog = new AlertDialog(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null && alertDialog.isShowing())
            alertDialog.dismiss();
    }

    @OnClick(R.id.ivSetting)
    public void setIvSettingClicked(){
        initDialog();
        alertDialog.doShowAction();
    }
}