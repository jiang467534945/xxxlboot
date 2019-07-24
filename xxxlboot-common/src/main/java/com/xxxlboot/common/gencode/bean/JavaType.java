package com.xxxlboot.common.gencode.bean;

/**
 * Explain:     [Java类型bean]
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 */
public class JavaType {

    /**Java类型(简写)*/
    private String javaType;
    /**Java类型(基本类型)*/
    private String baseJavaType;
    /**Java类型(全路径,含包名)*/
    private String fullPathJavaType;

    public JavaType() {
    }

    public JavaType(String javaType, String baseJavaType, String fullPathJavaType) {
        this.javaType = javaType;
        this.baseJavaType = baseJavaType;
        this.fullPathJavaType = fullPathJavaType;
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
}
