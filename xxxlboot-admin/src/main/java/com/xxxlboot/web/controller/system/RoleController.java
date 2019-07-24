package com.xxxlboot.web.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxlboot.system.dto.Role;
import com.xxxlboot.system.service.IRoleMenuService;
import com.xxxlboot.system.service.IRoleService;
import com.xxxlboot.common.base.BaseController;
import com.xxxlboot.common.base.BaseEnums;
import com.xxxlboot.common.base.Result;
import com.xxxlboot.common.util.Results;
import com.xxxlboot.common.vo.UserVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RoleController class
 * @author Easy
 * @date 18-10-27 00:42
 * @description:
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private IRoleMenuService roleMenuService;


    /**
     * @param current 分页页数
     * @return
     * @throws Exception
     */
    @ApiOperation(value="获取角色分页数据",notes = "获取角色分页数据")
    @RequestMapping(value="/pageList")
    private Result pageList(Integer current) throws Exception{
        Page<Role> page = new Page<>();
        page.setCurrent(current);
        IPage<Role> iPage =  iRoleService.selectPage(page);
        return Results.successWithData(iPage, BaseEnums.SUCCESS.code(),BaseEnums.SUCCESS.desc());
    }

    /**
     * 新增角色管理
     * @param row 角色数据
     * @return true/false
     */
    @ApiOperation(value="新增角色数据",notes = "新增角色数据")
    @ApiImplicitParam(name="row",value="角色实体",required = true,dataType = "Role",paramType ="path" )
    @PostMapping(value="/add")
    private Result add(@RequestBody Role row, UserVO userVO){
        boolean  boo = iRoleService.save(row,userVO);
        return Results.successWithData(boo, BaseEnums.SUCCESS.desc());
    }

    /**
     * @param id
     * @return 删除成功/失败
     */
    @ApiOperation(value="根据ID删除角色数据",notes = "删除角色数据")
    @ApiImplicitParam(name="id",value="角色ID",required = true,dataType = "String",paramType ="path" )
    @DeleteMapping("/{id}")
    private Result del(@PathVariable String id){
        boolean  boo = iRoleService.delRoleById(id);
        return Results.successWithData(boo, BaseEnums.SUCCESS.desc());
    }

    /**
     * @param role 操作的role对象，以及记录的user对象
     * @param userVO 操作人对象
     * @return 操作成功或者失败状态
     */
    @ApiOperation(value="修改角色",notes = "删除角色数据")
    @ApiImplicitParam(name="id",value="角色ID",required = true,dataType = "String",paramType ="path" )
    @PutMapping("/edit")
    public Result edit(@RequestBody Role role , UserVO userVO){

        boolean boo = iRoleService.updateById(role,userVO);
        return  Results.successWithData(boo,BaseEnums.SUCCESS.desc());
    }
    /**
     * 更新角色菜单
     *
     * @param roleId  角色ID
     * @param menuIds 菜单结合
     * @return success、false
     */
    @PutMapping("/roleMenuUpd")
    public Result roleMenuUpd(String roleId, @RequestParam(value = "menuIds", required = false) String menuIds, UserVO userVO) {
        Role adminRole = iRoleService.getById(roleId);
        return  Results.newResult(roleMenuService.insertRoleMenus(adminRole.getRoleCode(),roleId,menuIds,userVO));
    }
    /**
     * 通过ID查询
     *
     * @param id ID
     * @return Dept
     */
    @GetMapping("/getDeptRole/{key}")
    public  List<Role> getDeptRole(@PathVariable String key) {
        Role role= new Role();
        role.setDeptId(key);
        List<Role>roleList= iRoleService.list(new QueryWrapper<>(role));
        return roleList;
    }
}
