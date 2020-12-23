package com.ws.support.http;


import com.ws.support.utils.JsonUtils;
import com.ws.support.utils.StringUtils;
import com.ws.support.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultTO {
    // 结果状态
    public int code;
    // 结果
    public Object data;
    // 结果消息
    public String msg;

    public ResultTO() {
    }

    public int getStatus() {
        return code;
    }

    public boolean isSucc(){
        return  code == 200;
    }

    public String data(){
        String str = JsonUtils.toJson(data);
        if (str == null || "null".equalsIgnoreCase(str) || "{}".equalsIgnoreCase(str)){
            return  "";
        }
        return  str;
    }

    public JSONObject toJsonObject() throws JSONException {
        return new JSONObject(JsonUtils.toJson(data));
    }

    public JSONArray toJsonArray() throws JSONException {
        return new JSONArray(JsonUtils.toJson(data));
    }

    public String toString() {
        return JsonUtils.toJson(this);
    }

    public void showMsg(){
        if (StringUtils.isNotEmptyWithNull(msg))
        {
            ToastUtils.info(msg);
        }
    }

    public void showError()
    {
        if (StringUtils.isNotEmptyWithNull(msg))
        {
            ToastUtils.error(msg);
        }
    }
}
