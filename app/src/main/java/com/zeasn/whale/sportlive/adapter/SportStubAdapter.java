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
import com.zeasn.whale.sportlive.R2;
import com.zeasn.whale.sportlive.bean.BaseBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:Miracle.Lin
 * Date:2021/3/30
 * Email:miracle.lin@zeasn.com
 * Descripe: Sport League Team 三个复用
 */
public class SportStubAdapter extends RecyclerView.Adapter<SportStubAdapter.ViewHolder> {

    List<BaseBean> mObjectList;
    int type;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SportStubAdapter.ViewHolder(((Activity) parent.getContext()).getLayoutInflater().inflate(R.layout.item_sport, parent, false));
    }

    public SportStubAdapter(List<BaseBean> beanList , int mType) {
        mObjectList = beanList;
        type = mType;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.ivSport.setImageResource(((BaseBean)mObjectList.get(position)).getLogoResId());
        holder.vtSportName.setText(((BaseBean)mObjectList.get(position)).getTitleName());
        WidgetUtils.setViewParams(holder.cvSports.getContext(), holder.cvSports, 0.120, 0.100);
        holder.cvSports.setTag(position);
        holder.cvSports.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                holder.flFocusBorder.setVisibility(View.VISIBLE);
            else
                holder.flFocusBorder.setVisibility(View.INVISIBLE);
        });

        holder.cvSports.setOnClickListener(v -> {
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

        @BindView(R2.id.cvSports)
        CardView cvSports;
        @BindView(R2.id.tvSportName)
        TextView vtSportName;
        @BindView(R2.id.ivSport)
        ImageView ivSport;
        @BindView(R2.id.ivNike)
        ImageView ivNike;
        @BindView(R2.id.flFocusBorder)
        FrameLayout flFocusBorder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
