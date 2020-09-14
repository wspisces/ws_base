package com.heinqi.support.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.heinqi.support.base.listener.ViewOperationListener;


/**
 * <p>Title:CnBaseView.java</p>
 * <p>Description:初始View基础类</p>
 *
 * @author Johnny.xu
 *         date 2017年1月23日
 */
public class CnBaseViewGroup extends FrameLayout implements ViewOperationListener {

    private View mShowView;

    public CnBaseViewGroup(Context context) {
        this(context, null);
    }

    public CnBaseViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (getContentViewId() != 0) {
            mShowView = View.inflate(getContext(), getContentViewId(), null);
        }

        if (mShowView != null) {
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            mShowView.setLayoutParams(layoutParams);
            addView(mShowView);
            initView(mShowView);
        }

        initParams(attrs);
    }

    public View getChildView() {
        return mShowView;
    }

    protected void initView(View view) {
    }

    protected int getContentViewId() {
        return 0;
    }

    protected void onDestory() {

    }

    protected int getResColorById(int id) {
        return getResources().getColor(id);
    }

    @Override
    public void initPaint() {}

    @Override
    public void initParams(AttributeSet attrs) {

    }
}
