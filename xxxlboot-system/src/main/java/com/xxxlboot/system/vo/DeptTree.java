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

package com.xxxlboot.system.vo;
import com.xxxlboot.common.vo.TreeNode;


/**
 * @author Easy
 * @date 2018/10/10
 * 部门树
 */
public class DeptTree extends TreeNode {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
