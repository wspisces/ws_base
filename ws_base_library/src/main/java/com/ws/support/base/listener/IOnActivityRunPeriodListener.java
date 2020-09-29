package com.ws.support.base.listener;

import android.content.Intent;
import android.os.Bundle;

/**
  * IOnActivityRunPeriodListener.class
  * 页面运行周期监听器
  * time:2018/4/9
  */
public interface IOnActivityRunPeriodListener {

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onPause();

    void onResume();

    void onStop();

    void onDestroy();

    void onRestart();

    void onNewIntent(Intent intent);
}
