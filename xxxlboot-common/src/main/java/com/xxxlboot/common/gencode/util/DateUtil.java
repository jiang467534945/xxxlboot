package com.xxxlboot.common.gencode.util;


import com.xxxlboot.common.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Explain:     [时间工具类]
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 */
public class DateUtil {

/*    public static void main(String[] args) {
        String s = dateFormat(new Date(), null);

        getNowDateYMD();
    }*/

    public static final String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_YMD = "yyyy-MM-dd";

    public static final String FORMAT_HMS = "HH:mm:ss";

    public static final String FORMAT_NUMBER = "yyyyMMddHHmmss";

    //这种静态写法会有多线程漏洞,已废弃
//    public static final SimpleDateFormat SIMPLE_FORMAT_YMDHMS = new SimpleDateFormat(FORMAT_YMDHMS);
//    public static final SimpleDateFormat SIMPLE_FORMAT_YMD = new SimpleDateFormat(FORMAT_YMD);
//    public static final SimpleDateFormat SIMPLE_FORMAT_HMS = new SimpleDateFormat(FORMAT_HMS);
//    public static final SimpleDateFormat SIMPLE_FORMAT_NUMBER= new SimpleDateFormat(FORMAT_NUMBER);

    /**
     * 创建线程独立的SimpleDateFormat
     */
    public static final ThreadLocal<Map<String, SimpleDateFormat>> theradDateFormatMap = new ThreadLocal<Map<String, SimpleDateFormat>>(){
        @Override
        protected Map<String, SimpleDateFormat> initialValue() {
            Map<String, SimpleDateFormat> map = new HashMap<String, SimpleDateFormat>();
            map.put(FORMAT_YMDHMS, new SimpleDateFormat(FORMAT_YMDHMS));
            map.put(FORMAT_YMD, new SimpleDateFormat(FORMAT_YMD));
            map.put(FORMAT_HMS, new SimpleDateFormat(FORMAT_HMS));
            map.put(FORMAT_NUMBER, new SimpleDateFormat(FORMAT_NUMBER));
            return map;
        }
    };

    public static SimpleDateFormat getDateFormat(String format) {
        Map<String, SimpleDateFormat> formatMap = theradDateFormatMap.get();
        SimpleDateFormat dateFormat = formatMap.get(format);
        if (dateFormat != null) {
            return dateFormat;
        }
        return new SimpleDateFormat(format);
    }

    /**
     * 将日期进行格式化
     * @param date
     * @param format 日期格式,默认为"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String dateFormat(Date date, String format) {
        if (format == null) {
            format = FORMAT_YMDHMS;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 将字符串按照指定格式解析为Date对象
     * @param value 要解析的日期字符串
     * @param format 日期格式
     * @return Date 转换结果
     * @throws ParseException
     */
    public static Date parseDate(String value, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date parse = simpleDateFormat.parse(value);
        return parse;
    }

    /**
     * 得到当前日期格式为yyyy-MM-dd字符串
     *
     * @return
     */
    public static String getNowDateYMD() {
        return getDateFormat(FORMAT_YMD).format(new Date());
    }

    /**
     * 得到当前日期格式为yyyy-MM-dd HH:mm:ss字符串
     *
     * @return
     */
    public static String getNowDateYMDHMS() {
        return getDateFormat(FORMAT_YMDHMS).format(new Date());
    }

    /**
     * 得到当前日期格式为HH:mm:ss字符串
     *
     * @return
     */
    public static String getNowDateHMS() {
        return getDateFormat(FORMAT_HMS).format(new Date());
    }

    /**
     * 根据字符串格式去转换相应格式的日期和时间
     *
     * @param date 必要参数
     * @return java.util.Date
     * @throws ParseException 如果参数格式不正确会抛出此异常
     */
    public static Date convertToDate(String date) throws ParseException {
        SimpleDateFormat simple = null;
        date = StringUtil.trim(date);
        switch (date.length()) {
            case 19:// 日期+时间
                simple = getDateFormat(FORMAT_YMDHMS);
                break;
            case 10:// 仅日期
                simple = getDateFormat(FORMAT_YMD);
                break;
            case 8:// 仅时间
                simple = getDateFormat(FORMAT_HMS);
                break;
            default:
                simple = getDateFormat(FORMAT_YMDHMS);
                break;
        }
        return simple.parse(date);
    }

    /**
     * 根据字符串格式去转换相应格式的日期和时间为sqlDate
     *
     * @param date 必要参数
     * @return java.util.Date
     * @throws ParseException 如果参数格式不正确会抛出此异常
     */
    public static java.sql.Date convertToSqlDate(String date) {
        SimpleDateFormat simple = null;
        date = StringUtil.trim(date);
        switch (date.length()) {
            case 19:// 日期+时间
                simple = getDateFormat(FORMAT_YMDHMS);
                break;
            case 10:// 仅日期
                simple = getDateFormat(FORMAT_YMD);
                break;
            case 8:// 仅时间
                simple = getDateFormat(FORMAT_HMS);
                break;
            default:
                simple = getDateFormat(FORMAT_YMDHMS);
                break;
        }
        try {
            java.sql.Date sqldate = new java.sql.Date(simple.parse(date).getTime());
            return sqldate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前时间的指定格式字符串
     *
     * @param format
     * @return
     */
    public static String getNowFormatDate(String format) {
        if (format == null) {
            return getDateFormat(FORMAT_YMDHMS).format(new Date());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前时间的纯数字字符串,例如20170720110101
     * @return
     */
    public static String getNowNumberDate() {
        return getDateFormat(FORMAT_NUMBER).format(new Date());
    }
}
