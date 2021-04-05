package com.zeasn.whale.sportlive.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.leanback.widget.HorizontalGridView;

import com.google.android.material.tabs.TabLayout;
import com.zeasn.whale.sportlive.R;
import com.zeasn.whale.sportlive.SportApplication;
import com.zeasn.whale.sportlive.adapter.GameStubAdapter;
import com.zeasn.whale.sportlive.bean.GameBean;
import com.zeasn.whale.sportlive.bean.TeamBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author:Miracle.Lin
 * Date:2021/4/5
 * Email:miracle.lin@zeasn.com
 * Descripe:
 */
public class GameFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.tabGameSport)
    TabLayout tabGameSport;
    @BindView(R.id.hgStartGame)
    HorizontalGridView hgStartGame;
    @BindView(R.id.hgFutureGame)
    HorizontalGridView hgFutureGame;
    String[] mTabGame;
    private List<GameBean> gameBeanList = new ArrayList<>();
    private List<GameBean> gameLiveList = new ArrayList<>();

    public GameFragment() {
        super(R.layout.fragment_game);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

    }

    @Override
    public void onStart() {
        super.onStart();
        mTabGame = SportApplication.getInstance().getResources().getStringArray(R.array.tab_game);
        tabGameSport.setSelectedTabIndicator(0);
        for (int i = 0; i < mTabGame.length; i++) {
            TabLayout.Tab tab = tabGameSport.newTab();
            tab.setTag(i);
            tab.setText(mTabGame[i]);
            tabGameSport.addTab(tab);
        }
        tabGameSport.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view instanceof TextView) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(SportApplication.getInstance(), R.color.white));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view instanceof TextView) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(SportApplication.getInstance(), R.color.text_sub_title));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        TeamBean mLiveTeamA = new TeamBean(R.mipmap.team_fulham, getString(R.string.team_fulham));
        TeamBean mLiveTeamB = new TeamBean(R.mipmap.tottenham, getString(R.string.ball_tottenham));
        GameBean mLiveGame = new GameBean(getString(R.string.live_score), mLiveTeamA, mLiveTeamB, true);
        gameLiveList.add(mLiveGame);
        //已经开始的比赛
        hgStartGame.setHorizontalSpacing(100);
        hgStartGame.setNumRows(1);
        hgStartGame.setAdapter(new GameStubAdapter(gameLiveList));

        //还未开始的比赛

        TeamBean mTeamA1 = new TeamBean(R.mipmap.burnley, getString(R.string.ball_burnley));
        TeamBean mTeamB1 = new TeamBean(R.mipmap.arsenal, getString(R.string.team_arsenal));
        GameBean mGame1 = new GameBean(getString(R.string.time1), mTeamA1, mTeamB1, false);

        TeamBean mTeamA2 = new TeamBean(R.mipmap.sheffield_united, getString(R.string.ball_sheffield));
        TeamBean mTeamB2 = new TeamBean(R.mipmap.southampton, getString(R.string.team_southampton));
        GameBean mGame2 = new GameBean(getString(R.string.time2), mTeamA2, mTeamB2, false);

        TeamBean mTeamA3 = new TeamBean(R.mipmap.crystal_palace, getString(R.string.team_crystal));
        TeamBean mTeamB3 = new TeamBean(R.mipmap.team_liverpool, getString(R.string.team_liverpool));
        GameBean mGame3 = new GameBean(getString(R.string.time3), mTeamA3, mTeamB3, false);

        gameBeanList.add(mGame1);
        gameBeanList.add(mGame2);
        gameBeanList.add(mGame3);
        hgFutureGame.setHorizontalSpacing(100);
        hgFutureGame.setNumRows(1);
        hgFutureGame.setAdapter(new GameStubAdapter(gameBeanList));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * 自定义Tab的View
     * @param currentPosition
     * @return
     */
    private View getTabView(int currentPosition) {
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.tab_textview, null);
        TextView textView = (TextView) view.findViewById(R.id.tvTab);
        textView.setText(mTabGame[currentPosition]);
        return view;
    }

}
