package com.lolinico.technical.open.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.lolinico.technical.open.widget.SlidingProcessView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rico on 2017/12/26.
 */

public abstract class MenuRootAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    LayoutInflater inflater;
    List<DataTree> dataTreeList = new ArrayList<>();
    SlidingProcessView.OnCustomKeyListener customKeyListener;

    public MenuRootAdapter(Context context) {
        this.mContext = context;
        this.inflater = ((Activity) context).getLayoutInflater();
    }

    public void setDataTreeList(List data) {
        dataTreeList = data;
        notifyDataSetChanged();
    }

    public List<DataTree> getDataTreeList() {
        return dataTreeList;
    }

    public void setCustomKeyListener(SlidingProcessView.OnCustomKeyListener listener) {
        this.customKeyListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return setChildViewHolder(parent, viewType);
    }

    public abstract RecyclerView.ViewHolder setChildViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindGroupHolder(MenuRootAdapter menuRootAdapter, RecyclerView.ViewHolder holder, int position, Object o);

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(final View v, final int keyCode, final KeyEvent event) {
                customKeyListener.doAction(v, keyCode, event);
                return false;
            }
        });
        onBindGroupHolder(this, holder, position, dataTreeList.get(position) == null ? null : dataTreeList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataTreeList.size();
    }


    public final static class DataTree {
        private Object groupItem;

        public DataTree(Object groupItem) {
            this.groupItem = groupItem;
        }

        public java.lang.Object getGroupItem() {
            return groupItem;
        }
    }
}