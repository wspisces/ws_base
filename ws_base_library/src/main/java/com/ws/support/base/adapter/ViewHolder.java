package com.ws.support.base.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * @author allen
 * @email jaylong1302@163.com
 * @date 2014-1-15 上午10:31:28
 * @company 富媒科技
 * @version 1.0
 * @description
 */

public class ViewHolder {
	// I added a generic return type to reduce the casting noise in client
	// code
	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}

}
