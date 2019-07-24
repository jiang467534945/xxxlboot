package com.xxxlboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxlboot.common.vo.UserVO;
import com.xxxlboot.system.dto.User;
import com.xxxlboot.system.dto.UserInfo;
import com.xxxlboot.common.base.BaseService;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @auther: Easy
 * @Date: 18-9-10 21:53
 * @Description:
 */
public interface  IUserService extends BaseService<User> {
    public UserInfo getUserInfo(UserVO userVO);
    public IPage<User> selectUserPage(Page<User> page);
    public boolean installUser(User user, UserVO userVO);
    public boolean updateUser(User user, UserVO userVO);
    UserVO findUserByUsername(@PathVariable("username") String username);

}
