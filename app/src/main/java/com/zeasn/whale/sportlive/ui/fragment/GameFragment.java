package com.zeasn.whale.sportlive.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.leanback.widget.HorizontalGridView;

import com.zeasn.whale.sportlive.R;
import com.zeasn.whale.sportlive.SportApplication;
import com.zeasn.whale.sportlive.adapter.GameStubAdapter;
import com.zeasn.whale.sportlive.bean.GameBean;
import com.zeasn.whale.sportlive.bean.TeamBean;
import com.zeasn.whale.sportlive.util.RLog;

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
    LinearLayout tabGameSport;
    @BindView(R.id.hgStartGame)
    HorizontalGridView hgStartGame;
    @BindView(R.id.hgFutureGame)
    HorizontalGridView hgFutureGame;
    @BindView(R.id.tabItem1)
    TextView tabItem1;
    @BindView(R.id.tabItem2)
    TextView tabItem2;
    @BindView(R.id.tabItem3)
    TextView tabItem3;

    String[] mTabGame;
    private List<GameBean> gameBeanList = new ArrayList<>();
    private List<GameBean> gameLiveList = new ArrayList<>();
    private List<TextView> tabItemList = new ArrayList<>();

    public GameFragment() {
        super(R.layout.fragment_game);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        mTabGame = SportApplication.getInstance().getResources().getStringArray(R.array.tab_game);
        for (int i = 0; i < tabItemList.size(); i++) {
            tabItemList.add(tabItem1);
            tabItemList.add(tabItem2);
            tabItemList.add(tabItem3);
        }
        for (TextView tabItem : tabItemList) {
            tabItem.setFocusable(true);
            tabItem.setFocusableInTouchMode(true);
            tabItem.setOnFocusChangeListener((v, hasFocus) -> {
                RLog.v("GameFragment tabItem hasFocus== " + hasFocus);
                if (hasFocus)
                    tabItem.setBackgroundColor(v.getContext().getResources().getColor(R.color.white));
                else
                    tabItem.setBackgroundColor(v.getContext().getResources().getColor(R.color.text_sub_title));
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        TeamBean mLiveTeamA = new TeamBean(R.mipmap.team_fulham, getString(R.string.team_fulham) , 1);
        TeamBean mLiveTeamB = new TeamBean(R.mipmap.tottenham, getString(R.string.ball_tottenham) , 2);
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


}
