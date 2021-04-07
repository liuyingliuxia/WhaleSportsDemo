package com.zeasn.whale.sportlive.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zeasn.whale.sportlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:Miracle.Lin
 * Date:2021/4/7
 * Email:miracle.lin@zeasn.com
 * Descripe:
 */
public class TabStubAdapter extends RecyclerView.Adapter<TabStubAdapter.ViewHolder> {

    String[] mObjectList;

    @NonNull
    @Override
    public TabStubAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TabStubAdapter.ViewHolder(((Activity) parent.getContext()).getLayoutInflater().inflate(R.layout.item_tab, parent, false));
    }

    public TabStubAdapter(String[] beanList) {
        mObjectList = beanList;
    }

    @Override
    public void onBindViewHolder(@NonNull TabStubAdapter.ViewHolder holder, int position) {
        holder.tvTab.setText(mObjectList[position]);
        if (position != 2)
            holder.tvTab.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    holder.tvTab.setTextColor(v.getContext().getResources().getColor(R.color.white));
                } else
                    holder.tvTab.setTextColor(v.getContext().getResources().getColor(R.color.text_sub_title));
            });

        else {
            holder.tvTab.setFocusableInTouchMode(false);
            holder.tvTab.setFocusable(false);
        }
    }

    @Override
    public int getItemCount() {
        return mObjectList.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTab)
        TextView tvTab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
