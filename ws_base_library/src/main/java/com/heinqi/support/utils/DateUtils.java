package com.heinqi.support.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 时间工具类
 *
 * @author Johnny.xu
 * date 2017/2/15
 */
public class DateUtils {

    /**
     * 时间日期格式化到年月日.
     */
    public static String dateFormatYMD = "yyyy-MM-dd";

    /**
     * 时间日期格式化到年月.
     */
    public static String dateFormatYM = "yyyy-MM";

    /**
     * 时分秒.
     */
    public static String dateFormatHMS = "HH:mm:ss";

    /**
     * 时分.
     */
    public static String dateFormatHM = "HH:mm";

    /**
     * 小时
     */
    public static String dateFormatH = "HH";

    /**
     * 分
     */
    public static String dateFormatM = "mm";

    /**
     * 日
     */
    public static String dateFormatD = "dd";


    /**
     * 时间日期格式化到年月日时分秒.
     */
    public static String dateFormatYMDHMS = "yyyy-MM-dd" + " " + dateFormatHMS;


    /**
     * 时间日期格式化到年月日时分.
     */
    public static String dateFormatYMDHM = "yyyy-MM-dd" + " " + dateFormatHM;

    /**
     * 时间日期格式化到年月日时.
     */
    public static String dateFormatYMDH = "yyyy-MM-dd" + " " + dateFormatH;

    /**
     * 时间日期格式化到月日时分.
     */
    public static String dateFormatMDHM_CN = "MM月dd日" + " " + dateFormatHM;

    /**
     * 中文的年月日
     */
    public static String dateFormatYMD_CN = "yyyy年MM月dd日";

    /**
     * 中文的年月
     */
    public static String dateFormatYM_CN = "yyyy年MM月";

    /**
     * 中文的年月日 时分
     */
    public static String dateFormatYMDHM_CN = dateFormatYMD_CN + " " + dateFormatHM;

    /**
     * .的年月日 时分
     */
    public static String dateFormatYMD_P = "yyyy.MM.dd";

    /**
     * 时间日期格式化到年月日.
     */
    public static String dateFormatYMD_S = "yyyy / MM / dd";

    public static String dateFormatYMD_S2 = "yyyy/MM/dd";

    /**
     * 时间日期格式化到年月日时分
     */
    public static String dateFormatYMD_HM_S = "yyyy/MM/dd " + dateFormatHM;

    public static String dateFormatYMD_HM_P = "yyyy.MM.dd " + dateFormatHM;

    /**
     * 月-日
     */
    public static String dateFormatMD = "MM-dd";

    /**
     * 年
     */
    public static String dateFormatY = "yyyy";

    /**
     * 描述：String类型的日期时间转化为Date类型.
     *
     * @param strDate String形式的日期时间
     * @param format  格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return Date Date类型日期时间
     */
    public static Date getDateByFormat(String strDate, String format) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        Date date = null;
        try {
            date = mSimpleDateFormat.parse(strDate);
        } catch (ParseException e) {
           e.printStackTrace();
        }
        return date;
    }

    /**
     * 得到当天凌晨时间
     */
    public static long getMorningCurrentTime() {
        return getDateByFormat(getCurrentDateByFormat(dateFormatYMD), dateFormatYMD).getTime();
    }

    /**
     * 得到当前时间时分秒
     */
    public static long getCurrentHMSTime() {
        String[] timeStr = getCurrentDateByFormat(dateFormatHMS).split(":");
        if (timeStr.length == 3) {
            return Integer.valueOf(timeStr[0]) * 60 * 60 + Integer.valueOf(timeStr[1]) * 60 + Integer.valueOf(timeStr[2]);
        }
        return 0;
    }

    /**
     * 匹配时间是否是当天
     */
    public static boolean matchDateIsToday(long time, String format) {
        String todayString = DateUtils.getCurrentDateByFormat(format);
        String selectString = DateUtils.formatTimeByMillisecond(time, format);

        if (todayString == null || selectString == null) {
            return false;
        } else {
            return todayString.equals(selectString);
        }
    }

    /**
     * 匹配时间是同一小时
     */
    public static boolean matchDataIsSameHour(long time, long secondTime) {
        return DateUtils.formatTimeByMillisecond(time, dateFormatYMDH)
                .equals(DateUtils.formatTimeByMillisecond(secondTime, dateFormatYMDH));
    }

    /**
     * 匹配时间是同一天
     */
    public static boolean matchDataIsSameDay(long time, long secondTime) {
        return DateUtils.formatTimeByMillisecond(time, dateFormatYMD)
                .equals(DateUtils.formatTimeByMillisecond(secondTime, dateFormatYMD));
    }

    /**
     * 描述：Date类型转化为String类型.
     *
     * @param date   the date
     * @param format the format
     * @return String String类型日期时间
     */
    public static String formatTimeByDate(Date date, String format) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        String strDate = null;
        try {
            strDate = mSimpleDateFormat.format(date);
        } catch (Exception e) {
        }
        return strDate;
    }

    /**
     * 描述：long类型转化为String类型.
     *
     * @param millisecond  the date
     * @param formatString the format
     * @return String String类型日期时间
     */
    public static String formatTimeByMillisecond(long millisecond, String formatString) {

        Date date = new Date(millisecond);
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        return format.format(date);
    }

    /**
     * 将时间数据转格式化为年月日（yyyy-MM-dd HH:mm:ss）
     *
     * @param object 只能转换Date和Long类型的数据
     * @return 转换为指定格式的数据 yyyy-MM-dd HH:mm:ss
     */
    public static String formatTimeToYMDHMS(Object object) {
        if (object == null) {
            throw new NullPointerException("转换数据不能为空");
        } else if (object instanceof Date || object instanceof Long) {
            if (object instanceof Date) {
                return formatTimeByDate((Date) object, dateFormatYMDHMS);
            } else {
                return formatTimeByMillisecond((Long) object, dateFormatYMDHMS);
            }
        } else {
            throw new UnsupportedOperationException("只能转换Date和Long类型的数据");
        }
    }

    /**
     * 将时间数据转格式化为年月日（yyyy-MM-dd HH:mm）
     *
     * @param object 只能转换Date和Long类型的数据
     * @return 转换为指定格式的数据 yyyy-MM-dd HH:mm
     */
    public static String formatTimeToYMDHM(Object object) {
        if (object == null) {
            throw new NullPointerException("转换数据不能为空");
        } else if (object instanceof Date || object instanceof Long) {
            if (object instanceof Date) {
                return formatTimeByDate((Date) object, dateFormatYMDHM);
            } else {
                return formatTimeByMillisecond((Long) object, dateFormatYMDHM);
            }
        } else {
            throw new UnsupportedOperationException("只能转换Date和Long类型的数据");
        }
    }
    /**
     * 将时间数据转格式化为年月日（yyyy-MM-dd）
     *
     * @param object 只能转换Date和Long类型的数据
     * @return 转换为指定格式的数据 yyyy-MM-dd
     */
    public static String formatTimeToYM(Object object) {
        if (object == null) {
            throw new NullPointerException("转换数据不能为空");
        } else if (object instanceof Date || object instanceof Long) {
            if (object instanceof Date) {
                return formatTimeByDate((Date) object, dateFormatYM);
            } else {
                return formatTimeByMillisecond((Long) object, dateFormatYM);
            }
        } else {
            throw new UnsupportedOperationException("只能转换Date和Long类型的数据");
        }
    }

    /**
     * 将时间数据转格式化为年月日（yyyy-MM-dd）
     *
     * @param object 只能转换Date和Long类型的数据
     * @return 转换为指定格式的数据 yyyy-MM-dd
     */
    public static String formatTimeToYMD(Object object) {
        if (object == null) {
            throw new NullPointerException("转换数据不能为空");
        } else if (object instanceof Date || object instanceof Long) {
            if (object instanceof Date) {
                return formatTimeByDate((Date) object, dateFormatYMD);
            } else {
                return formatTimeByMillisecond((Long) object, dateFormatYMD);
            }
        } else {
            throw new UnsupportedOperationException("只能转换Date和Long类型的数据");
        }
    }

    /**
     * 将时间数据转格式化为年月日（yyyy-MM-dd）
     *
     * @param object 只能转换Date和Long类型的数据
     * @return 转换为指定格式的数据 yyyy-MM-dd
     */
    public static String formatTimeToHMS(Object object) {
        if (object == null) {
            throw new NullPointerException("转换数据不能为空");
        } else if (object instanceof Date || object instanceof Long) {
            if (object instanceof Date) {
                return formatTimeByDate((Date) object, dateFormatHMS);
            } else {
                return formatTimeByMillisecond((Long) object, dateFormatHMS);
            }
        } else {
            throw new UnsupportedOperationException("只能转换Date和Long类型的数据");
        }
    }

    /**
     * 描述：获取指定日期时间的字符串.
     *
     * @param strDate String形式的日期时间，必须为yyyy-MM-dd HH:mm:ss格式
     * @param format  格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String 转换后的String类型的日期时间
     */
    public static String getStringByFormat(String strDate, String format) {
        String mDateTime = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormatYMDHMS);
            c.setTime(mSimpleDateFormat.parse(strDate));
            SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format);
            mDateTime = mSimpleDateFormat2.format(c.getTime());
        } catch (Exception e) {
        }
        return mDateTime;
    }

    /**
     * 描述：获取表示当前日期时间的字符串.
     *
     * @param format 格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String String类型的当前日期时间
     */
    public static String getCurrentDateByFormat(String format) {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            Calendar c = new GregorianCalendar();
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
        }
        return curDateTime;

    }

    /**
     * 描述：计算两个日期所差的天数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的天数
     */
    public static int getDifferenceDay(long date1, long date2) {
        return getDifferenceDay(new Date(date1), new Date(date2));
    }

    protected static int getDifferenceDay(Date now, Date returnDate) {
        Calendar cNow = Calendar.getInstance();
        Calendar cReturnDate = Calendar.getInstance();
        cNow.setTime(now);
        cReturnDate.setTime(returnDate);
        setTimeToMidnight(cNow);
        setTimeToMidnight(cReturnDate);
        long todayMs = cNow.getTimeInMillis();
        long returnMs = cReturnDate.getTimeInMillis();
        long intervalMs = todayMs - returnMs;

        int intervalDay = (int) (intervalMs / (1000 * 86400));
        return intervalDay < 0 ? -intervalDay : intervalDay;
    }

    private static void setTimeToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    /**
     * 描述：计算两个日期所差的月份.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的月份
     */
    public static int getDifferenceMonth(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        //先判断是否同年
        int y1 = calendar1.get(Calendar.YEAR);
        int y2 = calendar2.get(Calendar.YEAR);
        int d1 = calendar1.get(Calendar.MONTH);
        int d2 = calendar2.get(Calendar.MONTH);

        int month = (y1 - y2) * 12 + d1 - d2;
        return month < 0 ? -month : month;
    }

    /**
     * 描述：计算两个日期所差的小时数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的小时数
     */
    public static int getDifferenceHour(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int h1 = calendar1.get(Calendar.HOUR_OF_DAY);
        int h2 = calendar2.get(Calendar.HOUR_OF_DAY);
        int h;
        int day = getDifferenceDay(date1, date2);
        h = h1 - h2 + day * 24;
        return h;
    }

    /**
     * 描述：计算两个日期所差的分钟数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的分钟数
     */
    public static int getDifferenceMinutes(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int m1 = calendar1.get(Calendar.MINUTE);
        int m2 = calendar2.get(Calendar.MINUTE);
        int h = getDifferenceHour(date1, date2);
        int m;
        m = m1 - m2 + h * 60;
        return m;
    }

    /**
     * 描述：根据时间返回几天前或几分钟的描述.
     *
     * @param strDate the str date
     * @return the string
     */
    public static String formatDateStr2Desc(String strDate, String outFormat) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c2.setTime(df.parse(strDate));
            c1.setTime(new Date());
            int d = getDifferenceDay(c1.getTimeInMillis(), c2.getTimeInMillis());
            if (d == 0) {
                int h = getDifferenceHour(c1.getTimeInMillis(), c2.getTimeInMillis());
                if (h > 0) {
                    return h + "小时前";
                } else if (h < 0) {
                    return Math.abs(h) + "小时后";
                } else if (h == 0) {
                    int m = getDifferenceMinutes(c1.getTimeInMillis(), c2.getTimeInMillis());
                    if (m > 0) {
                        return m + "分钟前";
                    } else if (m < 0) {
                        return Math.abs(m) + "分钟后";
                    } else {
                        return "刚刚";
                    }
                }
            } else if (d > 0) {
                if (d == 1) {
                    return "昨天";
                } else if (d == 2) {
                    return "前天";
                }
                return Math.abs(d) + "天前";
            } else if (d < 0) {
                if (d == -1) {
                    return "明天";
                } else if (d == -2) {
                    return "后天";
                }
                return Math.abs(d) + "天后";
            }

            String out = getStringByFormat(strDate, outFormat);
            if (!StringUtils.isEmpty(out)) {
                return out;
            }
        } catch (Exception e) {
        }
        return strDate;
    }

    /**
     * 根据毫秒时间返回几天前或几分钟的描述.
     * @param timestamp
     */
    public static String formatDateStr2Desc(long timestamp) {
        long currentSeconds = DateUtils.getCurrTimeMillis() / 1000;
        long interval = currentSeconds - timestamp / 1000;// 与现在时间相差秒数
        String timeStr;
        if (interval > 24 * 60 * 60) {// 1天以上
            timeStr = interval / (24 * 60 * 60) + "天前";
        } else if (interval > 60 * 60) {// 1小时-24小时
            timeStr = interval / (60 * 60) + "小时前";
        } else if (interval > 60) {// 1分钟-59分钟
            timeStr = interval / 60 + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    public static long getCurrTimeMillis() {
        return System.currentTimeMillis();
    }


    /**
     * 本系统年转换成天
     */
    public static final int DAY_OF_YEAR = 360;
    /**
     * 本系统月换成天
     */
    public static final int DAY_OF_MONTH = 30;

    /**
     * 计算耗时
     *
     * @param millis
     * @return
     */
    public static String convertHMSWithMillis(long millis) {
        if (millis < 60) {
            return millis + "秒";
        } else if (millis < 3600) {
            return "约" + (millis / 60) + "分钟";
        } else if (millis < 3600 * 24) {
            return "约" + millis / 3600 + "小时";
        } else {
            return "约" + (millis / 3600 / 24) + "天";
        }
    }

    //判断两个日期是否是同一天
    public static boolean isSameDate(Long long1, Long long2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(new Date(long1));
        c2.setTime(new Date(long2));
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
                && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                && (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 将日期转化成毫秒
     *
     * @param format
     * @return
     */
    public static long date2longTime(String dateString, String format) {

        //先把字符串转成Date类型
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        //此处会抛异常
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //获取毫秒数
        long longDate = date.getTime();

        return longDate;
    }

    /**
     * 根据日期获取星期
     *
     * @param date
     * @return
     */
    public static String date2Week(String date) {

        String[] arg = date.split("-");
        int year = Integer.valueOf(arg[0]);
        int month = Integer.valueOf(arg[1]);
        int day = Integer.valueOf(arg[2]);

        //获得一个日历
        Calendar calendar = Calendar.getInstance();

        //设置当前时间,月份是从0月开始计算
        calendar.set(year, month - 1, day);

        //星期表示1-7，是从星期日开始，
        int number = calendar.get(Calendar.DAY_OF_WEEK);
        String[] strWeek = {"", "周日", "周一", "周二", "周三", "周四", "周五", "周六",};

        return strWeek[number];

    }

    /**
     * 获取之后几天的时间
     */
    public static long getNextDay(int day) {
        return getNextDayByTime(day, -1);
    }

    /**
     * 获取之后几天的时间
     *
     * @param hour 将时间调整为hour:00；当大于23或小于0时则取当前时间
     */
    public static long getNextDayByTime(int day, int hour) {
        //获得一个日历
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getCurrTimeMillis());
        calendar.add(Calendar.DAY_OF_MONTH, day);
        if (hour > 0 && hour < 24) {
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, 0);
        }
        return calendar.getTimeInMillis();
    }

    /**
     * 将秒数转换成天具体的天时分秒
     * 比如172800S转换成2天0时0分0秒
     *
     * @param second
     * @return
     */
    public static String formatSecond(long second) {
        String timeStr = "0秒";
        if (second > 0) {
            StringBuilder sb = new StringBuilder();
            Integer days = (int) (second / (60 * 60 * 24));
            Integer hours = (int) (second / (60 * 60) - days * 24);
            Integer minutes = (int) (second / 60 - hours * 60 - days * 24 * 60);
            Integer seconds = (int) (second - minutes * 60 - hours * 60 * 60 - days * 24 * 60 * 60);
            if (days > 0) {
                sb.append(days).append("天");
            }
            if (hours > 0) {
                sb.append(hours).append("小时");
            }
            if (minutes > 0) {
                sb.append(minutes).append("分");
            }
            if (seconds > 0) {
                sb.append(seconds).append("秒");
            }
            if (sb.length() > 0) {
                return sb.toString();
            }
        }
        return timeStr;
    }
    static SimpleDateFormat sdf9 = new SimpleDateFormat("yyyy年MM月dd日HH");

    public static String format9(Date d) {
        return sdf9.format(d);
    }


    public static String[] getDayStartAndEndString(Calendar calendar){
        String[] strs = new String[2];
        strs[0] = formatTimeToYMD(calendar.getTime()) + " 00:00:00";
        strs[1] = formatTimeToYMD(calendar.getTime()) + " 23:59:59";
        return strs;
    }
}
