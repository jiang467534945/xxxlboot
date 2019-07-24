package com.xxxlboot.system.mapper;

import com.xxxlboot.common.vo.MenuVO;
import com.xxxlboot.system.dto.Menu;
import com.xxxlboot.common.base.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther: Easy
 * @Date: 18-9-25 21:47
 * @Description:
 */
public interface MenuMapper  extends Mapper<Menu> {
    /**
     * 通过角色名查询菜单
     *
     * @param role 角色名称
     * @return 菜单列表
     */
    List<MenuVO> findMenuByRoleName(@Param("role") String role, @Param("systemType") String systemType);

}
