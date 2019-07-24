package com.xxxlboot.common.gencode.bean;


import java.util.List;
import java.util.Map;

/**
 * Explain:     []
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 * @author easy
 */
public class GeneratorColumnInfo extends ColumnInfo {

    /**列注释(简写,去除了配置信息)*/
    private String simpleColumnComment;

    /////////// Comment配置 /////////////
    private Map columnConfig;
    /**值转换列表*/
    private List<Map> coverList;
    /**字段添加到页面表单隐藏域*/
    private boolean hidden = false;
    /**页面隐藏此字段信息*/
    private boolean blank = false;
    /**数据校验正则表达式*/
    private String regex;
    /**字段支持区间查询*/
    private boolean interval = false;
    /**使用模糊查询*/
    private boolean like = false;

    /**java属性名*/
    private String property;
    /**首字母大写属性名*/
    private String upperProperty;

    /**Java类型(包装类)*/
    private String javaType;
    /**Java类型(基本类型)*/
    private String baseJavaType;
    /**Java类型(全路径,含包名)*/
    private String fullPathJavaType;

    public String getSimpleColumnComment() {
        return simpleColumnComment;
    }

    public void setSimpleColumnComment(String simpleColumnComment) {
        this.simpleColumnComment = simpleColumnComment;
    }

    public Map getColumnConfig() {
        return columnConfig;
    }

    public void setColumnConfig(Map columnConfig) {
        this.columnConfig = columnConfig;
    }

    public List<Map> getCoverList() {
        return coverList;
    }

    public void setCoverList(List<Map> coverList) {
        this.coverList = coverList;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isBlank() {
        return blank;
    }

    public void setBlank(boolean blank) {
        this.blank = blank;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public boolean isInterval() {
        return interval;
    }

    public void setInterval(boolean interval) {
        this.interval = interval;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getUpperProperty() {
        return upperProperty;
    }

    public void setUpperProperty(String upperProperty) {
        this.upperProperty = upperProperty;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getBaseJavaType() {
        return baseJavaType;
    }

    public void setBaseJavaType(String baseJavaType) {
        this.baseJavaType = baseJavaType;
    }

    public String getFullPathJavaType() {
        return fullPathJavaType;
    }

    public void setFullPathJavaType(String fullPathJavaType) {
        this.fullPathJavaType = fullPathJavaType;
    }

    @Override
    public String toString() {
        return "GeneratorColumnInfo{" +
                "simpleColumnComment='" + simpleColumnComment + '\'' +
                ", columnConfig=" + columnConfig +
                ", coverList=" + coverList +
                ", hidden=" + hidden +
                ", blank=" + blank +
                ", regex='" + regex + '\'' +
                ", interval=" + interval +
                ", like=" + like +
                ", property='" + property + '\'' +
                ", upperProperty='" + upperProperty + '\'' +
                ", javaType='" + javaType + '\'' +
                ", baseJavaType='" + baseJavaType + '\'' +
                ", fullPathJavaType='" + fullPathJavaType + '\'' +
                '}';
    }

}
