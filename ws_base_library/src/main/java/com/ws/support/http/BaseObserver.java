package com.ws.support.http;

import android.content.Context;


import com.ws.support.utils.NetWorkUtils;
import com.ws.support.utils.StringUtils;
import com.ws.support.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<T>
{
    private boolean         mNeedShowDialog = true;
    private ProgressHandler mProgressHandler;
    private Context         context;

    public BaseObserver(Context context)
    {
        this.mProgressHandler = new ProgressHandler(context, false);
        this.context = context;
    }

    public BaseObserver(Context context, boolean dialogShow)
    {
        this.mNeedShowDialog = dialogShow;
        this.context = context;
        if (dialogShow)
            this.mProgressHandler = new ProgressHandler(context, false);
    }

    @Override
    public void onSubscribe(Disposable d)
    {
        if (!NetWorkUtils.checkNetworkAvailable())
        {
            ToastUtils.error("网络不通");
            onComplete();
            return;
        }
        if (mNeedShowDialog && mProgressHandler != null)
        {
            mProgressHandler.showProgressDialog();
        }
    }

    @Override
    public void onNext(T o)
    {
        if (o instanceof ResultTO && ((ResultTO) o).getStatus() == 401)
        {
            ToastUtils.error("401异常");
            return;
        }
        onSuccess(o);
    }

    @Override
    public void onError(Throwable e)
    {
        String errorMsg = e.getMessage();
        if (StringUtils.isNotEmptyWithNull(errorMsg))
        {
            if (errorMsg.contains("Failed to connect to")
                    || errorMsg.contains("Connection refused"))
            {
                ToastUtils.error("连接服务器失败");
            } else if (errorMsg.contains("Connection timed out"))
            {
                ToastUtils.error("请求超时");
            } else if (errorMsg.contains("Unable to resolve host ")){
                ToastUtils.error("请检查网络");
            }else
            {
                ToastUtils.error(errorMsg);
            }
        } else
        {
            ToastUtils.error("请求失败");
        }
        onFailed(e);
        if (mProgressHandler != null)
            mProgressHandler.disMissProgressDialog();
    }

    @Override
    public void onComplete()
    {
        if (mProgressHandler != null)
            mProgressHandler.disMissProgressDialog();
    }


    public abstract void onSuccess(T o);

    public abstract void onFailed(Throwable e);
}
