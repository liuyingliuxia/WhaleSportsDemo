package com.zeasn.whale.sportlive.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.zeasn.whale.sportlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:Miracle.Lin
 * Date:2021/3/30
 * Email:miracle.lin@zeasn.com
 * Descripe:
 */
public class SportStubAdapter extends RecyclerView.Adapter<SportStubAdapter.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SportStubAdapter.ViewHolder(((Activity) parent.getContext()).getLayoutInflater().inflate(R.layout.item_sport, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivSport.setBackgroundResource(R.mipmap.football);
        holder.vtSportName.setText(R.string.tab_football);
    }

    @Override
    public int getItemCount() {
        return 1; //数据写死
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cvSports)
        CardView cvSports;
        @BindView(R.id.tvSportName)
        TextView vtSportName;
        @BindView(R.id.ivSport)
        ImageView ivSport;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
