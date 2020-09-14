package com.heinqi.support.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @anthor wg
 * @time 2017/9/6 14:31
 * @describe 基础类
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    public List<T> dataList;
    public LayoutInflater mInflater;
    public Context mContext;

    public void addAllData(List<T> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        this.dataList.remove(position);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
        notifyDataSetChanged();
    }

    public void removeData(T t) {
        if (t != null) {
            this.dataList.remove(t);
        }
        notifyDataSetChanged();
    }

    public void replaceData(List<T> dataList) {
        this.dataList.clear();
        if (dataList != null) {
            this.dataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    public BaseRecyclerViewAdapter(final Context ctx, final List<T> l) {
        dataList = l == null ? new ArrayList<T>() : l;
        mInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = ctx;

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListen != null) {
                    mOnItemClickListen.onClick(position, dataList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public OnItemClickListen<T> mOnItemClickListen;

    public interface OnItemClickListen<T> {
        void onClick(int position, T item);
    }

    public void setOnItemClickListener(OnItemClickListen<T> mOnItemClickListen) {
        this.mOnItemClickListen = mOnItemClickListen;
    }


}
