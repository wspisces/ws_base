package com.ws.support.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eli
 * @cratetime 2018/12/10 下午2:58
 * @desc 权限util
 */
public class PermissionUtils {
    private static List<String> permissionList = new ArrayList<>();

    public static void addPermission(String permission){
        if(!permissionList.contains(permission)){
            permissionList.add(permission);
        }
    }
    public static String[] getPermission(){
        String[] mPermissionArray = new String[permissionList.size()];
        for(int i = 0;i<permissionList.size();i++){
            mPermissionArray[i] = permissionList.get(i);
        }
        return mPermissionArray;
    }
}
