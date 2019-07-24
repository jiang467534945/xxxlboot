package com.xxxlboot.common.gencode.constant;


import com.xxxlboot.common.gencode.bean.JavaType;

/**
 * JavaType常量，避免每次转换dbType到javaType都要new一个新对象
 * @author Easy
 * @version 1.0
 * @Email xiyousuiyuan@163.com
 * @Date 2018/10/10
 */
public class JavaTypeConst {
    public static final JavaType INT = new JavaType("Integer", "int", "java.lang.Integer");
    public static final JavaType LONG = new JavaType("Long", "long", "java.lang.Long");
    public static final JavaType STRING = new JavaType("String", "String", "java.lang.String");
    public static final JavaType DOUBLE = new JavaType("Double", "double", "java.lang.Double");
    public static final JavaType DATE = new JavaType("Date", "Date", "java.util.Date");
    public static final JavaType BOOLEAN = new JavaType("Boolean", "boolean", "java.lang.Boolean");
    public static final JavaType OBJECT = new JavaType("Object", "Object", "java.lang.Object");
}
