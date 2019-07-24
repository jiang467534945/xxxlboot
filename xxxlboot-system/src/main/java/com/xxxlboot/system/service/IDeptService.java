package com.xxxlboot.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxlboot.common.vo.UserVO;
import com.xxxlboot.system.dto.Dept;
import com.xxxlboot.system.dto.User;
import com.xxxlboot.system.vo.DeptTree;
import com.xxxlboot.common.base.BaseService;


import java.util.List;

/**
 * @auther: Easy
 * @Date: 18-10-18 23:12
 * @Description:
 */
public interface IDeptService extends BaseService<Dept> {
    /**
     * 查询部门树菜单
     * @param sysDeptEntityWrapper
     * @return 树
     */
    List<DeptTree> selectListTree(QueryWrapper<Dept> sysDeptEntityWrapper, String deptId);
    /**
     * 新增部门及其关联关系
     * @param dept
     * @return boolean状态
     */
    Boolean insertDept(Dept dept, UserVO userVO);
    /**
     * 删除部门及其关联关系
     * @param id
     * @return boolean状态
     */
    Boolean delDept(String id);
}
