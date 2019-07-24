package com.xxxlboot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxlboot.system.dto.Menu;
import com.xxxlboot.system.dto.RoleMenu;
import com.xxxlboot.system.mapper.MenuMapper;
import com.xxxlboot.system.service.IMenuService;
import com.xxxlboot.system.service.IRoleMenuService;
import com.xxxlboot.common.base.BaseServiceImpl;
import com.xxxlboot.common.vo.MenuVO;
import com.xxxlboot.common.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther: Easy
 * @Date: 18-9-25 21:46
 * @Description:
 */
//@CacheConfig(cacheNames = "menu")
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    MenuMapper sysMenuMapper;

    @Autowired
    IRoleMenuService roleMenuService;

    @Override
    public List<MenuVO> findMenuByRoleName(String role, String systemType) {
        return sysMenuMapper.findMenuByRoleName(role, systemType);
    }

    @Override
    public boolean insertMenu(Menu menu, UserVO userVO) {
        this.save(menu,userVO);
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setMenuId(menu.getId());
        roleMenu.setRoleId("1");
        roleMenuService.save(roleMenu,userVO);
        return true;
    }

    @Override
    public boolean removeByIdAndRoleMenu(String id) {
        boolean success;
        Menu menuContion = new Menu();
        menuContion.setParentId(id);
        List<Menu> remove = sysMenuMapper.selectList(new QueryWrapper<>(menuContion));
        sysMenuMapper.deleteById(id);
        RoleMenu contion = new RoleMenu();
        contion.setMenuId(id);
        roleMenuService.remove(new QueryWrapper<>(contion));
        if (remove.size() >0) {
            remove.forEach((menu -> {
                        sysMenuMapper.deleteById(menu.getId());
                        RoleMenu roleMenucontion = new RoleMenu();
                roleMenucontion.setMenuId(menu.getId());
                        roleMenuService.remove(new QueryWrapper<>(roleMenucontion));
                    })
            );
        }
        return true;
    }
}