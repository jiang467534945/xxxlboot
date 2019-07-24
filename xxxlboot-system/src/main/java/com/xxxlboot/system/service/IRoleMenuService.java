package com.xxxlboot.system.service;

import com.xxxlboot.common.vo.UserVO;
import com.xxxlboot.system.dto.RoleMenu;
import com.xxxlboot.common.base.BaseService;

import com.xxxlboot.system.dto.User;

/**
 * @auther: Easy
 * @date: 18-11-1 00:23
 * @description:
 */
public interface IRoleMenuService extends BaseService<RoleMenu> {
    /**
     * 更新角色菜单
     *
     *
     * @param role
     * @param roleId  角色
     * @param menuIds 菜单列表
     * @return
     */
    Boolean insertRoleMenus(String role, String roleId, String menuIds, UserVO userVO);
}
