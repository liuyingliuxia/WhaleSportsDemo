package com.zeasn.whale.sportlive.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zeasn.whale.sportlive.R;
import com.zeasn.whale.sportlive.R2;
import com.zeasn.whale.sportlive.util.RLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author:Miracle.Lin
 * Date:2021/3/30
 * Email:miracle.lin@zeasn.com
 * Descripe:
 */
public class HomeFragment extends Fragment {
    @BindView(R2.id.tvAdd)
    TextView tvAdd;
    @BindView(R2.id.llAddTeam)
    LinearLayout llAddTeam;

    Unbinder unBinder;

    public HomeFragment() {
        super(R.layout.fragment_home);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unBinder = ButterKnife.bind(this , view);
        initView();

    }

    public void initView() {
        tvAdd.findFocus();


    }


    @OnClick(R.id.tvAdd)
    public void setBtnOkClicked() {
        RLog.v(" you click add 'favorite button' ");
        llAddTeam.setVisibility(View.INVISIBLE);
        this.requireActivity().getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.flHomeContainer, new SelectFragment(), "Sport")
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBinder.unbind();
    }
}
