package com.ws.support.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ws.support.base.BaseApplication;

/**
 * 数据缓存类
 */
public class SharePreferUtil {

    public final static String ACCESSTOKEN = "accessToken";

    public final static  String MAX_TEMP          = "max_temp";
    public final static  String MIN_TEMP          = "min_temp";
    public final static  String TEMP_ID           = "temp_id";
    public final static  String AlertTime         = "alert_time";
    public final static  String FirstLogin        = "firstLogin";
    public final static  String LoginType         = "loginType";
    public final static  String ThirdPartyId      = "thirdPartyId";
    public final static  String OpenId            = "openId";
    public final static  String PersonId          = "personId";
    public final static  String DeviceId          = "DeviceId";
    public final static  String MacAddrss         = "MacAddrss";
    public final static  String AlertTimeBefore   = "AlertTimeBefore";
    private final static String AUTO_LOGIN        = "AutoLogin";
    private final static String REMEMBER_PASSWORD = "RememberPassword";
    private final static String USER_ACCOUNT      = "UserAccount";
    private final static String USER_PASSWORD     = "UserPassword";
    private final static String USER_NAME         = "UserName";
    private final static String USER_ID           = "userId";
    private static final String KEY_AUTO_LOGIN    = "auto_login";
    private static final String KEY_REMEMBER_PWD  = "remember_password";
    private static final String PHONE             = "phone";
    private final static String ISVERIFY = "isVerify";
    private static       String TAG               = "andu_tempratrue";

    public static void setTag(String code) {
        TAG = code;
    }

    public static Editor getEditor() {
        SharedPreferences sharedPreferences = BaseApplication.getInstance().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.edit();
    }

    public static SharedPreferences getSharedPreferences() {
        return BaseApplication.getInstance().getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    public static String getString(String str) {
        return getSharedPreferences().getString(str, "");
    }

    public static boolean isAutoLogin() {

        return getSharedPreferences().getBoolean(AUTO_LOGIN, false);
    }

    public static boolean saveAutoLogin(boolean isAuto) {

        return getEditor().putBoolean(AUTO_LOGIN, isAuto).commit();
    }

    public static boolean isRemeberPassward() {
        return getSharedPreferences().getBoolean(REMEMBER_PASSWORD, true);
    }

    public static boolean saveRemeberPassward(boolean isRemember) {

        return getEditor().putBoolean(REMEMBER_PASSWORD, isRemember).commit();
    }

    public static String getUserAccount() {
        return getSharedPreferences().getString(USER_ACCOUNT, "");
    }

    public static boolean saveUserAccount(String account) {
        return getEditor().putString(USER_ACCOUNT, account).commit();
    }

    public static String getUserPassword() {
        return getSharedPreferences().getString(USER_PASSWORD, "");
    }

    public static boolean saveUserPassword(String pwd) {
        return getEditor().putString(USER_PASSWORD, pwd).commit();
    }

    public static String getUserName() {
        return getSharedPreferences().getString(USER_NAME, "");
    }

    public static boolean saveUserName(String name) {
        return getEditor().putString(USER_NAME, name).commit();
    }

    public static String getUserId() {
        return getSharedPreferences().getString(USER_ID, "");
    }

    public static boolean saveUserId(String userId) {
        return getEditor().putString(USER_ID, userId).commit();
    }

    //    /*---记住密码---*/
//    public static void saveRemPwd( boolean flag) {
//        getDefaultEditor().putBoolean(KEY_REMEMBER_PWD, flag).commit();
//    }
//
//    public static boolean getRemPwd() {
//        return getDefaultSharedPreferences().getBoolean(KEY_REMEMBER_PWD, true);
//    }
//
    /*---Phone---*/
    public static void savePhone(String phone) {
        getEditor().putString(PHONE, phone).commit();
    }

    public static String getPhone() {
        return getSharedPreferences().getString(PHONE, "");
    }

    public static void saveAccessToken(String accessToken) {
        getEditor().putString(ACCESSTOKEN, accessToken).commit();
    }

    public static String getAccessToken() {
        return getSharedPreferences().getString(ACCESSTOKEN, "");
    }

    public static void saveMaxTemperature(float max) {
        getEditor().putFloat(MAX_TEMP, max).commit();
    }

    public static float getMaxTemperature() {
        return getSharedPreferences().getFloat(MAX_TEMP, 38.5f);
    }

    public static void saveMinTemperature(float max) {
        getEditor().putFloat(MIN_TEMP, max).commit();
    }

    public static float getMinTemperature() {
        return getSharedPreferences().getFloat(MIN_TEMP, 35.5f);
    }

    public static void saveTemperatureId(int id) {
        getEditor().putInt(TEMP_ID, id).commit();
    }

    public static int getTemperatureId() {
        return getSharedPreferences().getInt(TEMP_ID, -1);
    }

    public static void saveAlertTime(int time) {
        getEditor().putInt(AlertTime, time).commit();
    }

    public static int getAlertTime() {
        return getSharedPreferences().getInt(AlertTime, 30);
    }

    public static void saveFirstLogin() {
        getEditor().putBoolean(FirstLogin, false).commit();
    }

    public static boolean getFirstLogin() {
        return getSharedPreferences().getBoolean(FirstLogin, true);
    }

    public static void saveLoginType(String loginType) {
        getEditor().putString(LoginType, loginType).commit();
    }

    public static String getLoginType() {
        return getSharedPreferences().getString(LoginType, "");
    }

    public static void saveThirdPartyId(String thirdPartyId) {
        getEditor().putString(ThirdPartyId, thirdPartyId).commit();
    }

    public static String getThirdPartyId() {
        return getSharedPreferences().getString(ThirdPartyId, "");
    }

    public static void saveOpenId(String openId) {
        getEditor().putString(OpenId, openId).commit();
    }

    public static String getOpenId() {
        return getSharedPreferences().getString(OpenId, "");
    }

    public static void saveCurrentPersonId(int personId) {
        getEditor().putInt(PersonId, personId).commit();
    }

    public static int getCurrentPersonId() {
        return getSharedPreferences().getInt(PersonId, -1);
    }

    public static void saveCurrentDeviceId(String deviceId) {
        getEditor().putString(DeviceId, deviceId).commit();
    }

    public static String getCurrentDeviceId() {
        return getSharedPreferences().getString(DeviceId, "");
    }

    public static void saveCurrentDeviceMac(String macAddrss) {
        getEditor().putString(MacAddrss, macAddrss).commit();
    }

    public static String getCurrentDeviceMac() {
        return getSharedPreferences().getString(MacAddrss, "");
    }

    public static void saveAlertTimeBefore(long time) {
        getEditor().putLong(AlertTimeBefore, time).commit();
    }

    public static long getAlertTimeBefore() {
        return getSharedPreferences().getLong(AlertTimeBefore, 0);
    }

    public static void saveIsVerify(boolean isVerify) {
        getEditor().putBoolean(ISVERIFY, isVerify).commit();
    }

    public static boolean isVerify() {
        return getSharedPreferences().getBoolean(ISVERIFY, false);
    }


    //清理缓存数据
    public static void ClearCache() {
        savePhone("");
        saveAccessToken("");

        saveTemperatureId(-1);

        saveLoginType("");
        saveThirdPartyId("");
        saveOpenId("");

        saveCurrentPersonId(-1);
        saveCurrentDeviceId("");
        saveCurrentDeviceMac("");
        saveAlertTimeBefore(0);
    }


}
