package com.ws.support.base.view.entity;

import android.view.View;

/**
  * BaseViewGroupModel.class
  * 基础布局控件模型
  * @author Johnny.xu
  * time:2018/11/29
  */
public class BaseViewGroupModel {

    public BaseViewGroupModel() {

    }

    public BaseViewGroupModel(View view) {

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        viewWidth = view.getMeasuredWidth();
        viewHeight = view.getMeasuredHeight();
    }

    public BaseViewGroupModel(View view, int x, int y) {

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        viewWidth = view.getMeasuredWidth();
        viewHeight = view.getMeasuredHeight();

        left = x;
        top = y;
        right = x + viewWidth;
        bottom = y + viewHeight;
    }

    public BaseViewGroupModel(View view, int x, int y, int maxWidth, int beginX, int beginY) {

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
