package com.ws.support.http.socket;

/**
 * 描述信息
 *
 * @author ws
 * 2020/9/28 15:20
 * 修改人：ws
 */
public class WsEvent
{
    public String content;
    public int    status;

    public WsEvent(int status, String content)
    {
        this.status = status;
        this.content = content;
    }

    public WsEvent(int status)
    {
        this.status = status;
    }

}
