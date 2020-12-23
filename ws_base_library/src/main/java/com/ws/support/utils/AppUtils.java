package com.ws.support.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

import com.orhanobut.logger.Logger;
//import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.TELEPHONY_SERVICE;
import static com.ws.support.utils.DataCleanManager.getFormatSize;

/**
 * 跟App相关的辅助类
 *
 * @author zhy
 */
public class AppUtils
{

    private AppUtils()
    {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("MissingPermission")
    public static String getImei(Context context)
    {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        return TelephonyMgr.getDeviceId();
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getWlanId(Context context)
    {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wm.getConnectionInfo().getMacAddress();
    }

    /**
     * 判断系统是否在后台运行
     *
     * @param context        上下文
     * @param appPackageName 包名
     */
    public static boolean isAppRunning(Context context, String appPackageName)
    {
        //判断应用是否在运行
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        for (ActivityManager.RunningTaskInfo info : list)
        {
            if (info.topActivity.getPackageName().equals(appPackageName) || info.baseActivity.getPackageName().equals(appPackageName))
            {
                isAppRunning = true;
                break;
            }
        }
        return isAppRunning;
    }

    /**
     * 拨打电话
     *
     * @param context  上下文-最好是Activity
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
    private static void callPhoneAction(String phoneNum, Context context)
    {

        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 判断服务是否在后台运行
     *
     * @param context     上下文
     * @param serviceName 服务名称
     * @return true 正在运行
     */
    public static boolean isServiceRunning(Context context, String serviceName)
    {
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (serviceName.equals(service.service.getClassName()))
            {
                Logger.i("检测服务:" + serviceName);
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否安装目标应用
     *
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    public static boolean isInstallByread(String packageName)
    {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 获取Manifest Meta Data
     *
     * @param context
     * @param metaKey
     * @return
     */
    public static String getMetaData(Context context, String metaKey)
    {
        String name = context.getPackageName();
        ApplicationInfo appInfo;
        String msg = "";
        try
        {
            appInfo = context.getPackageManager().getApplicationInfo(name,
                    PackageManager.GET_META_DATA);
            msg = appInfo.metaData.getString(metaKey);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

//		if (cn.finalteam.toolsfinal.StringUtils.isEmpty(msg)) {
//			return "";
//		}

        return msg;
    }

    /**
     * 获得渠道号
     *
     * @param context
     * @param channelKey
     * @return
     */
    public static String getChannelNo(Context context, String channelKey)
    {
        return getMetaData(context, channelKey);
    }

    public static int getPackageUid(Context context, String packageName)
    {
        PackageManager packageManager = context.getPackageManager();
        int uid = -1;
        try
        {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA);
            uid = packageInfo.applicationInfo.uid;
        } catch (PackageManager.NameNotFoundException e)
        {
        }
        return uid;
    }

    public static boolean isPackage(Context context, CharSequence s)
    {
        PackageManager packageManager = context.getPackageManager();
        try
        {
            packageManager.getPackageInfo(s.toString(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
        return true;
    }

    public static void jumpToDeviceWifiPage(Context context)
    {
        context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    }

    public static String getDataSize(long size)
    {
        DecimalFormat formater = new DecimalFormat("####.00");
        long b = 1024L;
        long kb = 1024 * 1024L;
        long mb = 1024 * 1024 * 1024L;
        long gb = 1024 * 1024 * 1024 * 1024L;
        if (size < b)
        {
            return size + "B";
        } else if (size < kb)
        {
            float kbsize = size / 1024f;
            return formater.format(kbsize) + "KB";
        } else if (size < mb)
        {
            float mbsize = size / 1024f / 1024f;
            return formater.format(mbsize) + "MB";
        } else if (size < gb)
        {
            float gbsize = size / 1024f / 1024f / 1024f;
            return formater.format(gbsize) + "GB";
        } else
        {
            return "";
        }
    }

    public static String getKb(long size)
    {
        DecimalFormat formater = new DecimalFormat("####.00");
        long b = 1024L;
        if (size < b)
        {
            return size + "B";
        }
        float kbsize = size / 1024f;
        return formater.format(kbsize) + "KB";
    }


    /**
     * 获取android总运行内存大小
     *
     * @param context 上下文
     */
    public static float getTotalMemory(Context context)
    {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        //long initial_memory = 0;
        try
        {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            // 获得系统总内存，单位是KB
            int i = Integer.parseInt(arrayOfString[1]);
            localBufferedReader.close();
            return (float) i / 1024 / 1024;//GB
        } catch (IOException ignored)
        {
        }
        return 0;// Byte转换为KB或者MB，内存大小规格化
        //return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }

    /**
     * 当前的手机总存储内存大小
     *
     * @return xx GB
     */
    public static float getTotalInternalMemorySize(Context context)
    {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();

        return (float) totalBlocks * blockSize / 1024 / 1024 / 1024;
        //return Formatter.formatFileSize(context, totalBlocks * blockSize);
    }

    /**
     * 当前手机可用存储内存大小
     *
     * @return xx GB
     */
    public static String getAvailableInternalMemorySize(Context context)
    {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(context, availableBlocks * blockSize);
    }

    /**
     * 获取android当前可用运行内存大小
     *
     * @param context 上下文
     */
    public static String getAvailMemory(Context context)
    {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        // mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
    }

    /**
     * 获得缓存大小
     *
     * @param context 上下文
     * @return 缓存大小
     */
    public static String getTotalCacheSize(Context context)
    {
        long cacheSize = 0;
        try
        {
            cacheSize = getFolderSize(context.getCacheDir());
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            {
                cacheSize += getFolderSize(context.getExternalCacheDir());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return getFormatSize(cacheSize);
    }

    private static long getFolderSize(File file) throws Exception
    {
        long size = 0;
        try
        {
            File[] fileList = file.listFiles();
            for (File value : fileList)
            {
                // 如果下面还有文件
                if (value.isDirectory())
                {
                    size = size + getFolderSize(value);
                } else
                {
                    size = size + value.length();
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 清除缓存
     *
     * @param context 上下文
     */
    public static void clearAllCache(Context context)
    {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir)
    {
        if (dir != null && dir.isDirectory())
        {
            String[] children = dir.list();
            for (String child : children)
            {
                boolean success = deleteDir(new File(dir, child));
                if (!success)
                {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 获取SSID
     * https://blog.csdn.net/a289676449/article/details/105201051/
     * @param activity 上下文
     * @return WIFI 的SSID
     */
    public static String getWIFISSID(Activity activity)
    {
        String ssid = "unknown id";

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O || Build.VERSION.SDK_INT == Build.VERSION_CODES.P)
        {

            WifiManager mWifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            assert mWifiManager != null;
            WifiInfo info = mWifiManager.getConnectionInfo();

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            {
                return info.getSSID();
            } else
            {
                return info.getSSID().replace("\"", "");
            }
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1)
        {

            ConnectivityManager connManager = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connManager != null;
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo.isConnected())
            {
                if (networkInfo.getExtraInfo() != null)
                {
                    return networkInfo.getExtraInfo().replace("\"", "");
                }
            }
        }
        return ssid;
    }

    //https://blog.csdn.net/liuxiaopang520/article/details/107961486
    public static String getConnectedBtDevice() {
        //获取蓝牙适配器
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //得到已匹配的蓝牙设备列表
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if (bondedDevices != null && bondedDevices.size() > 0) {
            for (BluetoothDevice bondedDevice : bondedDevices) {
                try {
                    //使用反射调用被隐藏的方法
                    Method isConnectedMethod = BluetoothDevice.class.getDeclaredMethod("isConnected", (Class[]) null);
                    isConnectedMethod.setAccessible(true);
                    boolean isConnected = (boolean) isConnectedMethod.invoke(bondedDevice, (Object[]) null);
                    Logger.e("isConnected：" + isConnected);
                    if (isConnected) {
                        return bondedDevice.getName();
                    }
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }


}
