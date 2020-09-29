package com.ws.support.http.socket;

/**
 * 描述信息
 *
 * @author ws
 * @date 2020/9/18 17:03
 * 修改人：ws
 */
public interface IReceiveMessage {
    void onConnectSuccess();// 连接成功

    void onConnectFailed();// 连接失败

    void onClose(); // 关闭

    void onMessage(String text);
}


