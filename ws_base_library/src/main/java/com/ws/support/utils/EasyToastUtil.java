package com.ws.support.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ws.base.R;
import com.ws.support.base.BaseApplication;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author curry
 */

public class EasyToastUtil {
    static Toast toast;
    static TextView tv;
    public static void showToast(Context context, String message, boolean longToast) {
        if (null == toast) {
            toast = new Toast(context);
            View view = LayoutInflater.from(context).inflate(R.layout.layout_toast,null);
            tv = view.findViewById(R.id.tv_toast);

            toast.setView(view);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        if (longToast){
            toast.setDuration(Toast.LENGTH_LONG);
        }else {
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        tv.setText(message);
        toast.show();
    }
    private static final boolean DEBUG = false;
    public static void debug(String message) {
        if (DEBUG)
            showToast(BaseApplication.getInstance(), message, false);
    }

    public static void showToast(String message) {
        if (StringUtils.isNotEmptyWithNull(message)) {
            showToast(BaseApplication.getInstance(), message, false);
        }
    }

    public static void showToast(String message, long time) {
        if (!StringUtils.isNotEmptyWithNull(message)) {
            return;
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, time);
        showToast(BaseApplication.getInstance(), message, false);
    }

    public static void showToast(Context context, String message) {
        if (!StringUtils.isNotEmptyWithNull(message)) {
            return;
        }
        showToast(context, message, false);
    }

    /**
     * @param context
     * @param resId   ---资源id
     */
    public static void showToastById(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }
}
