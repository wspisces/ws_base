package com.heinqi.support.base.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.heinqi.support.base.listener.IOnActivityRunPeriodListener;

import java.util.ArrayList;
import java.util.List;

/**
  * BaseFragmentActivity.class
  * 基础页面
  * time:2018/4/9
  */
class BaseFragmentActivity extends AppCompatActivity {

    private Bundle savedInstanceState;

    private List<IOnActivityRunPeriodListener> mOnActivityRunPeriodListenerList = new ArrayList<>();

    /** 设置页面运行周期监听 **/
    protected void setActivityRunPeriodListenerListener(IOnActivityRunPeriodListener listener) {

        if (listener != null) {
            mOnActivityRunPeriodListenerList.add(listener);
            listener.onCreate(savedInstanceState);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.savedInstanceState = savedInstanceState;

        for (IOnActivityRunPeriodListener listener : mOnActivityRunPeriodListenerList) {
            listener.onCreate(savedInstanceState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        for (IOnActivityRunPeriodListener listener : mOnActivityRunPeriodListenerList) {
            listener.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        for (IOnActivityRunPeriodListener listener : mOnActivityRunPeriodListenerList) {
            listener.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        for (IOnActivityRunPeriodListener listener : mOnActivityRunPeriodListenerList) {
            listener.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        for (IOnActivityRunPeriodListener listener : mOnActivityRunPeriodListenerList) {
            listener.onStop();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (mOnActivityRunPeriodListenerList != null) {
            for (IOnActivityRunPeriodListener listener : mOnActivityRunPeriodListenerList) {
                listener.onNewIntent(intent);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (mOnActivityRunPeriodListenerList != null) {
            for (IOnActivityRunPeriodListener listener : mOnActivityRunPeriodListenerList) {
                listener.onRestart();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        for (IOnActivityRunPeriodListener listener : mOnActivityRunPeriodListenerList) {
            listener.onDestroy();
        }

        mOnActivityRunPeriodListenerList.clear();
    }
}
