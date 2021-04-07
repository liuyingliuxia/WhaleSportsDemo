package com.zeasn.whale.sportlive.ui.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lolinico.technical.open.utils.AppUtil;
import com.zeasn.whale.sportlive.R;
import com.zeasn.whale.sportlive.R2;
import com.zeasn.whale.sportlive.SportApplication;
import com.zeasn.whale.sportlive.adapter.SportStubAdapter;
import com.zeasn.whale.sportlive.adapter.TeamStubAdapter;
import com.zeasn.whale.sportlive.bean.BaseBean;
import com.zeasn.whale.sportlive.bean.LeagueBean;
import com.zeasn.whale.sportlive.bean.SportBean;
import com.zeasn.whale.sportlive.widget.SelectedAllVerticalView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author:Miracle.Lin
 * Date:2021/4/3
 * Email:miracle.lin@zeasn.com
 * Descripe: 选择 Sports League Team 三个统一使用的页面
 */
public class SelectFragment extends Fragment implements SelectedAllVerticalView.OnChildFocusListener{
    public static final int TYPE_SELECT_SPORT = 0;
    public static final int TYPE_SELECT_LEAGUE = 1;
    public static final int TYPE_SELECT_TEAM = 2;

    private List<BaseBean> baseBeanList = new ArrayList<>();

    @BindView(R2.id.tvSelectWord)
    TextView tvSelectWord;
    @BindView(R2.id.hgSelectItem)
    SelectedAllVerticalView hgSelectItem;
    @BindView(R2.id.tvNextOrDone)
    TextView tvNextOrDone;
    Unbinder unbinder;

    public SelectFragment() {
        super(R.layout.fragment_select_all);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        //根据传过来的 tag 展示相应的页面
        assert this.getTag() != null;
        hgSelectItem.requestFocus();
        switch (this.getTag()) {
            case "Sport":
                tvSelectWord.setText(R.string.select_sports);
                tvNextOrDone.setVisibility(View.GONE);
                String[] mSports = SportApplication.getInstance().getResources().getStringArray(R.array.sports_string);
                int[] resIntSport = AppUtil.getDrawables(R.array.sports_sort);

                for (int i = 0; i < mSports.length; i++) {
                    baseBeanList.add(new SportBean(resIntSport[i], mSports[i]));
                }
//                WidgetUtils.setViewParams(view.getContext(), hgSelectItem, 0.8, 0.4);
                hgSelectItem.setHorizontalSpacing(30);
                hgSelectItem.setNumColumns(4);

                hgSelectItem.setAdapter(new SportStubAdapter(baseBeanList, TYPE_SELECT_SPORT, onClickListener));
                break;
            case "League": {
                tvSelectWord.setText(R.string.select_league);
                tvNextOrDone.setVisibility(View.GONE);
                String[] mLeagueStr = SportApplication.getInstance().getResources().getStringArray(R.array.league_string);
                int[] resIntLeague = AppUtil.getDrawables(R.array.league_sort);

                for (int i = 0; i < mLeagueStr.length; i++) {
                    baseBeanList.add(new LeagueBean(resIntLeague[i], mLeagueStr[i]));
                }
//                WidgetUtils.setViewParams(view.getContext(), hgSelectItem, 0.8, 0.5);
                hgSelectItem.setHorizontalSpacing(20);
                hgSelectItem.setNumColumns(4);

                hgSelectItem.setAdapter(new SportStubAdapter(baseBeanList, TYPE_SELECT_LEAGUE, onClickListener));
                break;
            }
            case "Team": {
                tvSelectWord.setText(R.string.select_team);
                tvNextOrDone.setVisibility(View.VISIBLE);
                tvNextOrDone.setText(R.string.done);
                String[] mLeagueStr = SportApplication.getInstance().getResources().getStringArray(R.array.team_string);
                int[] resIntLeague = AppUtil.getDrawables(R.array.team_sort);

                for (int i = 0; i < mLeagueStr.length; i++) {
                    baseBeanList.add(new LeagueBean(resIntLeague[i], mLeagueStr[i]));
                }
//                WidgetUtils.setViewParams(hgSelectItem.getContext(), hgSelectItem, 0.8, 0.6);
                hgSelectItem.setHorizontalSpacing(20);
                hgSelectItem.setNumColumns(5);

                hgSelectItem.setOnKeyInterceptListener(event -> {
                    if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
                        if (tvNextOrDone != null && tvNextOrDone.getVisibility() == View.VISIBLE) {
                            tvNextOrDone.requestFocusFromTouch();
                            return true;
                        }
                    }
                    return false;
                });
                hgSelectItem.setAdapter(new TeamStubAdapter(baseBeanList));
                hgSelectItem.setOnChildFocusListener(this);
                break;
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tvNextOrDone)
    public void tvNextOrDoneClick() {
        assert this.getTag() != null;
//        if (this.getTag().toString().equals("Sport"))//当前的
//            this.requireActivity().getSupportFragmentManager().beginTransaction()
//                    .setReorderingAllowed(true)
//                    .replace(R.id.flHomeContainer, new SelectFragment(), "League") //下一个
//                    .commit();
//        else if (this.getTag().equals("League"))
//            this.requireActivity().getSupportFragmentManager().beginTransaction()
//                    .setReorderingAllowed(true)
//                    .replace(R.id.flHomeContainer, new SelectFragment(), "Team") //下一个
//                    .commit();
        if (this.getTag().equals("Team"))
            this.requireActivity().getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.flHomeContainer, new GameFragment(), "Game") //下一个
                    .commit();
    }


    View.OnClickListener onClickListener = v -> {
        assert this.getTag() != null;
        if (this.getTag().equals("Sport"))//当前的
            this.requireActivity().getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.flHomeContainer, new SelectFragment(), "League") //下一个
                    .commit();
        else if (this.getTag().equals("League"))
            this.requireActivity().getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.flHomeContainer, new SelectFragment(), "Team") //下一个
                    .commit();
    };

    @Override
    public void OnChildFocus(View view, int position) {
        
    }
}
