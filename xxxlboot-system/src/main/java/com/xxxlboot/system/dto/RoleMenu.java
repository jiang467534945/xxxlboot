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

import java.io.Serializable;

/**
 * <p>
 * 角色菜单表
 * </p>
 *
 * @author easy
 * @since 2017-10-29
 */
@TableName("admin_role_menu")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RoleMenu extends BaseDTO<RoleMenu> {

    private static final long serialVersionUID = 1L;


	private String roleId;
    /**
     * 菜单ID
     */
	private String menuId;

}
