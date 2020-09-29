package com.ws.support.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * 判断给定字符串是否空白串。
     * 空白串是指由空格、制表符、回车符、换行符组成的字符串
     * 若输入字符串为null或空字符串，返回true
     *
     * @param input 预判文案
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断给定字符串是否空白串。
     * 空白串是指由空格、制表符、回车符、换行符组成的字符串
     * 若输入字符串为null或空字符串，返回true
     *
     * @param input 预判文案
     * @return boolean
     */
    public static boolean isNotEmpty(String input) {
        return !isEmpty(input);
    }

    /**
     * 判断给定字符串是否空白串。
     * 空白串是指由空格、制表符、回车符、换行符组成的字符串
     * 若输入字符串为null或空字符串或'null'字符串，返回true
     *
     * @param input 预判文案
     * @return boolean
     */
    public static boolean isEmptyWithNull(String input) {
        if (input == null || "".equals(input) || "null".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断给定字符串是否空白串。
     * 空白串是指由空格、制表符、回车符、换行符组成的字符串
     * 若输入字符串为null或空字符串或'null'字符串，返回true
     *
     * @param input 预判文案
     * @return boolean
     */
    public static boolean isNotEmptyWithNull(String input) {
        return !isEmptyWithNull(input);
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 功能：判断字符是否为数字
     *
     * @param c
     * @return
     */
    public static boolean isNumeric(char c) {
        return (c >= '0' && c <= '9');
    }

    public static String appArray(List<String> strings, String sep) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : strings) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(sep);
            }
            stringBuilder.append(string);
        }

        return stringBuilder.toString();
    }

    /**
     * 去除数字里多余的0
     *
     * @param number
     * @return
     */
    public static String getPrettyNumber(String number) {
        return BigDecimal.valueOf(Double.parseDouble(number)).stripTrailingZeros().toPlainString();
    }

    /**
     * 根据Unicode编码完美的判断中文汉字和符号
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符是否为汉字，不包括符号。
     *
     * @param c 待校验的字符。
     * @return 是否为汉字。
     */
    public static boolean isChineseWord(char c) {
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher matcher = pattern.matcher(String.valueOf(c));
        return matcher.matches();
    }

    /**
     * 判断传递的文本是否仅包含a-Z和A-Z这些字母。
     *
     * @param text
     * @return
     */
    public static boolean isLetters(String text) {
        if (text == null || text.trim() == "") return false;
        for (int i = 0; i < text.length(); i++) {
            if (!((text.charAt(i) <= 'Z' && text.charAt(i) >= 'A')
                    || (text.charAt(i) <= 'z' && text.charAt(i) >= 'a'))) {
                //字符不在A-Z或a-z之间，那么整个text就不全是字母
                return false;
            }
        }
        return true;
    }

    /**
     * 判断传递的文本是否仅包含a-Z和A-Z这些字母。
     *
     * @param c
     * @return
     */
    public static boolean isLetters(char c) {
        if ((c <= 'Z' && c >= 'A')
                || (c <= 'z' && c >= 'a')) {
            return true;
        }
        return false;
    }

    /**
     * 根据拼音数组得到对应首字母的缩写，如“ni hao”得到“nh”。
     *
     * @param chinesePinyin 拼音数组。
     * @return 首字母缩写。
     */
    public static String getPinyinAcronym(String[] chinesePinyin) {
        if (chinesePinyin == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < chinesePinyin.length; i++) {
                if (chinesePinyin[i] == null || "".equals(chinesePinyin[i].trim())) {
                    sb.append("@");
                } else {
                    sb.append(chinesePinyin[i].charAt(0));
                }
            }
            return sb.toString();
        }
    }


    /**
     * 计算text的长度，一个汉字按2个字符记。
     *
     * @param text
     * @return
     */
    public static int getTextLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (StringUtils.isChinese(text.charAt(i))) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length;
    }

    /**
     * 将传递的字符串trim,如果为null返回空字符串。
     *
     * @param src 要转换的字符串。
     * @return trim后的字符串。
     */
    public static String trimString(String src) {
        if (src == null) return "";
        return src.trim();
    }

    /**
     * 距离格式化
     * <P>将距离以米为单位进行格式化，最高单位为千米,当距离为负数时返回空字符串</p>
     *
     * @param distance 距离
     */
    public static String formartDistance(double distance) {
        if (distance > 1000) {
            return ConvertUtil.reserveDecimalsFormat(distance / 1000d, 2) + "km";
        } else if (distance >= 0) {
            return ConvertUtil.reserveDecimalsFormat(distance, 2) + "m";
        } else {
            return "";
        }
    }

    /**
     * 去除字符串后的0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 格式化数据
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static String getFormatStr(String value, String defaultValue) {
        if (isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }
    public static String encodeUTF(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        return null;
    }

    public static String encode(String text, String enc) {
        try {
            return URLEncoder.encode(text, enc);
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证手机号码
     *
     * @param mobiles 手机号码字符串
     */
    public static boolean isMobileNO(String mobiles) {
        // 正则表达式
        String phone = "^1[3456789]\\d{9}$";
        Pattern pattern = Pattern.compile(phone);
        Matcher matcher = pattern.matcher(mobiles);
        return matcher.matches();
    }
}
