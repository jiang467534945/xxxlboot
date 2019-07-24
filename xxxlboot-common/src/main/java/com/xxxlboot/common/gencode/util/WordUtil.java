package com.xxxlboot.common.gencode.util;


import com.xxxlboot.common.gencode.constant.DbConst;
import com.xxxlboot.common.util.StringUtil;

/**
 * Explain:     [单词工具类]
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 */
public class WordUtil {

    /**
     * 驼峰命名法转换为下划线分隔
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if(StringUtil.isEmpty(param)) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; ++i) {
            char c = param.charAt(i);
            if(Character.isUpperCase(c) && i != 0) {
                sb.append('_');
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 下划线分隔命名转换为驼峰命名法,例如ACCOUNT_ID转化成accountId
     * 如果传入的参数不包含下划线,则转换成小写返回
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if(StringUtil.isEmpty(param)) {
            return "";
        } else {
            String temp = param.toLowerCase();
            int len = temp.length();
            StringBuilder sb = new StringBuilder(len);
            boolean mark = false;
            for(int i = 0; i < len; ++i) {
                char c = temp.charAt(i);
                if(c == '_') {
                    mark = true;
                } else {
                    if (mark) {
                        sb.append(Character.toUpperCase(c));
                        mark = false;
                    } else {
                        sb.append(c);
                    }
                }
            }
            return sb.toString();
        }
    }

    /**
     * 将单词转换成首字母大写
     * 此方法会去除传入字符串前后的空白字符,并将第一个字符转换成大写
     *
     * @param str
     * @return
     */
    public static String initialUp(String str) {
        str = StringUtil.trim(str);
        if (StringUtil.isEmpty(str)) {
            return "";
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
    }

    /**
     * 将单词转换成首字母小写
     * 此方法会去除传入字符串前后的空白字符,并将第一个字符转换成小写
     *
     * @param str
     * @return
     */
    public static String initialLow(String str) {
        if (str == null) {
            return "";
        }
        str = str.trim();
        if (str.length() == 0 || str.equals("")) {
            return "";
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1, str.length());
    }

    /**
     * 将字符串转换成大写
     * @param str
     * @return
     */
    public static String toUppercase(String str){
        if (StringUtil.isEmpty(str)) {
            return "";
        }
        return str.toUpperCase();
    }

    /**
     * 将字符串转换成小写
     * @param str
     * @return
     */
    public static String toLowerCase(String str){
        if (StringUtil.isEmpty(str)) {
            return "";
        }
        return str.toLowerCase();
    }

    /**
     * 根据名称自动识别驼峰命名法或下划线命名规则,返回DbConst.NAMING_UNDERLINE_DIVIDE或DbConst.NAMING_CAMELCASE
     * 算法规则:
     *  name为空返回驼峰命名法;
     *  有下划线则返回下划线分隔命名法;
     *  包含大写与小写字符返回驼峰命名法,否则返回下划线分隔命名法
     * @param name
     * @return
     */
    public static String automaticNamingRule(String name) {
        if(StringUtil.isEmpty(name)){
            return DbConst.NAMING_CAMELCASE;
        }
        boolean haveUnderline = false;
        boolean haveUpperWord = false;
        boolean haveLowerWord = false;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == '_') {
                haveUnderline = true;
                break;
            }
            if (Character.isUpperCase(c)){
                haveUpperWord = true;
            }
            if (Character.isLowerCase(c)){
                haveLowerWord = true;
            }
        }
        if (haveUnderline) {
            return DbConst.NAMING_UNDERLINE_DIVIDE;
        }
        if (haveLowerWord && haveUpperWord) {
            return DbConst.NAMING_CAMELCASE;
        }
        return DbConst.NAMING_UNDERLINE_DIVIDE;
    }

    /**
     * 根据命名规则将name转换为java规范的property(驼峰命名法,首字母小写)
     * @param name
     * @param namingRule
     * @return
     */
    public static String converToProperty(String name, String namingRule) {
        if (StringUtil.isEmpty(namingRule) || namingRule == DbConst.NAMING_AUTO) {
            namingRule = WordUtil.automaticNamingRule(name);
        }
        if (namingRule == DbConst.NAMING_UNDERLINE_DIVIDE) {
            name = WordUtil.underlineToCamel(name);
            name = WordUtil.initialLow(name);


        } else if (namingRule == DbConst.NAMING_CAMELCASE) {
            name = WordUtil.initialLow(name);
        } else {
            throw new RuntimeException("异常!不能识别的命名规则,请准确配置命名规则DbConst.NAMING_UNDERLINE_DIVIDE或DbConst.NAMING_CAMELCASE");
        }
        return name;
    }

  /*  public static void main(String[] args) {
        String a = "user_account";

        String b = "userAccount";

        String c = "user";

        String d = "user_map";

        String e = "userMap";

    }*/
}
