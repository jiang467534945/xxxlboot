/*
 *    Copyright (c) 2018-2025, easy All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the tracer_4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: easy (wangiegie@gmail.com)
 */

package com.xxxlboot.system.dto;



import com.baomidou.mybatisplus.annotation.TableName;
import com.xxxlboot.common.base.BaseDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 部门管理
 * </p>
 *
 * @author easy
 * @since 2018-01-22
 */
@TableName("admin_dept")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Dept extends BaseDTO<Dept> {

    private static final long serialVersionUID = 1L;

    private String name;
    /**
     * 排序
     */
    private Integer orderNum;


    private String parentId;


}
