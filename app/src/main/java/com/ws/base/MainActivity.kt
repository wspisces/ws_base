package com.ws.base

import android.os.Bundle
import com.ws.sample.R
import com.ws.support.base.activity.BaseActivity

class MainActivity : BaseActivity() {


    override fun initData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun getContentLayout(): Int {
        return R.layout.activity_main
    }

    override fun getToolbarTite(): String {
       return ""
    }
}