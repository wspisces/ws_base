package com.ws.base

import android.os.Bundle
import android.widget.TextView
import com.ws.sample.R
import com.ws.support.base.activity.BaseActivity
import com.ws.support.utils.ScreenUtils

class MainActivity : BaseActivity() {


    override fun initData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var tv = findViewById<TextView>(R.id.tv);
        tv.append("分辨率="+ScreenUtils.getScreenWidth(this).toString())
        tv.append(" "+ScreenUtils.getScreenHeight(this)+"\n");
        tv.append("屏幕尺寸="+ScreenUtils.getPingMuSize(this));
        tv.append("\n屏幕DPI="+ScreenUtils.getDPI(this));
    }


    override fun getContentLayout(): Int {
        return R.layout.activity_main
    }

    override fun getToolbarTite(): String {
       return ""
    }
}
