package com.heinqi.support.base.view.entity;

import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;


/**
  * ViewGroupPosition.class
  * 控件位置信息
  * @author Johnny.xu
  * time:2018/11/29
  */
public class ViewGroupPosition {

    public ViewGroupPosition(View view) {

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        viewWidth = view.getMeasuredWidth();
        viewHeight = view.getMeasuredHeight();
    }

    public ViewGroupPosition(View view, int x, int y) {

        view.measure(ViewGroup.MeasureSpec.UNSPECIFIED, ViewGroup.MeasureSpec.UNSPECIFIED);
        viewWidth = view.getMeasuredWidth();
        viewHeight = view.getMeasuredHeight();

        left = x;
        top = y;
        right = x + viewWidth;
        bottom = y + viewHeight;
    }

    public ViewGroupPosition(View view, int x, int y, int width, int height) {

        view.measure(ViewGroup.MeasureSpec.UNSPECIFIED, ViewGroup.MeasureSpec.UNSPECIFIED);
        viewWidth = view.getMeasuredWidth();
        viewHeight = view.getMeasuredHeight();
        Logger.i("viewWidth:" + viewWidth + " width:" + viewHeight);
        left = x;
        top = y;
        right = x + width;
        bottom = y + height;

        if (viewWidth > width || viewHeight > height) {
            view.setLayoutParams(new ViewGroup.LayoutParams(width, height));
            view.measure(width, height);
            viewWidth = view.getMeasuredWidth();
            viewHeight = view.getMeasuredHeight();
        }
    }

    public ViewGroupPosition(View view, int x, int y, int maxWidth, int beginX, int beginY) {

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        viewWidth = view.getMeasuredWidth();
        viewHeight = view.getMeasuredHeight();

        if (x != beginX && x + viewWidth > maxWidth) {
            left = beginX;
            top = beginY;
            right = beginX + viewWidth;
            bottom = beginY + viewHeight;
        } else {
            left = x;
            top = y;
            right = x + viewWidth;
            bottom = y + viewHeight;
        }
    }

    private int viewWidth;
    private int viewHeight;

    private int left;
    private int top;
    private int right;
    private int bottom;

    public int getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }
}
