package com.xxxlboot.common.gencode.util;

/**
 * Explain:     [通过重用对象节约StringBuilder内部的char[],提高程序效率。此StringBuilder是线程独立的,初始容量为256]
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 */
public class ReuseStringBuilder {

    private static final ThreadLocal<StringBuilder> threadLocalStringBuilder = new ThreadLocal<StringBuilder>(){
        @Override
        protected StringBuilder initialValue() {
            return new StringBuilder(256);
        }
    };

    /**
     * 获取可重用的StringBuilder
     * 此StringBuilder是线程独立的
     * 特别注意:在同一线程中获取到的StringBuilder是相同的,所以如果需要使用多个StringBuilder对象,请使用new StringBuilder
     * @return
     */
    public static StringBuilder getStringBuilder() {
        StringBuilder sb = threadLocalStringBuilder.get();
//
        sb.setLength(0);
        return sb;
    }
}
