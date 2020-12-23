package com.ws.support.base.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.ws.base.R;
import com.ws.base.databinding.ActivityDataBaseBinding;
import com.ws.support.utils.ToastUtils;
import com.ws.support.widget.MyProgressDialogFragment;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
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
public abstract class BaseViewDataBindActivity<T extends ViewDataBinding> extends AppCompatActivity
{
    private final static long                     DELAY_TIME = 800;
    protected            ActivityDataBaseBinding  baseBinding;
    protected            Context                  mContext;
    protected            MyProgressDialogFragment progressDialog;
    protected            AppCompatActivity        mActivity;
    Toast    toast;
    TextView toatTv;

    protected T initContent()
    {
        int layoutId = getLayoutId();
        if (layoutId != 0)
        {
            return DataBindingUtil.inflate(getLayoutInflater(), layoutId, baseBinding.fl, true);
        }
        return null;
    }

    private void initData_()
    {
        mActivity = this;
        mContext = this;
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
            baseBinding.toolbar.setNavigationIcon(R.drawable.ic_back_white);
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
    protected abstract void initView(ActivityDataBaseBinding bindView, T binding);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        int menuLayout = getMenuLayout();
        if (menuLayout != 0)
        {
            getMenuInflater().inflate(menuLayout, menu);
            return true;
        } else
        {
            return super.onCreateOptionsMenu(menu);
        }
    }

    protected int getMenuLayout()
    {
        return 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            //hintKbTwo();
            onBackPressed();
        }
        return true/* super.onOptionsItemSelected(item) */;
    }

    //跳转
    protected void gotoActivity(Class<?> cls, Bundle bundle)
    {
        Intent intent = new Intent(this, cls);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void gotoActivity(Class<?> cls)
    {
        startActivity(new Intent(this, cls));
    }

    protected void jumptoActivity(Class<?> cls, Bundle bundle)
    {
        gotoActivity(cls, bundle);
        finish();
    }


  /*  private static final int             TWO_SECOND   = 2 * 1000;
    //双击后退
    private long preTime;
    @Override
    public void onBackPressed()
    {
        long currentTime = new Date().getTime();
        if (currentTime - preTime > TWO_SECOND || preTime == 0)
        {
            ToastUtils.info("再点击一次退出");
            preTime = currentTime;
        } else
        {
            System.exit(0);
        }
    }*/

    protected void jumptoActivity(Class<?> cls)
    {
        gotoActivity(cls);
        finish();
    }

    protected void hideKeyBoard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        // imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        if (imm.isActive() && getCurrentFocus() != null)
        {
            if (getCurrentFocus().getWindowToken() != null)
            {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

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
        baseBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_base);
        initData_();
        initToolbar();
        initToast();
        initView(baseBinding, initContent());
    }

    private void initToast()
    {
        //获取样式布局
        View toastRoot = LayoutInflater.from(this).inflate(R.layout.layout_toast, null);
//声明Toast
        toast = new Toast(this);
//给Toast设置布局
        toast.setView(toastRoot);
//设置布局文件里的控件属性
        toatTv = toastRoot.findViewById(R.id.tv_toast);
        toast.setDuration(Toast.LENGTH_SHORT);
    }

    /**
     * 公用组件： Toast
     */
    @SuppressLint({"CheckResult"})
    public void showToast(final String message)
    {
        if (TextUtils.isEmpty(message))
        {
            return;
        }
        toatTv.setText(message);
        toast.show();
    }

    /**
     * 公用组件： Toast
     */
    @SuppressLint({"CheckResult"})
    public void showToast(final int message)
    {
        if (message == 0)
        {
            return;
        }
        toatTv.setText(message);
        toast.show();
    }
}
