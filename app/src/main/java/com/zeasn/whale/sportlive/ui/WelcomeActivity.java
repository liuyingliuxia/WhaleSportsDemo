package com.zeasn.whale.sportlive.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lolinico.technical.open.parent.BaseActivity;
import com.lolinico.technical.open.parent.BaseParentActivity;
import com.lolinico.technical.open.parent.BasePresenter;
import com.zeasn.whale.sportlive.R;
import com.zeasn.whale.sportlive.presenter.AllPresenter;

import butterknife.BindView;

public class WelcomeActivity extends BaseParentActivity {

    @BindView(R.id.conWelcomeBg)
    ConstraintLayout constraintLayout;

    @Override
    public BasePresenter bindPresenter() {
        return new AllPresenter(this);
    }

    @Override
    public int getContentId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
        if (constraintLayout != null)
            constraintLayout.postDelayed(() -> {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }, 1000);
    }

}