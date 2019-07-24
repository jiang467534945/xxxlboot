package com.xxxlboot.common.gencode.util;

import com.xxxlboot.common.gencode.constant.GeneratorConst;
import com.xxxlboot.common.util.StringUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Explain:     [配置信息工具类]
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 * @author easy
 */
public class ConfigUtil {

    /**转换值Map-key的key*/
    public static final String CONVERT_VALUE_MAP_KEY = "id";
    /**转换值Map-value的key*/
    public static final String CONVERT_VALUE_MAP_VALUE = "text";
    //匹配配置信息
    private static final String COMMENT_CGHREGEX = "\\{\\{.+\\}\\}$";
    private static final Pattern COMMENTCFG_PATTERN = Pattern.compile(COMMENT_CGHREGEX);
    //1.5版本时的匹配key：值转换配置信息
    private static final String COMMENT_CONVERREGEX = "\\(.+:.+\\)$";
    private static final Pattern COMMENT_CONVER_PATTERN = Pattern.compile(COMMENT_CONVERREGEX);
    //匹配"1:选项一..."格式的字符串
    private static final String CONVERT_REGEX = "^.+:.+$";
    private static final Pattern CONVERT_PATTERN = Pattern.compile(CONVERT_REGEX);

    /**
     * 解析注释的配置
     * @param comment
     * @return
     */
    public static Map<String, String> analysisCommentCfg(String comment) {
        comment = StringUtil.trim(comment);
        Map<String, String> cfgMap = new HashMap<String, String>(50);
        Matcher matcher = COMMENTCFG_PATTERN.matcher(comment);
        if (matcher.find()) {
            String orders = matcher.group();
            orders = orders.substring(2, orders.length() - 2);
            String mark = "";
            boolean markOn = false;
            StringBuilder orderTemp = new StringBuilder();
            boolean orderOn = true;
            StringBuilder valueTemp = new StringBuilder();
            boolean valueOn = false;
            for (int i = 0, len = orders.length(); i < len; i++) {
                char c = orders.charAt(i);
                if (c == '\'' || c == '"') {
                    //引号打开状态?
                    if (markOn) {
                        if (mark.equals(""+c)) {
                            //与开启引号相同?
                            markOn = false;
                            if (orderOn) {
                                //指令?
                                orderOn = false;
                                valueOn = true;
                            } else if (valueOn) {
                                //值?
                                valueOn = false;
                                orderOn = true;
                            } else {
                                System.out.printf("解析Comment:%s配置不符合语法,已跳过,位置%d,'%s'\n", orders, i, c);
                                cfgMap.clear();
                                break;
                            }
                        } else {
                            if (orderOn) {
                                orderTemp.append(c);
                            } else if (valueOn) {
                                valueTemp.append(c);
                            }
                        }
                    } else {
                        markOn = true;
                        mark = ""+c;
                    }
                } else if (c == ':') {
                    if (markOn) {
                        if (orderOn) {
                            orderTemp.append(c);
                        } else if (valueOn) {
                            valueTemp.append(c);
                        }
                    } else {
                        if (orderOn) {
                            orderOn = false;
                            valueOn = true;
                        } else {
                            System.out.printf("解析Comment:%s配置不符合语法,已跳过,位置%d,'%s'\n", orders, i, c);
                            cfgMap.clear();
                            break;
                        }
                    }
                } else if (c == ' ') {
                    if (markOn) {
                        //空格只有在引号内才有效,其它的忽略
                        if (orderOn) {
                            orderTemp.append(c);
                        } else if (valueOn) {
                            valueTemp.append(c);
                        }
                    }
                } else {
                    if (c == ',') {
                        if (markOn) {
                            if (orderOn) {
                                orderTemp.append(c);
                            } else if (valueOn) {
                                valueTemp.append(c);
                            }
                        } else {
                            if (orderOn) {
                                orderOn = true;
                                valueOn = false;
                                cfgMap.put(orderTemp.toString(), valueTemp.toString());
                                //存储配置
                                orderTemp.setLength(0);
                                valueTemp.setLength(0);
                            } else if (valueOn) {
                                orderOn = true;
                                valueOn = false;
                                cfgMap.put(orderTemp.toString(), valueTemp.toString());
                                //存储配置
                                orderTemp.setLength(0);
                                valueTemp.setLength(0);
                            } else {
                                System.out.printf("解析Comment:%s配置不符合语法,已跳过,位置%d,'%s'\n", orders, i, c);
                                cfgMap.clear();
                                break;
                            }
                        }
                    } else {
                        if (orderOn) {
                            orderTemp.append(c);
                        } else if (valueOn) {
                            valueTemp.append(c);
                        } else {
                            System.out.printf("解析Comment:%s配置不符合语法,已跳过,位置%d,'%s'\n", orders, i, c);
                            cfgMap.clear();
                            break;
                        }
                    }
                }
            }
            if (orderTemp.length() > 0 && !markOn) {
                //存储最后一个配置，此解析规则有bug，但不影响正常使用，暂时忽略
                cfgMap.put(orderTemp.toString(), valueTemp.toString());
                //存储配置
                orderTemp.setLength(0);
                valueTemp.setLength(0);
            }
        } else {
            //如果没找到匹配{{}}的配置，尝试兼容1.5版本的(1:男,2:女)的值转换配置
            Matcher converMatcher = COMMENT_CONVER_PATTERN.matcher(comment);
            if (converMatcher.find()) {
                String converCfg = converMatcher.group();
                converCfg = converCfg.substring(1, converCfg.length() - 1);
                cfgMap.put(GeneratorConst.COLUMN_COVER_ORDER, converCfg);
            }
        }
        return cfgMap;
    }

    /**
     * 解析值转换配置
     * 将字符串"1:选项一,2:选项二"转换成[{id=1, text=选项一}, {id=2, text=选项二}]List对象
     * @param comment
     * @return
     */
    public static List<Map> analysisConvertCfg(String comment) {
        comment = StringUtil.trim(comment);
        List<Map> converList = new ArrayList<Map>();
        Matcher matcher = CONVERT_PATTERN.matcher(comment);
        if (matcher.find()) {
            String s = matcher.group();
            String[] split = s.split(",");
            for (int i = 0; i < split.length; i++) {
                String[] vals = split[i].split(":");
                if (vals != null && vals.length == 2) {
                    Map map = new LinkedHashMap();
                    map.put(CONVERT_VALUE_MAP_KEY, vals[0]);
                    map.put(CONVERT_VALUE_MAP_VALUE, vals[1]);
                    converList.add(map);
                }
            }
        }
        return converList;
    }

    /**
     * 移除comment的配置
     * @param comment
     * @return
     */
    public static String removeConfig(String comment) {
        comment = StringUtil.trim(comment);
        Matcher matcher = COMMENTCFG_PATTERN.matcher(comment);
        if (matcher.find()) {
            int indexOf = comment.lastIndexOf("{{");
            return comment.substring(0, indexOf);
        } else {
            //如果没找到匹配{{}}的配置，尝试兼容1.5版本的(1:男,2:女)的值转换配置
            Matcher converMatcher = COMMENT_CONVER_PATTERN.matcher(comment);
            if (converMatcher.find()) {
                int indexOf = comment.lastIndexOf("(");
                return comment.substring(0, indexOf);
            }
        }
        return comment;
    }
}
