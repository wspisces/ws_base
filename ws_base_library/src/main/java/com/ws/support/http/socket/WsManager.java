package com.ws.support.http.socket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * 描述信息
 *
 * @author ws
 * 2020/9/18 17:10
 * 修改人：ws
 */
public class WsManager implements IWsManager
{
    private final static int          RECONNECT_INTERVAL = 10 * 1000;    //重连自增步长
    private final static long         RECONNECT_MAX_TIME = 120 * 1000;   //最大重连间隔
    private static       WsManager    mInstance          = null;
    private              Context      mContext;
    private              String       wsUrl;
    private              WebSocket    mWebSocket;
    private              OkHttpClient mOkHttpClient;
    private              Request      mRequest;
    private              int          mCurrentStatus     = WsStatus.DISCONNECTED;     //websocket连接状态
    private              boolean      isNeedReconnect;          //是否需要断线自动重连
    private              boolean      isManualClose      = false;         //是否为手动关闭websocket连接
    private              Lock         mLock;
    private              Handler      wsMainHandler      = new Handler(Looper.getMainLooper());
    private              int          reconnectCount     = 0;   //重连次数
    private Runnable          reconnectRunnable  = () ->
    {
        Log.e("websocket", "服务器重连接中...");
        buildConnect();
    };
    private WebSocketListener mWebSocketListener = new WebSocketListener()
    {

        @Override
        public void onOpen(WebSocket webSocket, final Response response)
        {
            mWebSocket = webSocket;
            setCurrentStatus(WsStatus.CONNECTED);
            EventBus.getDefault().post(new WsEvent(WsStatus.CONNECTED));
            connected();
            if (Looper.myLooper() != Looper.getMainLooper())
            {
                //send("connect");
            } else
            {
                Log.e("websocket", "服务器连接成功");
            }
        }

        @Override
        public void onMessage(WebSocket webSocket, final ByteString bytes)
        {
            if (Looper.myLooper() != Looper.getMainLooper())
            {
                wsMainHandler.post(() -> Log.e("websocket", "WsManager-----onMessage"));
            } else
            {
                Log.e("websocket", "WsManager-----onMessage");
            }
        }

        @Override
        public void onMessage(WebSocket webSocket, final String text)
        {

            EventBus.getDefault().post(new WsEvent(WsStatus.CONNECTED,text));
            if (Looper.myLooper() != Looper.getMainLooper())
            {
                wsMainHandler.post(() -> Log.e("websocket", "WsManager-----onMessage="+text));
            } else
            {
                Log.e("websocket", "WsManager-----onMessage");
            }
        }

        @Override
        public void onClosing(WebSocket webSocket, final int code, final String reason)
        {

            if (Looper.myLooper() != Looper.getMainLooper())
            {
                wsMainHandler.post(() -> Log.e("websocket", "服务器连接关闭中"));
            } else
            {
                Log.e("websocket", "服务器连接关闭中");
            }
        }

        @Override
        public void onClosed(WebSocket webSocket, final int code, final String reason)
        {
            EventBus.getDefault().post(new WsEvent(WsStatus.DISCONNECTED));
            if (Looper.myLooper() != Looper.getMainLooper())
            {
                wsMainHandler.post(() -> Log.e("websocket", "服务器连接已关闭"));
            } else
            {
                Log.e("websocket", "服务器连接已关闭");
            }
        }

        @Override
        public void onFailure(WebSocket webSocket, final Throwable t, final Response response)
        {

            try
            {
                tryReconnect();
                Log.e("liusehngjei", "[走的链接失败这里！！！！！！！！！！！！！！！！]");
                if (Looper.myLooper() != Looper.getMainLooper())
                {
                    wsMainHandler.post(() -> Log.e("websocket", "服务器连接失败"));
                } else
                {
                    Log.e("websocket", "服务器连接失败");
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    };


    private WsManager()
    {
    }
    public WsManager(Builder builder)
    {
        mContext = builder.mContext;
        wsUrl = builder.wsUrl;
        isNeedReconnect = builder.needReconnect;
        mOkHttpClient = builder.mOkHttpClient;
        this.mLock = new ReentrantLock();
    }

    public static WsManager getInstance()
    {
        if (null == mInstance)
        {
            synchronized (WebSocketManager.class)
            {
                if (mInstance == null)
                {
                    mInstance = new WsManager();
                }
            }
        }
        return mInstance;
    }

    private void initWebSocket()
    {
        if (mOkHttpClient == null)
        {
            mOkHttpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .build();
        }
        if (mRequest == null)
        {
            mRequest = new Request.Builder()
                    .url(wsUrl)
                    .build();
        }
        mOkHttpClient.dispatcher().cancelAll();
        try
        {
            mLock.lockInterruptibly();
            try
            {
                mOkHttpClient.newWebSocket(mRequest, mWebSocketListener);
            } finally
            {
                mLock.unlock();
            }
        } catch (InterruptedException e)
        {
        }
    }

    @Override
    public WebSocket getWebSocket()
    {
        return mWebSocket;
    }


    @Override
    public synchronized boolean isWsConnected()
    {
        return mCurrentStatus == WsStatus.CONNECTED;
    }

    @Override
    public synchronized int getCurrentStatus()
    {
        return mCurrentStatus;
    }

    @Override
    public synchronized void setCurrentStatus(int currentStatus)
    {
        this.mCurrentStatus = currentStatus;
    }

    @Override
    public void startConnect()
    {
        isManualClose = false;
        buildConnect();
    }

    @Override
    public void stopConnect()
    {
        isManualClose = true;
        disconnect();
    }

    private void tryReconnect()
    {
        if (!isNeedReconnect | isManualClose)
        {
            return;
        }
        Log.e("liusehngjei", "reconnectCount2222222[" + reconnectCount + "]");
        if (!isNetworkConnected(mContext))
        {
            setCurrentStatus(WsStatus.DISCONNECTED);
            Log.e("liusehngjei", "[请您检查网络，未连接]");
//            return;
        }
        setCurrentStatus(WsStatus.RECONNECT);
        Log.e("liusehngjei", "reconnectCount11111111[" + reconnectCount + "]");
        long delay = reconnectCount * RECONNECT_INTERVAL;
//        wsMainHandler.postDelayed(reconnectRunnable, delay > RECONNECT_MAX_TIME ? RECONNECT_MAX_TIME : delay);
        wsMainHandler.postDelayed(reconnectRunnable, 10000);
        Log.e("liusehngjei", "reconnectCount[" + reconnectCount + "]");
        reconnectCount++;

    }

    private void cancelReconnect()
    {
        wsMainHandler.removeCallbacks(reconnectRunnable);
        reconnectCount = 0;
    }

    private void connected()
    {
        cancelReconnect();
    }

    private void disconnect()
    {
        if (mCurrentStatus == WsStatus.DISCONNECTED)
        {
            return;
        }
        cancelReconnect();
        if (mOkHttpClient != null)
        {
            mOkHttpClient.dispatcher().cancelAll();
        }
        if (mWebSocket != null)
        {
            boolean isClosed = mWebSocket.close(WsStatus.CODE.NORMAL_CLOSE, WsStatus.TIP.NORMAL_CLOSE);
            //非正常关闭连接
            if (!isClosed)
            {
                Log.e("websocket", "服务器连接失败");
            }
        }
        setCurrentStatus(WsStatus.DISCONNECTED);
    }

    private synchronized void buildConnect()
    {
        if (!isNetworkConnected(mContext))
        {
            setCurrentStatus(WsStatus.DISCONNECTED);
//            return;
        }
        switch (getCurrentStatus())
        {
            case WsStatus.CONNECTED:
            case WsStatus.CONNECTING:
                break;
            default:
                setCurrentStatus(WsStatus.CONNECTING);
                initWebSocket();
        }
    }

    //发送消息
    @Override
    public boolean sendMessage(String msg)
    {
        return send(msg);
    }

    @Override
    public boolean sendMessage(ByteString byteString)
    {
        return send(byteString);
    }

    private boolean send(Object msg)
    {
        boolean isSend = false;
        if (mWebSocket != null && mCurrentStatus == WsStatus.CONNECTED)
        {
            if (msg instanceof String)
            {
                isSend = mWebSocket.send((String) msg);
            } else if (msg instanceof ByteString)
            {
                isSend = mWebSocket.send((ByteString) msg);
            }
            //发送消息失败，尝试重连
            if (!isSend)
            {
                tryReconnect();
            }
        }
        return isSend;
    }

    //检查网络是否连接
    private boolean isNetworkConnected(Context context)
    {
        if (context != null)
        {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission")
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null)
            {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static final class Builder
    {

        private Context      mContext;
        private String       wsUrl;
        private boolean      needReconnect = true;
        private OkHttpClient mOkHttpClient;

        public Builder(Context val)
        {
            mContext = val;
        }

        public Builder wsUrl(String val)
        {
            wsUrl = val;
            return this;
        }

        public Builder client(OkHttpClient val)
        {
            mOkHttpClient = val;
            return this;
        }

        public Builder needReconnect(boolean val)
        {
            needReconnect = val;
            return this;
        }

        public WsManager build()
        {
            return new WsManager(this);
        }

    }

    //实例化WsMnanger
    /*WsManager wsManager = new WsManager.Builder(getBaseContext()).client(
            new OkHttpClient().newBuilder()
                    .pingInterval(15, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build())
            .needReconnect(true)
            .wsUrl("ws://192.168.3.145:80/MayaCloud_Lightpush//ws")
            .build();*/
    //连接可以直接使用
    /*wsManager.startConnect();*/
    //停止链接使用下面方法
    /*  if (wsManager != null) {
      wsManager.stopConnect();
      wsManager = null;
    }*/
    //onDestory（）里面要记得停止链接
    /*@Override
    protected void onDestroy() {
        if (wsManager != null) {
            wsManager.stopConnect();
            wsManager = null;
        }
    }*/


}


