package com.zeasn.whale.sportlive.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lolinico.technical.open.utils.WidgetUtils;
import com.zeasn.whale.sportlive.R;
import com.zeasn.whale.sportlive.bean.GameBean;
import com.zeasn.whale.sportlive.bean.TeamBean;
import com.zeasn.whale.sportlive.ui.dialog.AlertDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:Miracle.Lin
 * Date:2021/4/5
 * Email:miracle.lin@zeasn.com
 * Descripe:
 */
public class GameStubAdapter extends RecyclerView.Adapter<GameStubAdapter.ViewHolder> {

    List<GameBean> mObjectList;
    AlertDialog myAlertDialog;

    @NonNull
    @Override
    public GameStubAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameStubAdapter.ViewHolder(((Activity) parent.getContext()).getLayoutInflater().inflate(R.layout.item_game, parent, false));
    }

    public GameStubAdapter(List<GameBean> beanList) {
        mObjectList = beanList;

    }

    @Override
    public void onBindViewHolder(@NonNull GameStubAdapter.ViewHolder holder, int position) {

        WidgetUtils.setViewParams(holder.cvGame.getContext(), holder.cvGame, 0.33, 0.156);
        WidgetUtils.setViewParams(holder.ivTeamA.getContext(), holder.ivTeamA, 0.06, 0.06);
        WidgetUtils.setViewParams(holder.ivTeamB.getContext(), holder.ivTeamB, 0.06, 0.06);
        TeamBean teamA = (TeamBean) (mObjectList.get(position).getTeamA());
        TeamBean teamB = (TeamBean) (mObjectList.get(position).getTeamB());
        GameBean gameBean = mObjectList.get(position);
        holder.ivTeamA.setImageResource(teamA.getLogoResId());
        holder.ivTeamB.setImageResource(teamB.getLogoResId());
        holder.tvTeamA.setText(teamA.getTitleName());
        holder.tvTeamB.setText(teamB.getTitleName());
        if (gameBean.getStatus()) {//isLive
            holder.tvVs.setText(holder.tvVs.getContext().getString(R.string.score_to_score, teamA.getGtScore(), teamB.getGtScore()));
        }
        holder.tvTimeOrScore.setText(mObjectList.get(position).getGameName());
        holder.tvGameName.setText(holder.tvGameName.getContext().getString(R.string.game_name));
        holder.cvGame.setTag(position);
        holder.cvGame.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                holder.flFocusBorder.setVisibility(View.VISIBLE);
            else
                holder.flFocusBorder.setVisibility(View.INVISIBLE);
        });

        holder.cvGame.setOnClickListener(v -> {
            if (myAlertDialog == null)
                myAlertDialog = new AlertDialog(holder.cvGame.getContext());
            if (!myAlertDialog.isShowing())
                myAlertDialog.show();
            //点击后加上提醒
            if (holder.ivAlert.getVisibility() == View.INVISIBLE)
                holder.ivAlert.setVisibility(View.VISIBLE);
            else
                holder.ivAlert.setVisibility(View.INVISIBLE);
        });
        if ((mObjectList.get(position).getStatus()))
            holder.tvLive.setVisibility(View.VISIBLE);
        else
            holder.tvLive.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return mObjectList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cvGame)
        CardView cvGame;
        @BindView(R.id.tvLive)
        TextView tvLive;
        @BindView(R.id.tvTimeOrScore)
        TextView tvTimeOrScore;
        @BindView(R.id.ivAlert)
        ImageView ivAlert;
        @BindView(R.id.ivTeamA)
        ImageView ivTeamA;
        @BindView(R.id.ivTeamB)
        ImageView ivTeamB;
        @BindView(R.id.tvTeamA)
        TextView tvTeamA;
        @BindView(R.id.tvTeamB)
        TextView tvTeamB;
        @BindView(R.id.tvGameName)
        TextView tvGameName;
        @BindView(R.id.tvVS)
        TextView tvVs;

        @BindView(R.id.flFocusBorder)
        FrameLayout flFocusBorder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
