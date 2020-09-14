package com.heinqi.support.utils;

import android.text.TextUtils;

/**
 * Created by wg on 2018/2/6.
 */

public class JsonIsNullUtils {

    public static boolean isNotEmpty(Object mObject) {
        if (mObject == null || TextUtils.equals(mObject.toString(), "null")
                || TextUtils.equals(mObject.toString(), "[]")
                || TextUtils.equals(mObject.toString(), "{}")
                || TextUtils.equals(mObject.toString(), "")) {
            return false;
        } else {
            return true;
        }
    }

}
