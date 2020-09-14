package com.heinqi.support.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.heinqi.support.utils.ViewMeasureUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:BaseAdapter.java</p>
 * <p>Description:适配器基础类</p>
 * @author Johnny.xu
 * @date 2016年11月9日
 */
public abstract class CnBaseAdapter<T, E> extends android.widget.BaseAdapter {

	private View mEmptyView;
	
	private List<T> data;
	
	protected Context mContext;
	
	/**
	 * 添加数据，默认重新添加，清除之前缓存
	 * @param list
	 */
	public void addAllData(List<T> list) {
		addAllData(list, false);
	}
	
	/**
	 * 添加数据
	 * @param list
	 * @param isAdd true则添加数据并不清除之前数据，否则清除旧数据再次添加新数据
	 */
	public void addAllData(List<T> list, boolean isAdd) {

		if (!isAdd) {
			data.clear();
		} 
		if (list != null && list.size() > 0) {
			data.addAll(list);
		}
		this.notifyDataSetChanged();
	}

	public void addDate(int i, T d) {

		data.add(i, d);
		this.notifyDataSetChanged();
	}

	public void reSetItem(T d) {

		int position = getData().indexOf(d);
		if (position < 0 || position > getCount()) {
			addDate(d);
		} else {
			getData().set(position, d);
		}
	}

	/**
	 * 插入数据
     */
	public void addDate(T t) {
		if (data.contains(t)) {
			data.remove(t);
		}
		data.add(t);
		notifyDataSetChanged();
	}
	
	public List<T> getData() {
		return data;
	}
	
	public CnBaseAdapter(Context context) {
		data =  new ArrayList<T>();
		mContext = context;
	}

	public void initEmptyViewLayout() {
		if (mEmptyView != null) {
			ViewMeasureUtils.initViewVisibilityWithGone(mEmptyView, getCount() == 0);
		}
	}
	
	@Override
	public int getCount() {
		return data != null ? data.size() : 0;
	}

	@Override
	public T getItem(int position) {
		return data != null && position < data.size() && position >= 0 
				? data.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		E vb;
		if (convertView == null) {
			convertView = View.inflate(mContext, getLayout(), null);
			vb = getViewHolder(convertView);
			convertView.setTag(vb);
		} else {
			vb = (E)convertView.getTag();
		}
		initData(position, vb);
		return convertView;
	}
	
	public abstract int getLayout();
	
	public abstract E getViewHolder(View convertView);
	
	public abstract void initData(int position, E vh);

	public int getResColorById(int id) {
		return mContext.getResources().getColor(id);
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		initEmptyViewLayout();
	}

	public void removeData(T t) {
		data.remove(t);
		notifyDataSetChanged();
	}

	public void clearData() {
		data.clear();
		notifyDataSetChanged();
	}
}
