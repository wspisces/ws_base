package com.heinqi.support.utils;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import androidx.appcompat.app.AppCompatActivity;


/**
 * 跟网络相关的工具类
 * 
 * @author zhy
 * 
 */
public class NetUtils
{
	private NetUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 判断网络是否连接
	 *
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context)
	{

		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (null != connectivity)
		{

			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (null != info && info.isConnected())
			{
				if (info.getState() == NetworkInfo.State.CONNECTED)
				{
					return true;
				}
			}
		}
		return false;
	}

	public static int getNetLevel(Context context) {

		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

//		if (null != connectivity)
//		{
//
//			NetworkInfo info = connectivity.getActiveNetworkInfo();
//			if (null != info && info.isConnected())
//			{
//				if (info.getState() == NetworkInfo.State.CONNECTED)
//				{
//					return info.get;
//				}
//			}
//		}
		return 0;
	}

	/**
	 * 判断是否包含SIM卡
	 *
	 * @return 状态
	 */
	public boolean hasSimCard(Context context) {
		int simState = ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getSimState();
		return hasSimCard(simState);
	}

	/**
	 * 判断是否包含SIM卡
	 *
	 * @return 状态
	 */
	public boolean hasSimCard(TelephonyManager telephonyManager) {
		int simState = telephonyManager.getSimState();
		return hasSimCard(simState);
	}

	/**
	 * 判断是否包含SIM卡
	 *
	 * @return 状态
	 */
	public boolean hasSimCard(int simState) {
		boolean result = true;
		switch (simState) {
			case TelephonyManager.SIM_STATE_ABSENT:
				result = false; // 没有SIM卡
				break;
			case TelephonyManager.SIM_STATE_UNKNOWN:
				result = false;
				break;
		}
		return result;
	}

	/**
	 * 判断是否是wifi连接
	 */
	@SuppressLint("MissingPermission")
	public static boolean isWifi(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm == null)
			return false;
		return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

	}

	/**
	 * 打开网络设置界面
	 */
	public static void openSetting(AppCompatActivity activity)
	{
		Intent intent = new Intent("/");
		ComponentName cm = new ComponentName("com.android.settings",
				"com.android.settings.WirelessSettings");
		intent.setComponent(cm);
		intent.setAction("android.intent.action.VIEW");
		activity.startActivityForResult(intent, 0);
	}

}
