package com.ws.support.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ws.support.widget.recycleview.view.PullLoadMoreRecyclerView;

import java.util.ArrayList;

/**
  * CnBaseRecycleAdapter.class
  * Recycle控件适配器
  * @author Johnny.xu
  * time:2019/2/20
  */
public abstract class CnBaseRecycleAdapter<T, E> extends BaseRecyclerViewAdapter<T> {

    public CnBaseRecycleAdapter(Context ctx) {
        super(ctx, new ArrayList<T>());
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getItemId(), parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        initView(getViewHolder(holder), position);
    }

    public void initRecycleConfig(PullLoadMoreRecyclerView recyclerView, boolean isRefresh, boolean isLoadMore,
                                  PullLoadMoreRecyclerView.PullLoadMoreListener listener) {
        recyclerView.setLinearLayout();
        recyclerView.setOnPullLoadMoreListener(listener);
        recyclerView.setPushRefreshEnable(isRefresh);
        recyclerView.setPullRefreshEnable(isLoadMore);
    }

    public abstract int getItemId();

    public abstract E getViewHolder(RecyclerViewHolder holder);

    public abstract void initView(E e, int position);

    public <Y extends View> Y getViewByViewHolder(RecyclerViewHolder holder, int id) {
        return RecyclerViewHolder.get(holder.itemView, id);
    }

    public int getNoneDataView() {
        return getItemCount() == 0 ? View.VISIBLE : View.GONE;
    }
}
