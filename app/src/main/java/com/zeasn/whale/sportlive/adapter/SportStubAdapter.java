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
import com.zeasn.whale.sportlive.ui.fragment.SelectFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:Miracle.Lin
 * Date:2021/3/30
 * Email:miracle.lin@zeasn.com
 * Descripe: Sport League  2个复用
 */
public class SportStubAdapter extends RecyclerView.Adapter<SportStubAdapter.ViewHolder> {

    List<BaseBean> mObjectList;
    int type;
    View.OnClickListener itemClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SportStubAdapter.ViewHolder(((Activity) parent.getContext()).getLayoutInflater().inflate(R.layout.item_sport, parent, false));
    }

    public SportStubAdapter(List<BaseBean> beanList, int mType, View.OnClickListener onClickListener) {
        mObjectList = beanList;
        type = mType;
        itemClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        if (type == SelectFragment.TYPE_SELECT_SPORT) {
//            RLog.v("onBindViewHolder = TYPE_SELECT_SPORT");
//            WidgetUtils.setViewMarginsParams(holder.cvSports.getContext(), holder.cvSports, 0.120, 0.100, 0, 0, 0, 0);
//        } else if (type == SelectFragment.TYPE_SELECT_LEAGUE) {
//            RLog.v("onBindViewHolder = TYPE_SELECT_LEAGUE");
//            WidgetUtils.setViewMarginsParams(holder.cvSports.getContext(), holder.cvSports, 0.050, 0.100, 0, 0, 0, 0);
//        }

        if (position != 0 && type != SelectFragment.TYPE_SELECT_TEAM || position > 2) {
            holder.cvSports.setFocusable(false);
            holder.cvSports.setFocusableInTouchMode(false);
            holder.flFocusBorder.setVisibility(View.VISIBLE);
            holder.flFocusBorder.setBackgroundColor(holder.flFocusBorder.getContext().getResources().getColor(R.color.half_black));
        } else {
            holder.cvSports.findFocus();
            holder.cvSports.requestFocus();
            holder.flFocusBorder.requestFocus();
        }
        holder.ivSport.setImageResource(((BaseBean) mObjectList.get(position)).getLogoResId());
        holder.vtSportName.setText(((BaseBean) mObjectList.get(position)).getTitleName());

        holder.cvSports.setTag(position);
        holder.cvSports.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                holder.flFocusBorder.setVisibility(View.VISIBLE);
            else
                holder.flFocusBorder.setVisibility(View.INVISIBLE);
        });

        if (type != SelectFragment.TYPE_SELECT_TEAM) {
            holder.cvSports.setOnClickListener(itemClickListener);
        } else {
            holder.cvSports.setOnClickListener(v -> {
                //加上选中框nike logo
                if (holder.ivNike.getVisibility() == View.INVISIBLE)
                    holder.ivNike.setVisibility(View.VISIBLE);
                else
                    holder.ivNike.setVisibility(View.INVISIBLE);
            });
        }
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
