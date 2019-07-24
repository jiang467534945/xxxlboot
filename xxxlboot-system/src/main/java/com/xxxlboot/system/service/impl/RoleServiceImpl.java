package com.xxxlboot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxlboot.system.dto.Role;
import com.xxxlboot.system.dto.UserRole;
import com.xxxlboot.system.mapper.RoleMapper;
import com.xxxlboot.system.service.IRoleService;
import com.xxxlboot.system.service.IUserRoleService;
import com.xxxlboot.common.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther: Easy
 * @Date: 18-10-16 23:46
 * @Description:
 */

@CacheConfig(cacheNames = "role")
@Service("iRoleService")
public class RoleServiceImpl  extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    IUserRoleService userRoleService;
    @Override
    public IPage<Role> selectPage(Page page) {
        IPage<Role> iPage = this.baseMapper.selectRolePage(page);
        return iPage;
    }

    @Override
    public boolean delRoleById(String id) {
    QueryWrapper contion = new QueryWrapper();
    contion.eq("role_id",id);
        List<UserRole> roles = userRoleService.list(contion);
        if(null== roles || roles.size()==0){
            return this.removeById(id);
        }
        return false;
    }
}
