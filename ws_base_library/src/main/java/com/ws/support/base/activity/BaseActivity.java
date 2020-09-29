package com.ws.support.base.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.ws.base.R;
import com.ws.support.base.BaseApplication;
import com.ws.support.network.NetType;
import com.ws.support.network.Network;
import com.ws.support.network.NetworkManager;
import com.ws.support.widget.MyProgressDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.xutils.DbManager;

import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;


public abstract class BaseActivity extends BaseFragmentActivity {

    private final static long                     DELAY_TIME     = 800;
    protected Toolbar  mToolbar;
    protected TextView mTitle;

    protected FrameLayout              mContentLayout;
    protected Context                  mContext;
    protected InputMethodManager       imm;
    protected DbManager                dbManager;

    private   BaseActivity             mThis;

    private   MyProgressDialogFragment progressDialog;

    @SuppressLint("CheckResult")
    protected void deleyActionOnMain(Consumer<Long> back)
    {
        deleyAction(DELAY_TIME, AndroidSchedulers.mainThread(), back);
    }

    @SuppressLint("CheckResult")
    protected void deleyActionOnMain(long time, Consumer<Long> back) {
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
    protected void deleyAction(long time, Scheduler scheduler, Consumer<Long> back) {
        Observable.timer(time, TimeUnit.MILLISECONDS)
                .subscribeOn(scheduler)
                .observeOn(scheduler)
                .subscribe(back);
    }

    /**
     * 是否启用网络监听
     */
    protected boolean enableNetWork() {
        return false;
    }

    /**
     * 网络重新连接调用
     */
    protected void WhenNetConnected() {

    }

    /**
     * 注册网络变化广播
     */
    private void registerNetWork() {
        if (enableNetWork()) {
            NetworkManager.getInstance().init(this.getApplication());
            NetworkManager.getInstance().registerObserver(this);
        }
    }

    /**
     * 取消注册网络变化广播
     */
    private void unregisterNetwork() {
        if (enableNetWork())
            NetworkManager.getInstance().unRegisterObserver(this);
    }

    /**
     * 进行别的初始化
     */
    protected abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isFullScreen()) {
            //设置无标题
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            //设置全屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        mThis = this;
        setContentView(R.layout.activity_base);
        initEventBus();
        initData_();
        initToolbar();
        initContent();
        initData();
        registerNetWork();

    }

    protected boolean isFullScreen() {
        return false;
    }

    /**
     * 隐藏navigationBar
     */
    private void hideNavigation() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                /*| View.SYSTEM_UI_FLAG_FULLSCREEN*/;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void initData_() {
        mContext = this;
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        dbManager = BaseApplication.mDbManager;
        //CnBaseApplication.getInstance().addActivity(this);
    }

    protected void setRegisterTopStyle() {
        mToolbar.setBackgroundColor(Color.WHITE);
        mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    /**
     * 是否全屏（隐藏Toolbar）
     */
    private boolean fullScreen() {
        int flag = this.getWindow().getAttributes().flags;
        return (flag & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    /**
     * 隐藏Toolbar
     */
    protected boolean hideToolbar() {
        return false;
    }

    /**
     * 是否显示返回按钮
     */
    protected boolean enableNavigation() {
        return true;
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        if (mToolbar != null) {
            if (fullScreen() || hideToolbar()) {
                mToolbar.setVisibility(View.GONE);
                return;
            }
            mToolbar.setTitle("");
            if (enableNavigation()) {
                mToolbar.setNavigationIcon(R.drawable.ic_back_white);
            }
            setSupportActionBar(mToolbar);
        }
        mTitle = this.findViewById(R.id.title);
        setTitle(getToolbarTite());
    }


    /**
     * 设置标题
     */
    protected void setTitle(String title) {
        if (mTitle != null && title != null) {
            mTitle.setText(title);
        }
    }

    /**
     * 初始化内容布局
     */
    protected void initContent() {
        mContentLayout = this.findViewById(R.id.content);
        int contentId = getContentLayout();
        if (contentId != 0) {
            LayoutInflater.from(mContext).inflate(contentId, mContentLayout);
        }
    }


    /**
     * 公用组件：进度条
     */
    protected void initProgress(String msg) {
        try {
            if (null == progressDialog) {
                progressDialog = MyProgressDialogFragment.newInstance(msg);
            }
            progressDialog.setMessage(msg);
            progressDialog.show(getSupportFragmentManager(), MyProgressDialogFragment.class.getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 公用组件：关闭进度条
     */
    protected void hideProgress() {
        if (!isFinishing() && null != progressDialog
                && progressDialog.getDialog() != null
                && progressDialog.getDialog().isShowing()) {
            progressDialog.dismiss();

        }
    }

    /**
     * 公用组件： Toast
     */
    @SuppressLint({"CheckResult"})
    public void showToast(final String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Toasty.info(this, message);
    }

    /**
     * 公用组件： Toast
     */
    @SuppressLint({"CheckResult"})
    public void showToast(final int message) {
        if (message == 0) {
            return;
        }
        Toasty.info(this, message);
    }

    /**
     * 隐藏输入法
     */
    public void hideKeyboard(View view) {
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 隐藏输入法
     */
    public void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 打开输入法
     */
    public void showKeyboard(View view) {
        imm.showSoftInputFromInputMethod(view.getWindowToken(), InputMethodManager.SHOW_FORCED);
    }

    /**
     * 初始化EventBus
     */
    private void initEventBus() {
        if (enableEventBus()) {
            registerEventBus();
        }
    }

    private void quitEventBus() {
        if (enableEventBus()) {
            unregisterEventBus();
        }
    }

    /**
     * 是否启用EventBus
     */
    protected boolean enableEventBus() {
        return false;
    }

    private void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    private void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    /**
     * 初始化内容视图
     */
    protected abstract int getContentLayout();

    /**
     * 设置Toolbar标题
     */
    protected abstract String getToolbarTite();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuLayout = getMenuLayout();
        if (menuLayout != 0) {
            getMenuInflater().inflate(menuLayout, menu);
            return true;
        } else {
            return super.onCreateOptionsMenu(menu);
        }
    }

    protected int getMenuLayout() {
        return 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            hintKbTwo();
            onBackPressed();
        }
        return true/* super.onOptionsItemSelected(item) */;
    }

    //跳转
    protected void gotoActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void gotoActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    protected void jumptoActivity(Class<?> cls, Bundle bundle) {
        gotoActivity(cls, bundle);
        finish();
    }

    protected void jumptoActivity(Class<?> cls) {
        gotoActivity(cls);
        finish();
    }

    public boolean judgeExist() {
        if (mThis == null || mThis.isFinishing()) {
            return false;
        } else {
            return !mThis.isDestroyed();
        }
    }

    @Override
    protected void onDestroy() {
        hideProgress();
        quitEventBus();

        unregisterNetwork();
        super.onDestroy();
    }

    @Network(netType = NetType.AUTO)
    public void onNetChanged(NetType netType) {
        switch (netType) {
            case WIFI:
                showToast("wifi网络连接成功");
                //Logger.e("AUTO监控：WIFI CONNECT");
                WhenNetConnected();
                break;
            case MOBILE:
                showToast("手机网络连接成功");
                //Logger.e("AUTO监控：MOBILE CONNECT");
                WhenNetConnected();
                break;
            case AUTO:
                //Logger.e("AUTO监控：AUTO CONNECT");
                WhenNetConnected();
                break;
            case NONE:
                //Logger.e("AUTO监控：NONE CONNECT");
                showToast("网络连接断开");
                break;
            default:
                break;
        }
    }

    @Network(netType = NetType.WIFI)
    public void onWifiChanged(NetType netType) {
        switch (netType) {
            case WIFI:
                //Logger.e("wifi监控：WIFI CONNECT");
                break;
            case NONE:
                //Logger.e("wifi监控：NONE CONNECT");
                break;
        }
    }

    @Network(netType = NetType.MOBILE)
    public void onMobileChanged(NetType netType) {
        switch (netType) {
            case MOBILE:
                //Logger.e("Mobile监控：MOBILE CONNECT");
                break;
            case NONE:
                //Logger.e("Mobile监控：NONE CONNECT");
                break;
        }
    }

  /*//双击后退
    private long    preTime;
    private static final int        TWO_SECOND = 2 * 1000;
    @Override
    public void onBackPressed()
    {
            long currentTime = new Date().getTime();
            if (currentTime - preTime > TWO_SECOND || preTime == 0)
            {
                showToast("再点击一次退出");
                preTime = currentTime;
            } else
            {
                System.exit(0);
            }
    }*/
}
