package com.heinqi.support.base.listener;

import android.view.View;

/**
 * 页面操作回调
 *
 * @author Johnny.xu
 *         date 2017/3/15
 */
public interface IActivityOperationCallback {

    /**
     * 隐藏键盘
     */
    void hideKeyboard(View view);

    /**
     * 显示键盘
     */
    void showKeyboard(View view);

    /**
     * 隐藏遮盖层
     * @return 当返回值为true的情况下，说明隐藏成功；
     *  否则为请求层不存在或请求层状态为隐藏状态
     */
    boolean hideCoverView();

    /**
     * 显示遮盖层
     */
    void showCoverView();

    /**
     * 隐藏请求层
     * @return 当返回值为true的情况下，说明隐藏成功；
     *  否则为请求层不存在或请求层状态为隐藏状态
     */
    boolean hideRequestView();

    /**
     * 显示请求层 请求层文案默认为'请求中...'
     */
    void showRequestView();

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
    void showNetWorkErrorView(Runnable action);

    /**
     * 显示遮盖内容层
     */
    void showCoverContentView();

    /**
     * 隐藏遮盖内容层
     * @return 当返回值为true的情况下，说明隐藏成功；
     *  否则为请求层不存在或请求层状态为隐藏状态
     */
    boolean hideCoverContentView();

    /**
     * 初始化覆盖页面
     */
    void initCoverContentView(View view);

    /**
     * 返回
     */
    void goBack();


    /**
     * 退出应用
     */
    void exitApp();

}
