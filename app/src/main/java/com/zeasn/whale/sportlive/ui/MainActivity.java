package com.zeasn.whale.sportlive.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.leanback.widget.HorizontalGridView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zeasn.whale.sportlive.R;
import com.zeasn.whale.sportlive.ui.fragment.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ivSetting)
    ImageView ivSetting;
    @BindView(R.id.ivHome)
    ImageView ivHome;//需要有三种状态

    public MainActivity() {
        super(R.layout.activity_main);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.flHomeContainer,new HomeFragment(), null)
                    .commit();
        }
    }

    public void initView() {

    }


    public void requestDataAction() {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}