package com.ws.support.http.socket;

import okhttp3.WebSocket;
import okio.ByteString;

/**
 * 描述信息
 *
 * @author ws
 * @date 2020/9/18 17:10
 * 修改人：ws
 */
public interface IWsManager {

    WebSocket getWebSocket();

    void startConnect();

    void stopConnect();

    boolean isWsConnected();

    int getCurrentStatus();

    void setCurrentStatus(int currentStatus);

    boolean sendMessage(String msg);

    boolean sendMessage(ByteString byteString);
}


