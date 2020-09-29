package com.ws.support.base.impl;

/**
  * IActivityOperation.class
  * 页面基础操作
  * @author Johnny.xu
  * time:2019/3/29
  */
public interface IActivityOperation {

    /**
     * 隐藏请求层
     * @return 当返回值为true的情况下，说明隐藏成功；
     *  否则为请求层不存在或请求层状态为隐藏状态
     */
    boolean hideRequestView();

    /**
     * 显示请求层 请求层文案默认为'请求中...'
     */
    void showRequestView(String prompt);

    /**
     * 隐藏网络错误层
     */
    void hideNetWorkErrorView();

    /**
     * 显示网络错误层
     */
    void showNetWorkErrorView(String prompt, Runnable action);

    /**
     * 清除错误页面回调功能
     */
    void clearAllErrorRunnable();

    /**
     * 是否初始化EventBus
     */
    boolean isInitEventBus();

    /**
     * 是否显示标题栏
     */
    boolean isShowTitleBar();

    /**
     * 是否过滤设置背景
     */
    boolean isPassSettingWindowBackground();

    int getContentViewById();
}
