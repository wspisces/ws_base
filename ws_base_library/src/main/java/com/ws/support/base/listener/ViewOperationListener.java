package com.ws.support.base.listener;

import android.util.AttributeSet;

/**
 * 自定义View操作接口
 */
public interface ViewOperationListener {

    public void initPaint();

    public void initParams(AttributeSet attrs);
}
