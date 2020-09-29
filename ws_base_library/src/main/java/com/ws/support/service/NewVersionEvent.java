package com.ws.support.service;

/**
 * 描述信息
 *
 * @author ws
 * 2020/8/12 14:09
 * 修改人：ws
 */
public class NewVersionEvent
{

    public int type = 0;//用于区分不同的页面
    private boolean hasNewVersion  = false;//有新版本
    private boolean downloadFinish = false;//下载完成
    private boolean downloadStart  = false;
    private boolean downloadError  = false;

    private NewVersionEvent()
    {
    }


    private NewVersionEvent(boolean hasNewVersion, int type)
    {
        this.hasNewVersion = hasNewVersion;
        this.type = type;
    }


    public static NewVersionEvent hasNew(boolean hasNewVersion, int type)
    {
        return new NewVersionEvent(hasNewVersion, type);
    }

    public static NewVersionEvent downLoadStart(int type)
    {
        NewVersionEvent event = new NewVersionEvent(true, type);
        event.downloadStart = true;
        return event;
    }

    public static NewVersionEvent downLoadFinish(int type)
    {
        NewVersionEvent event = new NewVersionEvent(true, type);
        event.downloadFinish = true;
        return event;
    }

    public static NewVersionEvent downLoadError(int type)
    {
        NewVersionEvent event = new NewVersionEvent(true, type);
        event.downloadError = true;
        return event;
    }

    public boolean isHasNewVersion()
    {
        return hasNewVersion;
    }


    public boolean isDownloadStart()
    {
        return downloadStart;
    }

    public boolean isDownloadFinish()
    {
        return downloadFinish;
    }

    public boolean isDownloadError()
    {
        return downloadError;
    }
}
