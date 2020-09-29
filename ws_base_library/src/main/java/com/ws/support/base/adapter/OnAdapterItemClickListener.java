package com.ws.support.base.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * RecycleView Adapter 单击事件监听
 *
 * @author ws
 * @date 2020/8/20 16:28
 * 修改人：ws
 */
public interface OnAdapterItemClickListener {
    void onItemClick(RecyclerView.ViewHolder holder, int position);
    void onItemChildClick(RecyclerView.ViewHolder holder, View view , int position);
}
