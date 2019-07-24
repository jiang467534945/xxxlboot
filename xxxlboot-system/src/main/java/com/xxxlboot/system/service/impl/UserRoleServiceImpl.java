package com.xxxlboot.system.service.impl;

import com.xxxlboot.system.dto.UserRole;
import com.xxxlboot.system.mapper.UserRoleMapper;
import com.xxxlboot.system.service.IUserRoleService;
import com.xxxlboot.common.base.BaseServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * @auther: Easy
 * @Date: 18-10-16 23:12
 * @Description:
 */
@CacheConfig(cacheNames = "userRole")
@Service("iUserRoleService")
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
}
