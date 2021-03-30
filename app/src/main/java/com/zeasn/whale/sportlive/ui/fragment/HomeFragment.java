package com.zeasn.whale.sportlive.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.leanback.widget.HorizontalGridView;

import com.lolinico.technical.open.parent.BaseFragment;
import com.zeasn.whale.sportlive.R;
import com.zeasn.whale.sportlive.adapter.SportStubAdapter;
import com.zeasn.whale.sportlive.ui.dialog.AlertDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:Miracle.Lin
 * Date:2021/3/30
 * Email:miracle.lin@zeasn.com
 * Descripe:
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.flContainer)
    FrameLayout flContainer;
    @BindView(R.id.btnOK)
    Button btnOk;
    @BindView(R.id.llAddTeam)
    LinearLayout llAddTeam;
    @BindView(R.id.llTab)
    LinearLayout llTab;
    @BindView(R.id.hgSport)
    HorizontalGridView hgSport;

    @Override
    public int setContent() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
            llTab.setFocusable(true);
            llTab.setFocusableInTouchMode(true);
            llTab.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    llTab.setWeightSum(1.5f);
                }else {
                    llTab.setWeightSum(1.0f);
                }
            });

        hgSport.setNumRows(1);
        hgSport.setAdapter(new SportStubAdapter());


    }


    @OnClick(R.id.btnOK)
    public void setBtnOkClicked() {
        llAddTeam.setVisibility(View.INVISIBLE);
    }


}
