package com.xxxlboot.common.gencode.util;


import com.xxxlboot.common.util.StringUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象工具类<br>
 * date：2018/09/28
 *
 * @author Easy
 * @version 1.0
 */
public class ObjectUtil {

    /**
     * 缓存Method对象,避免重复反射获取,提高效率
     */
    private static final Map<Class, Map<String,Method>> classMethodCache = new ConcurrentHashMap<Class, Map<String,Method>>();

    /**
     * 已知属性名，遵循javaBean规范返回get方法名。如属性名是name,get方法是getName
     * @param attrName
     * @return
     */
    public static String getGetMethodName(String attrName) {
        if (StringUtil.isEmpty(attrName)) {
            return "";
        }
        StringBuilder mbuffer = new StringBuilder();
        if (attrName.length() > 1 && Character.isUpperCase(attrName.charAt(1))) {
            mbuffer.append("get").append(attrName);
        } else {
            mbuffer.append("get").append(WordUtil.initialUp(attrName));
        }
        return mbuffer.toString();
    }

    /**
     * 已知属性名，遵循javaBean规范返回set方法名。如属性名是name,set方法是setName
     * @param attrName
     * @return
     */
    public static String getSetMethodName(String attrName) {
        if (StringUtil.isEmpty(attrName)) {
            return "";
        }
        StringBuilder mbuffer = new StringBuilder();
        if (attrName.length() > 1 && Character.isUpperCase(attrName.charAt(1))) {
            mbuffer.append("set").append(attrName);
        } else {
            mbuffer.append("set").append(WordUtil.initialUp(attrName));
        }
        return mbuffer.toString();
    }

    /**
     * 已知属性名，遵循javaBean规范返回boolean的is方法名，如属性名是boy,is方法是isBoy，属性名是以is开头并且后面跟随着大写字母，例如isX则返回isX
     * @param attrName
     * @return
     */
    public static String getBooIsMethodName(String attrName) {
        StringBuilder mbuffer = new StringBuilder();
        if (attrName.startsWith("is") && attrName.length() > 2 && Character.isUpperCase(attrName.charAt(2))) {
            mbuffer.append(attrName);
        } else {
            mbuffer.append("is").append(WordUtil.initialUp(attrName));
        }
        return mbuffer.toString();
    }

    /**
     * 已知属性名，遵循javaBean规范返回boolean的set方法名，如属性名是boy,set方法是setBoy，属性名是以is开头并且后面跟随着大写字母，例如isX则返回setX
     * @param attrName
     * @return
     */
    public static String getBooSetMethodName(String attrName) {
        StringBuilder mbuffer = new StringBuilder();
        if (attrName.startsWith("is") && attrName.length() > 2 && Character.isUpperCase(attrName.charAt(2))) {
            mbuffer.append("set").append(attrName.substring(2));
        } else {
            mbuffer.append("set").append(WordUtil.initialUp(attrName));
        }
        return mbuffer.toString();
    }

    /**
     * 获取bean对象中指定属性的值
     * @param bean
     * @param propertyName
     * @return
     */
    public static Object getBeanProperty(Object bean, String propertyName) {
        if (bean == null) {
            return null;
        }
        Class c = bean.getClass();
        Map<String, Method> methodMap = classMethodCache.get(c);
        if (methodMap == null) {
            methodMap = new HashMap<String, Method>(3);
            classMethodCache.put(c, methodMap);
        }
        Method method = methodMap.get(propertyName);
        if (method == null) {
            String methodName = getGetMethodName(propertyName);
            method = getMethod(c, methodName, null);
            if (method == null) {
                methodName = getBooIsMethodName(propertyName);
                method = getMethod(c, methodName, null);
            }
            methodMap.put(propertyName, method);
        }
        if (method != null) {
            try {
                return method.invoke(bean, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取bean对象中指定属性的值,支持链式获取,例如user.child.name
     * @param bean
     * @param propertyName
     * @return
     */
    public static Object getChainBeanProperty(Object bean, String propertyName) {
        if (bean == null || propertyName == null) {
            return null;
        }
        String[] names = propertyName.split("\\.");
        for (int i = 0; i < names.length; i++) {
            propertyName = names[i];
            Class c = bean.getClass();
            Map<String, Method> methodMap = classMethodCache.get(c);
            if (methodMap == null) {
                methodMap = new HashMap<String, Method>(3);
                classMethodCache.put(c, methodMap);
            }
            Method method = methodMap.get(propertyName);
            if (method == null) {
                String methodName = getGetMethodName(propertyName);
                method = getMethod(c, methodName, null);
                if (method == null) {
                    methodName = getBooIsMethodName(propertyName);
                    method = getMethod(c, methodName, null);
                }
                methodMap.put(propertyName, method);
            }
            if (method != null) {
                try {
                    bean = method.invoke(bean, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return null;
            }
        }
        return bean;
    }

    /**
     * 获取对象的指定Method方法对象
     * @param c
     * @param methodName
     * @param paras
     * @return
     */
    public static Method getMethod(Class c, String methodName, Class... paras) {
        try {
            Method m = c.getMethod(methodName, paras);
            m.setAccessible(true);
            return m;
        } catch (Exception e) {
            return null;
        }
    }

}
