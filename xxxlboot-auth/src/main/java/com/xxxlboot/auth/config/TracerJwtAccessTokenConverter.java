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

package com.xxxlboot.auth.config;

import com.xxxlboot.common.vo.UserVO;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

/**
 * @author easy
 * @date 2018/10/10
 * token 声明版权
 */
public class TracerJwtAccessTokenConverter extends JwtAccessTokenConverter {
    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String userName = authentication.getUserAuthentication().getName();
        // 与登录时候放进去的UserDetail实现类一直查看link{SecurityConfiguration}
        UserVO user = (UserVO) authentication.getUserAuthentication().getPrincipal();
        Map<String, Object> representation = (Map<String, Object>) super.convertAccessToken(token, authentication);
        representation.put("license", "made by tracer");
        representation.put("userName", userName);
        representation.put("userId", user.getId());
        return representation;
    }

    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        return super.extractAccessToken(value, map);
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        return super.extractAuthentication(map);
    }
}
