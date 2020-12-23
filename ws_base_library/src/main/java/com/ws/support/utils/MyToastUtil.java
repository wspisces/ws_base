package com.ws.support.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ws.base.R;
import com.ws.support.base.BaseApplication;

/**
 * 描述信息
 *
 * @author ws
 * @date 12/21/20 4:43 PM
 * 修改人：ws
 */
public class MyToastUtil
{
    public static void show(String messge)
    {
        if (TextUtils.isEmpty(messge))return;
        //获取样式布局
        View toastRoot = LayoutInflater.from(BaseApplication.getInstance()).inflate(R.layout.layout_toast, null);
        //声明Toast
        Toast toast = new Toast(BaseApplication.getInstance());
        //给Toast设置布局
        toast.setView(toastRoot);
        //设置布局文件里的控件属性
        TextView tv = toastRoot.findViewById(R.id.tv_toast);
        toast.setDuration(Toast.LENGTH_SHORT);
        tv.setText(messge);
        toast.show();
    }
    public static void show(int messge)
    {
        if (messge == 0)return;
        //获取样式布局
        View toastRoot = LayoutInflater.from(BaseApplication.getInstance()).inflate(R.layout.layout_toast, null);
        //声明Toast
        Toast toast = new Toast(BaseApplication.getInstance());
        //给Toast设置布局
        toast.setView(toastRoot);
        //设置布局文件里的控件属性
        TextView tv = toastRoot.findViewById(R.id.tv_toast);
        toast.setDuration(Toast.LENGTH_SHORT);
        tv.setText(messge);
        toast.show();
    }
}
