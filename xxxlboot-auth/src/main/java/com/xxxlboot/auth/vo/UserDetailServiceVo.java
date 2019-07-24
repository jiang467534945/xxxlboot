package com.xxxlboot.auth.vo;

import com.xxxlboot.common.vo.UserVO;
import com.xxxlboot.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @auther: Easy
 * @date: 19-7-11 09:55
 * @description:
 */
public class UserDetailServiceVo implements UserDetailsService {
    @Autowired
    private IUserService userService;

    @Override
    public UserDetailsVo loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO userVo = userService.findUserByUsername(username);
        return new UserDetailsVo(userVo);
    }
}
