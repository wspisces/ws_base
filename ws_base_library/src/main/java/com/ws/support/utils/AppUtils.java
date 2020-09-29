package com.ws.support.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.orhanobut.logger.Logger;
//import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.TELEPHONY_SERVICE;

/**
 * 跟App相关的辅助类
 *
 * @author zhy
 *
 */
public class AppUtils {

	private AppUtils() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [获取应用程序版本名称信息]
	 * @param context
	 * @return 当前应用的版本号
	 */
	public static int getVersionCode(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * [获取应用程序版本名称信息]
	 * @param context
	 * @return 当前应用的版本名称
	 */
	public static String getVersionName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressLint("MissingPermission")
	public static String getImei(Context context){
		TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
		return TelephonyMgr.getDeviceId();
	}

	@SuppressLint({"HardwareIds", "MissingPermission"})
	public static String getWlanId(Context context){
		WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		return wm.getConnectionInfo().getMacAddress();
	}

	/**
	 * 判断系统是否在后台运行
	 * @param context 上下文
	 * @param appPackageName 包名
	 */
	public static boolean isAppRunning(Context context, String appPackageName) {
		//判断应用是否在运行
		ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
		boolean isAppRunning = false;
		for (ActivityManager.RunningTaskInfo info : list) {
			if (info.topActivity.getPackageName().equals(appPackageName) || info.baseActivity.getPackageName().equals(appPackageName)) {
				isAppRunning = true;
				break;
			}
		}
		return isAppRunning;
	}

	/**
	 * 拨打电话
	 * @param context 上下文-最好是Activity
	 * @param phoneNum 手机号码
	 */
/*	@SuppressLint("CheckResult")
	public static void callPhone(final Context context, final String phoneNum) {

		if (context instanceof AppCompatActivity) {

			RxPermissions rxPermissions = new RxPermissions((AppCompatActivity) context);
			rxPermissions.request(Manifest.permission.CALL_PHONE)
					.subscribe(new Consumer<Boolean>() {
						@Override
						public void accept(Boolean isOpen) throws Exception {
							if (isOpen) {
								callPhoneAction(phoneNum, context);
							} else {
								Toast.makeText(context, "请打开拨打电话权限", Toast.LENGTH_SHORT).show();
							}
						}
					});
		} else {

			callPhoneAction(phoneNum, context);
		}
	}*/

	@SuppressLint("MissingPermission")
	private static void callPhoneAction(String phoneNum, Context context) {

		Intent intent = new Intent(Intent.ACTION_CALL);
		Uri data = Uri.parse("tel:" + phoneNum);
		intent.setData(data);
		context.startActivity(intent);
	}

	/**
	 * 判断服务是否在后台运行
	 * @param context 上下文
	 * @param serviceName 服务名称
	 * @return true 正在运行
	 */
	public static boolean isServiceRunning(Context context, String serviceName) {
		ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceName.equals(service.service.getClassName())) {
				Logger.i("检测服务:" + serviceName);
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否安装目标应用
	 * @param packageName 目标应用安装后的包名
	 * @return 是否已安装目标应用
	 */
	public static boolean isInstallByread(String packageName) {
		return new File("/data/data/" + packageName).exists();
	}
	/**
	 * 获取Manifest Meta Data
	 * @param context
	 * @param metaKey
	 * @return
	 */
	public static String getMetaData(Context context, String metaKey) {
		String name = context.getPackageName();
		ApplicationInfo appInfo;
		String msg = "";
		try {
			appInfo = context.getPackageManager().getApplicationInfo(name,
					PackageManager.GET_META_DATA);
			msg = appInfo.metaData.getString(metaKey);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

//		if (cn.finalteam.toolsfinal.StringUtils.isEmpty(msg)) {
//			return "";
//		}

		return msg;
	}

	/**
	 * 获得渠道号
	 * @param context
	 * @param channelKey
	 * @return
	 */
	public static String getChannelNo(Context context, String channelKey) {
		return getMetaData(context, channelKey);
	}

	public static int getPackageUid(Context context, String packageName) {
		PackageManager packageManager = context.getPackageManager();
		int uid = -1;
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA);
			uid = packageInfo.applicationInfo.uid;
		} catch (PackageManager.NameNotFoundException e) {
		}
		return uid;
	}

	public static boolean isPackage(Context context, CharSequence s) {
		PackageManager packageManager = context.getPackageManager();
		try {
			packageManager.getPackageInfo(s.toString(), PackageManager.GET_META_DATA);
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
		return true;
	}

	public static void jumpToDeviceWifiPage(Context context) {
		context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
	}

	public static String getDataSize(long size) {
		DecimalFormat formater = new DecimalFormat("####.00");
		long b = 1024L;
		long kb = 1024*1024L;
		long mb = 1024*1024*1024L;
		long gb = 1024*1024*1024*1024L;
		if (size < b) {
			return size + "B";
		}
		else if (size < kb) {
			float kbsize = size / 1024f;
			return formater.format(kbsize) + "KB";
		} else if (size < mb) {
			float mbsize = size / 1024f / 1024f;
			return formater.format(mbsize) + "MB";
		} else if (size < gb) {
			float gbsize = size / 1024f / 1024f / 1024f;
			return formater.format(gbsize) + "GB";
		} else {
			return "";
		}
	}

	public static String getKb(long size) {
		DecimalFormat formater = new DecimalFormat("####.00");
		long b = 1024L;
		if (size < b) {
			return size + "B";
		}
		float kbsize = size / 1024f;
		return formater.format(kbsize) + "KB";
	}
}
