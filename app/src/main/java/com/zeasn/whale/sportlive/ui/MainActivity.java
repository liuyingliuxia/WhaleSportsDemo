package com.zeasn.whale.sportlive.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zeasn.whale.sportlive.R;
import com.zeasn.whale.sportlive.R2;
import com.zeasn.whale.sportlive.ui.fragment.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R2.id.ivSetting)
    ImageView ivSetting;
    @BindView(R2.id.ivHome)
    ImageView ivHome;//需要有三种状态

    public MainActivity() {
        super(R.layout.activity_main);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.flHomeContainer, new HomeFragment(), "Home")
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