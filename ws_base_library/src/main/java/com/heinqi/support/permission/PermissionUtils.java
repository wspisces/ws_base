//package com.heinqi.support.permission;
//
//import android.app.Activity;
//import android.os.Build;
//import androidx.appcompat.app.AlertDialog;
//import com.heinqi.base.R;
//import com.yanzhenjie.permission.AndPermission;
//import com.yanzhenjie.permission.Setting;
//
//
//public class PermissionUtils {
//
//    public static final String TAG = "PermissionUtils";
//
//    public static void requestPermission(Activity activity, OnRequestPermissionListener listener, String... permissions) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            if (AndPermission.hasPermissions(activity, permissions)) {
//                if (listener != null) {
//                    listener.onGranted();
//                }
//                return;
//            }
//            AndPermission.with(activity)
//                    .runtime()
//                    .permission(permissions)
//                    .onGranted(data -> {
//                        listener.onGranted();
//                    })
//                    .onDenied(data -> {
//                        listener.onDenied(false);
//
//                        //申请失败
//                        if (AndPermission.hasAlwaysDeniedPermission(activity, permissions)) {
//
//                            new AlertDialog.Builder(activity, R.style.Theme_AppCompat_Light_Dialog_Alert)
//                                    .setTitle(R.string.error_please_retry)
//                                    .setMessage(R.string.update_title_dialog)
//                                    .setPositiveButton(R.string.sure, (dialog, witch) -> {
//                                        requestAgain(activity, listener, permissions);
//
//                                    })
//                                    .setNegativeButton(R.string.cancel, (dialog, witch) -> {
//                                        listener.onDenied(true);
//
//                                    })
//                                    .setCancelable(false)
//                                    .create()
//                                    .show();
//
//
//                        }
//
//
//                    })
//                    .start();
//        } else {
//            listener.onGranted();
//        }
//    }
//
//    public interface OnRequestPermissionListener {
//        void onGranted();
//
//        void onDenied(boolean isAlways);
//
//    }
//
//
//    public static void requestAgain(Activity activity, OnRequestPermissionListener listener, String... permissions) {
//        // 这些权限被用户总是拒绝。
//        AndPermission.with(activity)
//                .runtime()
//                .setting()
//                .onComeback(new Setting.Action() {
//                    @Override
//                    public void onAction() {
//                        requestPermission(activity, listener, permissions);
//                        // 用户从设置回来了。
//                    }
//                })
//                .start();
//    }
//}