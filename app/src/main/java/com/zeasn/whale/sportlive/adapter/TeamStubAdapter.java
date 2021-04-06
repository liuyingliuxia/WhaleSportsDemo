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

import com.zeasn.whale.sportlive.R;
import com.zeasn.whale.sportlive.R2;
import com.zeasn.whale.sportlive.bean.BaseBean;
import com.zeasn.whale.sportlive.util.RLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:Miracle.Lin
 * Date:2021/4/6
 * Email:miracle.lin@zeasn.com
 * Descripe:
 */
public class TeamStubAdapter extends RecyclerView.Adapter<TeamStubAdapter.ViewHolder> {

    List<BaseBean> mObjectList;
    //    int type;
    View.OnClickListener itemClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeamStubAdapter.ViewHolder(((Activity) parent.getContext()).getLayoutInflater().inflate(R.layout.item_team, parent, false));
    }

    public TeamStubAdapter(List<BaseBean> beanList) {
        mObjectList = beanList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position > 2) {
            holder.cvTeam.setFocusable(false);
            holder.cvTeam.setFocusableInTouchMode(false);
            holder.flFocusBorder.setVisibility(View.VISIBLE);
            holder.flFocusBorder.setBackgroundColor(holder.flFocusBorder.getContext().getResources().getColor(R.color.half_black));
        } else {
            holder.cvTeam.findFocus();
            holder.cvTeam.requestFocus();
            holder.flFocusBorder.requestFocus();
        }
        holder.ivTeam.setImageResource(((BaseBean) mObjectList.get(position)).getLogoResId());
        holder.tvTeamName.setText(((BaseBean) mObjectList.get(position)).getTitleName());

        holder.cvTeam.setTag(position);
        holder.cvTeam.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                holder.flFocusBorder.setVisibility(View.VISIBLE);
            else
                holder.flFocusBorder.setVisibility(View.INVISIBLE);
        });


        holder.cvTeam.setOnClickListener(v -> {
            RLog.v("holder.cvTeam.setOnClickListener!!!---");
            //加上选中框nike logo
            if (holder.ivNike.getVisibility() == View.INVISIBLE)
                holder.ivNike.setVisibility(View.VISIBLE);
            else
                holder.ivNike.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        return mObjectList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cvTeam)
        CardView cvTeam;
        @BindView(R.id.tvTeamName)
        TextView tvTeamName;
        @BindView(R.id.ivTeam)
        ImageView ivTeam;
        @BindView(R.id.ivNike)
        ImageView ivNike;
        @BindView(R2.id.flFocusBorder)
        FrameLayout flFocusBorder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
