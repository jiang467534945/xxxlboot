package com.xxxlboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxlboot.system.dto.User;
import com.xxxlboot.system.dto.UserInfo;
import com.xxxlboot.system.dto.UserRole;
import com.xxxlboot.system.mapper.UserMapper;
import com.xxxlboot.system.service.IMenuService;
import com.xxxlboot.system.service.IRoleService;
import com.xxxlboot.system.service.IUserRoleService;
import com.xxxlboot.system.service.IUserService;
import com.xxxlboot.common.base.BaseServiceImpl;
import com.xxxlboot.common.constats.SecurityConstants;
import com.xxxlboot.common.vo.AdminRole;
import com.xxxlboot.common.vo.MenuVO;
import com.xxxlboot.common.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @auther: Easy
 * @Date: 18-9-10 21:56
 * @Description:
 */
@Service("iUserService")
public class UserServiceImpl extends BaseServiceImpl<UserMapper,User> implements IUserService {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleService roleService;
    @Override
    //cacheManager = "cacheManager"可以不指定
    public UserInfo getUserInfo(UserVO userVO) {
        User usertion = new User();
        usertion.setUserName(userVO.getUserName());
        User user =  this.getOne(new QueryWrapper<>(usertion));
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        List<AdminRole> roleList = userVO.getRoleList();
        List<String> roleNames = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(roleList)) {
            for (AdminRole sysRole : roleList) {
                if (!StrUtil.equals(SecurityConstants.BASE_ROLE, sysRole.getRoleName())) {
                    roleNames.add(sysRole.getRoleName());
                }
            }
        }
        String[] roles = roleNames.toArray(new String[roleNames.size()]);
        userInfo.setRoles(roles);
        Set<MenuVO> menuVoSet = new HashSet<>();
        for (String role : roles) {
            List<MenuVO> menuVos = menuService.findMenuByRoleName(role,null);
            menuVoSet.addAll(menuVos);
        }
        Set<String> permissions = new HashSet<>();
        for (MenuVO menuVo : menuVoSet) {
            if (StringUtils.isNotEmpty(menuVo.getPermission())) {
                String permission = menuVo.getPermission();
                permissions.add(permission);
            }
        }
        userInfo.setPermissions(permissions.toArray(new String[permissions.size()]));
        return userInfo;
    }

    @Override
    public IPage<User> selectUserPage(Page<User> page) {
        IPage<User> iPage = this.baseMapper.selectPageVo(page);
        for(User user :iPage.getRecords()){
            QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",user.getId());
            System.out.println(user.getId());
            UserRole userRoles = userRoleService.getOne(queryWrapper);

            user.setRole(userRoles.getRoleId());
        }
        return iPage;
    }
    @Override
    public boolean installUser(User user,UserVO userVO) {
//        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        this.save(user,userVO);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(user.getRole());
        userRole.setCreateDate(new Date());
        userRoleService.save(userRole,userVO);
        return true;
    }
    @Override
    public boolean updateUser(User user,UserVO userVO) {
        user.setUpdateDate(new Date());
        this.updateById(user,userVO);
        UserRole contion = new UserRole();
        contion.setUserId(user.getId());
        UserRole userRole = userRoleService.getOne(new QueryWrapper<>(contion));
        userRole.setRoleId(user.getRole());
        userRole.setUpdateDate(new Date());
        userRoleService.saveOrUpdate(userRole,userVO);
        return true;
    }

    @Override
    public UserVO findUserByUsername(String username) {
        return this.baseMapper.selectUserVoByUsername(username);    }
}
