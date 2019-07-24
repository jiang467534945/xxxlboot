package com.xxxlboot.common.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @auther: Easy
 * @date: 19-6-12 10:42
 * @description:
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GetParam<T> {
    public Integer current;
    public Page page;
    public T searchForm;


}
