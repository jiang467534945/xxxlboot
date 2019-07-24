package com.xxxlboot.common.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @auther: Easy
 * @Date: 18-9-10 21:09
 * @Description:继承通用Mapper，并可以自定义自己的基础mapper
 */
public interface  Mapper<T> extends BaseMapper<T> {
    // 这里可以放一些公共的方法
}
