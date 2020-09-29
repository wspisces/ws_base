package com.ws.base.sample

import android.os.Bundle
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.ws.sample.R
import com.ws.support.base.activity.BaseActivity
import com.ws.support.http.socket.WsEvent
import com.ws.support.http.socket.WsManager
import com.ws.support.http.socket.WsStatus
import com.ws.support.utils.StringUtils
import com.ws.support.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_web_socket.*
import okhttp3.OkHttpClient
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.TimeUnit

class WebSocketActivity : BaseActivity() {
    var wsManager: WsManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wsManager = WsManager.Builder(baseContext).client(
                OkHttpClient().newBuilder()
                        .pingInterval(10, TimeUnit.SECONDS) // 设置 PING 帧发送间隔
                        .writeTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(5, TimeUnit.SECONDS)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build())
                .needReconnect(true)
                //ws://echo.websocket.org 测试地址,请勿删除此行
                .wsUrl("ws://echo.websocket.org") //测试地址
                .build()
    }

    override fun initData() {
        ButterKnife.bind(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        wsManager?.stopConnect()
        wsManager = null
    }


    override fun getContentLayout(): Int {
        return R.layout.activity_web_socket
    }

    override fun getToolbarTite(): String {
        return "WebSocket"
    }

    override fun enableEventBus(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun WsEvent(event: WsEvent) {
        when (event.status) {
            WsStatus.CONNECTED -> {
                if (StringUtils.isNotEmptyWithNull(event.content)) {
                    tv.append(event.content)
                    tv.append("\n")
                }else{
                    tv.append("已连接\n")
                }
            }
            WsStatus.DISCONNECTED -> {
                tv.append("连接断开\n")
            }
        }
    }


    @OnClick(R.id.btn_connect, R.id.btn_dissconnect, R.id.btn_send)
    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_connect -> {
                wsManager?.startConnect()
            }
            R.id.btn_dissconnect -> {
                wsManager?.stopConnect()
            }
            R.id.btn_send -> {
                if (StringUtils.isEmptyWithNull(et.text.toString())) {
                    ToastUtils.error("请输入发送内容")
                    return
                }
                wsManager?.sendMessage(et.text.toString())
            }
        }
    }

}