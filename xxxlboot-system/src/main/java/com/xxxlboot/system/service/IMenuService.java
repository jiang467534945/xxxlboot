package com.xxxlboot.system.service;

import com.xxxlboot.common.vo.MenuVO;
import com.xxxlboot.common.vo.UserVO;
import com.xxxlboot.system.dto.Menu;
import com.xxxlboot.common.base.BaseService;

import com.xxxlboot.system.dto.User;

import java.util.List;

/**
 * @auther: Easy
 * @Date: 18-9-25 21:46
 * @Description:
 */
public interface IMenuService extends BaseService<Menu> {
    List<MenuVO> findMenuByRoleName(String role, String systemType);
    boolean insertMenu(Menu menu, UserVO userVO);
    boolean removeByIdAndRoleMenu(String id);
}
