package com.zeasn.whale.sportlive.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zeasn.whale.sportlive.R;
import com.zeasn.whale.sportlive.R2;
import com.zeasn.whale.sportlive.ui.fragment.GameFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R2.id.ivSetting)
    ImageView ivSetting;
    @BindView(R2.id.ivHome)
    ImageView ivHome;//需要有三种状态

    @BindView(R.id.tvLeftSetting)
    TextView tvSetting;
    @BindView(R.id.tvLeftHome)
    TextView tvHome;

    @BindView(R.id.llHome)
    LinearLayout llHome;

    public MainActivity() {
        super(R.layout.activity_main);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        llHome.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                tvHome.setVisibility(View.VISIBLE);
                tvSetting.setVisibility(View.VISIBLE);
            } else {
                tvHome.setVisibility(View.GONE);
                tvSetting.setVisibility(View.GONE);
            }
        });

        ivHome.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                tvHome.setVisibility(View.VISIBLE);
                tvSetting.setVisibility(View.VISIBLE);
            } else {
                tvHome.setVisibility(View.GONE);
                tvSetting.setVisibility(View.GONE);
            }
        });

        if (savedInstanceState != null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.flHomeContainer, new GameFragment(), "Home")
                    .commit();
            ivHome.setActivated(true);
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