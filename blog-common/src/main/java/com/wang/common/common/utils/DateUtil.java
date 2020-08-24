package com.wang.common.common.utils;

import com.wang.common.common.base.BaseException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

/**
 * @author: wjx
 * @date: 2018/10/23 14:24
 * @description: 时间工具类
 */
public class DateUtil extends cn.hutool.core.date.DateUtil {
    /**
     * 获取当前时间的YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String formatTime(Date date) {
        SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdfFormat.format(date);
    }

    /**
     * 获取当前时间的YYYY-MM-DD格式
     *
     * @return
     */
    public static String formatDateTime(Date date){
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sdfDateFormat.format(date);
    }

    /**
     * 获取当前时间的YYYY-MM格式
     *
     * @return
     */
    public static String formatMonthTime(Date date) {
        SimpleDateFormat sdfMonthFormat = new SimpleDateFormat("yyyy-MM");
        return sdfMonthFormat.format(date);
    }

    /**
     * 获取当前时间的YYYY格式
     *
     * @return
     */
    public static String formatYearTime(Date date) {
        SimpleDateFormat sdfYearFormat = new SimpleDateFormat("yyyy");
        return sdfYearFormat.format(date);
    }


    /**
     * 获取当前时间的YYYY-MM-DD HH:mm格式
     *
     * @return
     */
    public static String formatMinuteTime(Date date) {
        SimpleDateFormat sdfMinuteFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdfMinuteFormat.format(date);
    }


    /**
     * 日期比较，如果s>=e 返回true 否则返回false
     * @param s
     * @param e
     * @return
     */
    public static boolean compareDate(String s, String e) {
        if(getDate(s)==null||getDate(e)==null){
            return false;
        }
        return s.compareTo(e)>=0;
    }

    /**
     * 格式化日期 yyyy-MM-dd
     * @param date
     * @return
     */
    public static Date getDate(String date) {
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdfDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BaseException("日期格式错误");
        }
    }

    /**
     * 格式化日期 yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static Date getTime(String date){
        SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdfFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BaseException("日期格式错误");
        }
    }

    /**
     * 格式化日期 yyyy-MM
     * @param date
     * @return
     */
    public static Date getMonth(String date){
        SimpleDateFormat sdfMonthFormat = new SimpleDateFormat("yyyy-MM");
        try {
            return sdfMonthFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("日期格式错误");
        }
    }

    /**
     * 格式化日期 yyyy-MM
     * @param date
     * @return
     */
    public static Date getYear(String date){
        SimpleDateFormat sdfYearFormat = new SimpleDateFormat("yyyy");
        try {
            return sdfYearFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("日期格式错误");
        }
    }

    /**
     * 格式化日期 yyyy-MM-DD HH:mm
     * @param date
     * @return
     */
    public static Date getMinute(String date){
        SimpleDateFormat sdfMinuteFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return sdfMinuteFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BaseException("日期格式错误");
        }
    }

    /**
     * 获取当前时间的后i天
     * @param i
     * @return
     */
    public static String getAddDay(int i){
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = DateUtil.formatTime(new Date());
        GregorianCalendar gCal = new GregorianCalendar(
                Integer.parseInt(currentTime.substring(0, 4)),
                Integer.parseInt(currentTime.substring(5, 7)) - 1,
                Integer.parseInt(currentTime.substring(8, 10)));
        gCal.add(GregorianCalendar.DATE, i);
        return sdfDateFormat.format(gCal.getTime());
    }

    /**
     * 获取当前时间的后i天
     * 精确到秒
     * @param i
     * @return
     */
    public static String getAddDayTime(int i){
        SimpleDateFormat sdf_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis()+i*24*60*60*1000);
        return sdf_format.format(date);
    }

    /**
     * 获取当前时间的+多少秒
     * 精确到秒
     * @param i
     * @return
     */
    public static String getAddDaySecond(Long i){
        SimpleDateFormat sdf_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis()+i*1000);
        return sdf_format.format(date);
    }

    public static Date getDayBefore(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day1 = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day1 - 1);
        Date result = getDate(formatDateTime(c.getTime()));
        return result;
    }

    public static Date getDayAfter(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);
        Date result = getDate(formatDateTime(c.getTime()));
        return result;
    }

    public static String getDateTime(Timestamp timestamp){
        SimpleDateFormat sdf_date_format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf_date_format.format(timestamp);
        return dateString;
    }

    public static Date firstDayLastMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //设置当前月的下一个月
        cal.add(Calendar.MONTH, 1);
        Date lastMonth = cal.getTime();
        return lastMonth;
    }

    public static Date lastDayMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //设置当前月的下一个月
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastMonth = cal.getTime();
        return lastMonth;
    }

    public static Date lastDayYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //设置当前月的下一个月
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        Date lastMonth = cal.getTime();
        return lastMonth;
    }

    public static Integer getApartYear(String strDate){
        if(StringUtil.isEmpty(strDate)){
            throw new BaseException("日期不能为空");
        }
        Date date = getDate(strDate);
        Calendar cal = Calendar.getInstance();
        if (cal.before(date) || !Optional.ofNullable(date).isPresent()) {
            throw new BaseException("日期格式错误");
        }
        int yearNow = cal.get(Calendar.YEAR);
        cal.setTime(date);
        int yearBirth = cal.get(Calendar.YEAR);
        int year = yearNow - yearBirth;
        return year;
    }

    /**
     * 获取当前时间的月份
     *
     * @return
     */
    public static String getMonth(Date date) {
        SimpleDateFormat sdfMonthFormat = new SimpleDateFormat("MM");
        return sdfMonthFormat.format(date);
    }

    /**
     * 获取当前时间的月份+天
     *
     * @return
     */
    public static String getMonthDay(Date date) {
        SimpleDateFormat sdfMonthFormat = new SimpleDateFormat("MM-dd");
        return sdfMonthFormat.format(date);
    }
}
