package com.ws.component;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * 描述信息
 *
 * @author ws
 * 2020/9/10 18:45
 * 修改人：ws
 */
public class InputFilterFloatMinMax implements InputFilter {
    private float min, max;

    public InputFilterFloatMinMax(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public InputFilterFloatMinMax(String min, String max) {
        this.min = Float.parseFloat(min);
        this.max = Float.parseFloat(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            //限制小数点位数
            if (source.equals(".") && dest.toString().length() == 0) {
                return "0.";
            }
            if (dest.toString().contains(".")) {
                int index = dest.toString().indexOf(".");
                int mlength = dest.toString().substring(index).length();
                if (mlength == 3) {
                    return "";
                }
            }
            //限制大小
            float input = Float.parseFloat(dest.toString() + source.toString());
            if (isInRange(min, max, input)) {
                //ToastUtils.shortT("输入超出范围");
                return null;
            }
        } catch (Exception ignored) {
        }
        return "";
    }

    private boolean isInRange(float a, float b, float c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}