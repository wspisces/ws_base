package com.heinqi.support.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.BuildConfig;
import androidx.multidex.MultiDexApplication;

import com.heinqi.support.http.HttpHelper;
import com.heinqi.support.utils.MyFileUtils;
import com.heinqi.support.xutil.DbUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;
import org.xutils.DbManager;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Retrofit;


/**
 * BaseApplication.class
 * app应用基础页面
 *
 * @author Johnny.xu
 * time:2018/11/26
 */
public class BaseApplication extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {

    public static DbManager mDbManager;

    //private List<Activity> mActivityList;
    public static  Retrofit        retrofit;
    public static  boolean         isForeground;//是否在前台
    private static BaseApplication mInstance;
    protected      int             activityCount;//activity的count数

    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //mActivityList = new ArrayList<>();
        x.Ext.init(this);
        //retrofit = HttpHelper.initRetrofit();

        MyFileUtils.initPath();
        //监听App状态
        this.registerActivityLifecycleCallbacks(this);
        Toasty.Config.getInstance()
                .tintIcon(true) // optional (apply textColor also to the icon)
                //.setToastTypeface(@NonNull Typeface typeface) // optional
                //.setTextSize(int sizeInSp) // optional
                //.allowQueue(boolean allowQueue) // optional (prevents several Toastys from queuing)
                .apply(); // required
    }


    //判断是否在前台
    public void isForeground() {
        isForeground = activityCount > 0;
        //Logger.e("-------isForeground=" + isForeground);
    }


    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        activityCount++;
        //Logger.d("-------onActivityStarted=" + activityCount);
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        //Logger.e("-------onActivityResumed=" + activityCount);
        isForeground();
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NotNull Activity activity) {
        activityCount--;
        //Logger.e("-------onActivityStopped=" + activityCount);
        isForeground();
    }


    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
}
