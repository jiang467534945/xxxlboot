package com.xxxlboot.common.util;

import java.util.UUID;

/**
 * Explain:     []
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 */
public class StringUtil {

    /**
     * 方法描述:  [格式化字符串强制在一行内，这会去除所有换行符]
     * 创建人:    [Easy]
     * 创建时间:  [2017-4-24 下午1:58:40]
     * @param obj
     * @return
     */
    public static String formatStrInLine(Object obj){
        String str = obj.toString();
		/*
		 * 在regex中"\\"表示一个"\"，在java中一个"\"也要用"\\"表示。
		 * 这样，前一个"\\"代表regex中的"\"，后一个"\\"代表java中的"\"。
		 * 所以要想使用replaceAll方法将字符串中的反斜杠("\")替换成空字符串("")，则需要这样写：str.replaceAll("\\\\","");
		 */
        str = str.replaceAll("[(\r\n)|(\n)]+|(\\\\r\\\\n)+", "");
        return str;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean notEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串str是否在字符串数组datas中存在
     * @param str
     * @param datas
     * @return
     */
    public static boolean whereStrIn(String str, String... datas) {
        if (datas == null) {
            return false;
        }
        for (String data:datas) {
            if (data == str) {
                return true;
            }
            if (str != null && str.equals(data)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串str是否在字符串数组datas中存在,忽略大小写
     * @param str
     * @param datas
     * @return
     */
    public static boolean whereStrInIgnoreCase(String str, String... datas) {
        if (datas == null) {
            return false;
        }
        for (String data:datas) {
            if (data == str) {
                return true;
            }
            if (str != null && str.equalsIgnoreCase(data)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 生成新的uuid
     * @return
     */
    public static String uuid(){
        String s = UUID.randomUUID().toString();
        return s.replace("-", "").toUpperCase();
    }

    /**
     * 去除字符串前后空白字符,对象为空返回""
     * @param str
     * @return
     */
    public static String trim(String str) {
        return isEmpty(str) ? "" : str.trim();
    }
    public static String trim(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString().trim();
    }

    /**
     * 将目标字符串以指定字符串结尾的字符替换为新的字符,若不是以指定字符结尾,则返回原字符串
     * @param data 原字符串
     * @param endStr 结尾字符串
     * @param newStr 替换的字符串
     * @return
     */
    public static String replaceEndStr(String data, String endStr, String newStr) {
        if (data != null && endStr != null) {
            if (data.endsWith(endStr)) {
                int index = data.lastIndexOf(endStr);
                return data.substring(0, index) + newStr;
            }
        }
        return data;
    }

    /**
     * 判断字符串是否包含指定的子串
     * @param data 字符串数据
     * @param target 子串
     * @return data为空或不包含返回false,包含返回true
     */
    public static boolean contain(String data, String target) {
        if (data == null) {
            return false;
        }
        return data.contains(target);
    }

    /**
     * 将字符串中的正则特殊字符进行转义
     * 进行转义的字符有：'$', '(', ')', '*', '+', '.', '[', ']', '?', '\\', '^', '{', '}', '|'
     * @param str 需要进行转义的字符串
     * @return 转义后的字符串。
     */
    public static String escapeRegexChars(String str){
        if (isEmpty(str)) {
            return str;
        }
        return str.replace("\\", "\\\\").replace("*", "\\*")
                .replace("+", "\\+").replace("|", "\\|")
                .replace("{", "\\{").replace("}", "\\}")
                .replace("(", "\\(").replace(")", "\\)")
                .replace("^", "\\^").replace("$", "\\$")
                .replace("[", "\\[").replace("]", "\\]")
                .replace("?", "\\?").replace(",", "\\,")
                .replace(".", "\\.").replace("&", "\\&");
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String replaceStartStr(String data, String startStr, String newStr) {
        if (data != null && startStr != null) {
            if (data.startsWith(startStr)) {
                int index = data.indexOf(startStr);
                return newStr + data.substring(index+1, data.length());
            }
        }
        return data;
    }
}
