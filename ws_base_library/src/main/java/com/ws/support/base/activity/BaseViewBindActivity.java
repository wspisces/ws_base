package com.ws.support.base.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ws.base.R;
import com.ws.base.databinding.ActivityBaseBinding;
import com.ws.support.widget.MyProgressDialogFragment;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;


/**
 * 描述信息
 *
 * @author ws
 * 2020/11/13 11:04
 * 修改人：ws
 */
public abstract class BaseViewBindActivity extends AppCompatActivity
{
    private final static long                     DELAY_TIME = 800;
    protected            ActivityBaseBinding      baseBinding;
    protected            Context                  mContext;
    protected            AppCompatActivity        mActivity;
    private              MyProgressDialogFragment progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        if (isFullScreen())
        {
            //设置无标题
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            //设置全屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        baseBinding = ActivityBaseBinding.inflate(getLayoutInflater());
        setContentView(baseBinding.getRoot());
        initData_();
        initToolbar();
        initView(baseBinding);
    }

    private void initData_()
    {
        mContext = this;
        mActivity = this;
    }

    protected boolean isFullScreen()
    {
        return false;
    }


    /**
     * 隐藏Toolbar
     */
    protected boolean hideToolbar()
    {
        return false;
    }

    /**
     * 是否显示返回按钮
     */
    protected boolean enableNavigation()
    {
        return true;
    }

    /**
     * 设置Toolbar标题
     */
    protected abstract String getToolbarTite();

    /**
     * 初始化Toolbar
     */
    private void initToolbar()
    {
        if (isFullScreen() || hideToolbar())
        {
            baseBinding.toolbar.setVisibility(View.GONE);
            return;
        }
        baseBinding.toolbar.setTitle("");
        if (enableNavigation())
        {
            baseBinding.toolbar.setNavigationIcon(R.drawable.ic_back);
        }
        setSupportActionBar(baseBinding.toolbar);
        setTitle(getToolbarTite());
    }

    /**
     * 设置标题
     */
    protected void setTitle(String title)
    {
        if (title != null)
        {
            baseBinding.title.setText(title);
        }
    }


    /**
     * 获取资源ID
     *
     * @return 布局资源ID
     */
    protected abstract int getLayoutId();

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        hideProgress();
    }


    /**
     * 初始化界面
     *
     * @param bindView 界面绑定对象
     */
    protected abstract void initView(ActivityBaseBinding bindView);

    @SuppressLint("CheckResult")
    protected void deleyActionOnMain(Consumer<Long> back)
    {
        deleyAction(DELAY_TIME, AndroidSchedulers.mainThread(), back);
    }

    @SuppressLint("CheckResult")
    protected void deleyActionOnMain(long time, Consumer<Long> back)
    {
        deleyAction(time, AndroidSchedulers.mainThread(), back);
    }

    @SuppressLint("CheckResult")
    protected void deleyAction(Scheduler scheduler, Consumer<Long> back)
    {
        Observable.timer(DELAY_TIME, TimeUnit.MILLISECONDS)
                .subscribeOn(scheduler)
                .observeOn(scheduler)
                .subscribe(back);
    }

    @SuppressLint("CheckResult")
    protected void deleyAction(long time, Scheduler scheduler, Consumer<Long> back)
    {
        Observable.timer(time, TimeUnit.MILLISECONDS)
                .subscribeOn(scheduler)
                .observeOn(scheduler)
                .subscribe(back);
    }

    /**
     * 公用组件：进度条
     */
    protected void initProgress(String msg)
    {
        try
        {
            if (null == progressDialog)
            {
                progressDialog = MyProgressDialogFragment.newInstance(msg);
            }
            progressDialog.setMessage(msg);
            progressDialog.show(getSupportFragmentManager(), MyProgressDialogFragment.class.getSimpleName());
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * 公用组件：关闭进度条
     */
    protected void hideProgress()
    {
        if (!isFinishing() && null != progressDialog
                && progressDialog.getDialog() != null
                && progressDialog.getDialog().isShowing())
        {
            progressDialog.dismiss();

        }
    }

}
