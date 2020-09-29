package com.ws.support.base.eventbus;

/**
 * 消息通知管理
 */

public enum EventMessageType {
    SYNCBASEDATA(0, "同步基础数据完成通知"),
    REFRSH(1, "通知"),
    BUTTONCLICK(2, "按钮点击事件通知");
    public int typeCode;
    public String name;

    EventMessageType(int typeCode, String name) {
        this.typeCode = typeCode;
        this.name = name;
    }
}
