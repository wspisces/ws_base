package com.ws.support.base;

import androidx.multidex.MultiDexApplication;


import com.ws.support.utils.MyFileUtils;

import org.xutils.DbManager;
import org.xutils.x;

import es.dmoral.toasty.Toasty;
import retrofit2.Retrofit;


/**
 * BaseApplication.class
 * app应用基础页面
 *
 * @author Johnny.xu
 * time:2018/11/26
 */
public class BaseApplication extends MultiDexApplication
{

    public static  DbManager       mDbManager;
    public static  Retrofit        retrofit;
    private static BaseApplication mInstance;

    public static BaseApplication getInstance()
    {
        return mInstance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
        x.Ext.init(this);
        MyFileUtils.initPath();
        //监听App状态
        this.registerActivityLifecycleCallbacks(new AppLifecycleCallback());
        Toasty.Config.getInstance()
                .tintIcon(true) // optional (apply textColor also to the icon)
                //.setToastTypeface(@NonNull Typeface typeface) // optional
                //.setTextSize(int sizeInSp) // optional
                //.allowQueue(boolean allowQueue) // optional (prevents several Toastys from queuing)
                .apply(); // required
    }
}
