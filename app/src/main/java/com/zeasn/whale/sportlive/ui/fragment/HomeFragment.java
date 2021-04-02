package com.zeasn.whale.sportlive.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.leanback.widget.HorizontalGridView;

import com.zeasn.whale.sportlive.R;
import com.zeasn.whale.sportlive.adapter.SportStubAdapter;
import com.zeasn.whale.sportlive.ui.dialog.AlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:Miracle.Lin
 * Date:2021/3/30
 * Email:miracle.lin@zeasn.com
 * Descripe:
 */
public class HomeFragment extends Fragment {
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

    public HomeFragment() {
        super(R.layout.fragment_home);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        initView(view);

    }

    public void initView(View view) {
        llTab.setFocusable(true);
        llTab.setFocusableInTouchMode(true);
        llTab.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                llTab.setWeightSum(1.5f);
            } else {
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
