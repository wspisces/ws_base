package com.ws.support.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.ws.support.base.BaseApplication;


import es.dmoral.toasty.Toasty;

/**
 * Toasty封装
 *
 * @author ws
 * 2020/8/5 21:26
 * 修改人：ws
 */
public class ToastUtils {
//    private static void show(String msg, ToatType type, int time, boolean witnIcon) {
//        if (TextUtils.isEmpty(msg)) {
//            Logger.e("提示信息不能为空或者null");
//            return;
//        }
//        Context context = BaseApplication.getInstance();
//        switch (type) {
//            case Info:
//                Toasty.info(context, msg, time, witnIcon);
//                break;
//            case Normal:
//                Toasty.normal(context, msg, time);
//                break;
//            case Warning:
//                Toasty.warning(context, msg, time, witnIcon);
//                break;
//            case Success:
//                Toasty.success(context, msg, time, witnIcon);
//                break;
//            case Error:
//                Toasty.error(context, msg, time, witnIcon);
//                break;
//        }
//    }
//
//    //显示短时间toast
//    public static void warn(String msg) {
//        show(msg, ToatType.Warning, Toasty.LENGTH_SHORT, true);
//    }
//
//    public static void warnL(String msg) {
//        show(msg, ToatType.Warning, Toasty.LENGTH_LONG, true);
//    }
//
//    //显示短时间toast
//    public static void error(String msg) {
//        show(msg, ToatType.Error, Toasty.LENGTH_SHORT, true);
//    }
//
//    public static void errorL(String msg) {
//        show(msg, ToatType.Error, Toasty.LENGTH_LONG, true);
//    }
//
//    public static void success(String msg) {
//        show(msg, ToatType.Success, Toasty.LENGTH_SHORT, true);
//    }
//
//    public static void successL(String msg) {
//        show(msg, ToatType.Success, Toasty.LENGTH_LONG, true);
//    }
//
//    public static void info(String msg) {
//        show(msg, ToatType.Info, Toasty.LENGTH_SHORT, true);
//    }
//
//    public static void infoL(String msg) {
//        show(msg, ToatType.Info, Toasty.LENGTH_LONG, true);
//    }
//
//    public static void normal(String msg) {
//        show(msg, ToatType.Normal, Toasty.LENGTH_SHORT, true);
//    }
//
//    public static void normalL(String msg) {
//        show(msg, ToatType.Normal, Toasty.LENGTH_LONG, true);
//    }
//
//    enum ToatType {
//        Normal, Info, Warning, Success, Error
//    }

    static         Toast   toast;
    @SuppressLint("HandlerLeak")
    private static Handler handler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if (toast != null) {
                toast.cancel();
            }
        }
    };

    //显示短时间toast
    public static void warn(String msg) {
        Toasty.warning(BaseApplication.getInstance(), msg, Toasty.LENGTH_SHORT, true).show();
    }

    public static void warnL(String msg) {
        Toasty.warning(BaseApplication.getInstance(), msg, Toasty.LENGTH_LONG, true).show();
    }

    //显示短时间toast
    public static void error(String msg) {
        Toasty.error(BaseApplication.getInstance(), msg, Toasty.LENGTH_SHORT, true).show();
    }

    public static void errorL(String msg) {
        Toasty.error(BaseApplication.getInstance(), msg, Toasty.LENGTH_LONG, true).show();
    }

    public static void success(String msg) {
        Toasty.success(BaseApplication.getInstance(), msg, Toasty.LENGTH_SHORT, true).show();
    }

    public static void successL(String msg) {
        Toasty.success(BaseApplication.getInstance(), msg, Toasty.LENGTH_LONG, true).show();
    }

    public static void info(String msg) {
        Toasty.info(BaseApplication.getInstance(), msg, Toasty.LENGTH_SHORT, true).show();
    }

    public static void infoL(String msg) {
        Toasty.info(BaseApplication.getInstance(), msg, Toasty.LENGTH_LONG, true).show();
    }

    public static void normal(String msg) {
        Toasty.normal(BaseApplication.getInstance(), msg, Toasty.LENGTH_SHORT).show();
    }

    public static void normalL(String msg) {
        Toasty.normal(BaseApplication.getInstance(), msg, Toasty.LENGTH_LONG).show();
    }

    @SuppressLint("ShowToast")
    public static void shortT(String msg) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, 200);
    }

}
