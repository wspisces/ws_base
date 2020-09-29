package com.ws.support.base.listener;

import android.content.Intent;

/**
  * OnActivityForResultCallBack.class
  * 页面跳转回调监听类
  * @author Johnny.xu
  * time:2018/11/30
  */
public abstract class OnActivityForResultCallBack {

    public void onActivityResult(int requestCode, int resultCode, Intent data) {}

    public void onActivityResultOk(Intent data) {}
}
