package com.xxxlboot.common.base;

/**
 * @auther: Easy
 * @Date: 18-9-6 01:41
 * @Description:基础枚举接口
 */
public interface BaseEnum<K, V> {
    /**
     * 功能描述:
     *
     * @param: null
     * @return: 编码
     * @auther: easy
     * @date: 18-9-6 上午1:42
     */
    K code();

    /**
     * 功能描述:
     *
     * @param: null
     * @return: 描述
     * @auther: easy
     * @date: 18-9-6 上午1:42
     */

    V desc();
}
