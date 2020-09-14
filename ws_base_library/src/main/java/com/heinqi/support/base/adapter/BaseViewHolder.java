package com.heinqi.support.base.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
  * BaseViewHolder.class
  * 基础复用对象
  * time:2017/7/28
  */
public class BaseViewHolder {

    public View mBaseView;

    public BaseViewHolder(View view) {
        mBaseView = view;
    }

    protected TextView findTextViewById(int id) {
        return (TextView) mBaseView.findViewById(id);
    }

    protected ImageView findImageViewById(int id) {
        return (ImageView) mBaseView.findViewById(id);
    }

    protected ListView findListViewById(int id) {
        return (ListView) mBaseView.findViewById(id);
    }

    protected View findGroupViewById(int id) {
        return mBaseView.findViewById(id);
    }

}
