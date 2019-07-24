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

package com.xxxlboot.common.resolver;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.xxxlboot.common.util.UserUtils;
import com.xxxlboot.common.vo.AdminRole;
import com.xxxlboot.common.vo.UserVO;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author easy
 * @date 2018/09/28
 * Token转化UserVo
 */
@Configuration
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * 1. 入参筛选
     *
     * @param methodParameter 参数集合
     * @return 格式化后的参数
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {

        return methodParameter.getParameterType().equals(UserVO.class);
    }

    /**
     * @param methodParameter       入参集合
     * @param modelAndViewContainer dto 和 view
     * @param nativeWebRequest      web相关
     * @param webDataBinderFactory  入参解析
     * @return 包装对象
     * @throws Exception exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String roles = CollectionUtil.join(authentication.getAuthorities(),",");
        if (StrUtil.isBlank(username) || StrUtil.isBlank(roles)) {
            return null;
        } else {
        }
        String id = UserUtils.getUserId(request);
        UserVO userVO = new UserVO();
        userVO.setUserName(username);
        userVO.setId(id);
        System.out.println("检测到用户ID"+id);
        List<AdminRole> sysRoleList = new ArrayList<>();
        Arrays.stream(roles.split(",")).forEach(role -> {
            AdminRole sysRole = new AdminRole();
            sysRole.setRoleName(role);
            sysRoleList.add(sysRole);
        });
        userVO.setRoleList(sysRoleList);
        return userVO;
    }
}
