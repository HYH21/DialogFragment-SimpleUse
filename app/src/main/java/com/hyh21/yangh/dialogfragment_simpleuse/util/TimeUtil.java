package com.hyh21.yangh.dialogfragment_simpleuse.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * com.tjsoft.lwx.util.common.TimeUtil.java
 *
 * @author Dandelion
 *         This class is all static method to support the current date,time,month and so on
 *         此类均为静态方法，用于获得当前日期，时间等等
 * @since 2008-08-09
 *
 * modify by yanghuang on 2016/05/14
 * 将主要对象Date改为calendar
 */
public final class TimeUtil {

    private TimeUtil() {
    }

    public static final String YEAR_MONTH = "yyyy-MM";//year month
    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";//year-month-day
    public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";//common detail time for dataBase
    public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_MILLISECOND = "yyyy-MM-dd HH:mm:ss,SSS";//detail time for log
    public static final String DEFAULT_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";//EEE MMM dd HH:mm:ss zzz yyyy

    /**
     * 将calendar转成string 格式：yyyy-MM-dd HH:mm:ss
     *
     * @param calendar
     * @return
     */
    public static String calendarToString(final Calendar calendar) {
        return calendarToString(calendar, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
    }

    /**
     * 将calendar转成string
     *
     * @param format
     * @return
     */
    public static String calendarToString(final Calendar calendar, final String format) {
        return calendarToString(calendar, format, null);
    }

    /**
     * 将calendar转成string
     *
     * @param calendar
     * @param format
     * @param locale
     * @return String
     */
    public static String calendarToString(final Calendar calendar, final String format, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale == null ? Locale.getDefault() : locale);
        return sdf.format(calendar.getTime());
    }

    /**
     * 将字符串转换为calendar 格式：yyyy-MM-dd HH:mm:ss
     *
     * @param string
     * @return
     */
    public static Calendar stringToCalendar(final String string) {
        return stringToCalendar(string, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
    }

    /**
     * 将字符串转换为calendar
     *
     * @param string
     * @param format
     * @return
     */
    public static Calendar stringToCalendar(final String string, final String format) {
        return stringToCalendar(string, format, null);
    }

    /**
     * 将字符串转换为calendar
     *
     * @param string
     * @param format
     * @param locale
     * @return Date
     */
    public static Calendar stringToCalendar(final String string, final String format, final Locale locale) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale == null ? Locale.getDefault() : locale);
        try {
            date = sdf.parse(string);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 根据格式获得当前的时间 返回类型：string
     *
     * @param format
     * @return String
     */
    public static String formatCurrentTime(final String format) {
        return calendarToString(getTime(), format);
    }

    /**
     * <p>获得当前的日期 格式：[year-month-day] 返回类型：string</p>
     *
     * @return String
     */
    public static String getCurrentDay() {
        return formatCurrentTime(YEAR_MONTH_DAY);
    }

    /**
     * <p>获得当前的时间 格式：[year-month-day hour-minute-second-millsecond] 返回类型：string</p>
     *
     * @return String
     */
    public static String getCurrentMillisTime() {
        return formatCurrentTime(YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_MILLISECOND);
    }

    /**
     * <p>获得当前的时间 格式：[year-month-day hour-minute-second] 返回类型：string</p>
     *
     * @return String
     */
    public static String getCurrentSecondTime() {
        return formatCurrentTime(YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
    }

    /**
     * <p>获得当前年月 返回类型：string</p>
     *
     * @return String
     */
    public static String getCurrentYearMonth() {
        return formatCurrentTime(YEAR_MONTH);
    }

    /**
     * <p>获得当前日期 返回类型：Calendar</p>
     *
     * @return java.util.Date
     */
    public static Calendar getTime() {
        return Calendar.getInstance(Locale.getDefault());
    }

    /**
     * <p>获得当前日期 返回类型：Calendar</p>
     *
     * @param locale
     * @return java.util.Date
     */
    public static Calendar getTime(Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return Calendar.getInstance(locale);
    }

    /**
     * <p>获得当前的时间戳，返回类型：Timestamp，用于：dataBase</p>
     *
     * @return java.sql.Timestamp
     */
    public static Timestamp getCurrentTimestamp() {
        return (new Timestamp(System.currentTimeMillis()));
    }

    /**
     * <p>获得当前星期几（数字），返回类型：int</p>
     *
     * @return String
     */
    public static int getCurrentDayOfWeekArabia() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * <p>获得当前星期几（中文缩写），返回类型：string</p>
     *
     * @return String
     */
    public static String getCurrentDayOfWeekChinese() {
        String[] dayOfWeek = {"日", "一", "二", "三", "四", "五", "六"};
        return dayOfWeek[getCurrentDayOfWeekArabia() - 1];
    }

    /**
     * <p>获得当前星期几（英文），返回类型：string</p>
     *
     * @return String
     */
    public static String getCurrentDayOfWeekEnglish() {
        String[] dayOfWeek = {"Sunday", "Monday", "Tuseday", "Wednesday", "Thursday", "Friday", "Saturday"};
        return dayOfWeek[getCurrentDayOfWeekArabia() - 1];
    }

    /**
     * 获得用于当id格式的时间
     *
     * @return String
     */
    public static String getMillisId() {
        return formatCurrentTime("yyyyMMddHHmmssSSS");
    }

    /**
     * 将相对时间转为Calendar
     *
     * @param timeMillis
     * @return Date
     */
    public static Calendar timeMillisToCalendar(final long timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        return calendar;
    }

    public static Calendar dateToCalendar(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static void main(String[] args) {
        System.out.println(getCurrentDayOfWeekArabia());
        System.out.println(getCurrentDayOfWeekChinese());
        System.out.println(System.currentTimeMillis());
        System.out.println(getCurrentSecondTime());
        System.out.println(getMillisId());
        System.out.println(calendarToString(Calendar.getInstance(), "yyyy-MM-dd"));
        System.out.println(timeMillisToCalendar(Long.parseLong("1291883095078")));
    }
}