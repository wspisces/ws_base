package com.ws.support.utils;

/**
 * 校验工具
 *
 * @author Johnny.xu
 *         date 2017/6/28
 */
public class VerifyUtil {

    public static boolean verifyIdCard(String card) {
        return verForm(card);
    }

    //<------------------身份证格式的正则校验----------------->
    public static boolean verForm(String num) {
        String reg = "^\\d{15}$|^\\d{17}[0-9Xx]$";
        if (!num.matches(reg)) {
            return false;
        }
        return true;
    }

    //<------------------身份证最后一位的校验算法----------------->
    public static boolean verify(char[] id) {
        int sum = 0;
        int w[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
        char[] ch = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
        for (int i = 0; i < id.length - 1; i++) {
            sum += (id[i] - '0') * w[i];
        }
        int c = sum % 11;
        char code = ch[c];
        char last = id[id.length-1];
        last = last == 'x' ? 'X' : last;
        return last == code;
    }
}
