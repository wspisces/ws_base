package com.ws.support.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ArrayAdapter<T> extends BaseAdapter {

	protected List<T> mObjects;
	protected LayoutInflater mInflater;
	protected Context mContext;
	public ArrayAdapter(final Context ctx, final List<T> l) {
		mObjects = l == null ? new ArrayList<T>() : l;
		mInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = ctx;

	}

	@Override
	public int getCount() {
		return mObjects.size();
	}



	@Override
	public T getItem(int position) {
		return mObjects.get(position);
	}

	public int getItemExist(T tiem) {
		return mObjects.indexOf(tiem);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public void add(T item) {
		this.mObjects.add(item);
	}

	public void replace(ArrayList<T> newObjects) {
		if (newObjects == null)
			newObjects = new ArrayList<T>();
		this.mObjects = newObjects;
	}

	public void remove(int position){
		mObjects.remove(position);
		notifyDataSetChanged();
	}

	/**
	 * Adds the specified items at the end of the array.
	 * 
	 * @param items
	 *            The items to add at the end of the array.
	 */
	// public void addAll(T... items) {
	// List<T> values = this.mObjects;
	// for (T item : items) {
	// values.add(item);
	// }
	// this.mObjects = values;
	// }

	/**
	 * 
	 * @param collection
	 */
	public void addAll(Collection<? extends T> collection) {
		if (collection != null) {
			mObjects.addAll(collection);
			notifyDataSetChanged();
		}

	}

	/**
	 * Remove all elements from the list.
	 */
	public void clear() {
		mObjects.clear();
		notifyDataSetChanged();
	}


	public final List<T> getAll() {
		return mObjects;
	}
}
