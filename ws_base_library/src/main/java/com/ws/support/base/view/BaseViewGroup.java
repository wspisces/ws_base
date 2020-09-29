package com.ws.support.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.ws.support.base.view.entity.ViewGroupPosition;
import com.orhanobut.logger.Logger;

import java.util.Hashtable;

/**
  * BaseViewGroup.class
  * 基础布局控件
  * @author Johnny.xu
  * time:2018/11/29
  */
public abstract class BaseViewGroup extends ViewGroup {

    private boolean isAttachedToWindow;

    protected int widgetWidth;
    protected int widgetHeight;

    protected Hashtable<View, ViewGroupPosition> mViewPositionMap = new Hashtable<>();

    public BaseViewGroup(Context context) {
        super(context);
    }

    public BaseViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Logger.i("BaseViewGroup onMeasure()");
        widgetWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mCount = getChildCount();
        boolean hasValidWidget = false;
        for (int i = 0; i < mCount; i ++) {
            if (getChildAt(i).getVisibility() != View.GONE) {
                hasValidWidget = true;
                break;
            }
        }
        if (mCount == 0 || !hasValidWidget) {
            widgetHeight = 0;
        } else {
            widgetHeight = getMeasureHeight(widthMeasureSpec, heightMeasureSpec);
        }
        Logger.i("图片控件参数 widgetWidth:" + widgetWidth + " widgetHeight:" + widgetHeight);
        setMeasuredDimension(widgetWidth, widgetHeight);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        Logger.i("BaseViewGroup onAttachedToWindow()");

        isAttachedToWindow = true;

        for (View view : mViewPositionMap.keySet()) {
            addView(view);
        }
    }

    @Override
    public void invalidate() {

        if (isAttachedToWindow) {
            super.invalidate();
        }
    }

    @Override
    public void postInvalidate() {

        if (isAttachedToWindow) {
            super.postInvalidate();
        }
    }

    @Override
    public void requestLayout() {

        if (isAttachedToWindow) {
            super.requestLayout();
        }
    }

    protected int getMeasureHeight(int widthMeasureSpec, int heightMeasureSpec) {
        return 0;
    }

    protected ViewGroupPosition getViewPosition(View view) {
        return null;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            ViewGroupPosition position = mViewPositionMap.get(child) == null ? getViewPosition(child) : mViewPositionMap.get(child);
            if (position != null) {
                child.layout(position.getLeft(), position.getTop(), position.getRight(), position.getBottom());
            } else {
                Logger.e("PhotoLayoutView error");
            }
        }
    }

    @Override
    public boolean isAttachedToWindow() {
        return isAttachedToWindow;
    }
}
