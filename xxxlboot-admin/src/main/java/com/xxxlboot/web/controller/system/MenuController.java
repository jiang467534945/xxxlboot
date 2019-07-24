package com.xxxlboot.web.controller.system;



import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxlboot.util.TreeUtil;
import com.xxxlboot.common.base.BaseController;
import com.xxxlboot.common.base.BaseEnums;
import com.xxxlboot.common.base.Result;
import com.xxxlboot.common.constats.CommonConstant;
import com.xxxlboot.common.util.Results;
import com.xxxlboot.common.vo.MenuVO;
import com.xxxlboot.common.vo.UserVO;
import com.xxxlboot.system.dto.Menu;
import com.xxxlboot.system.dto.MenuTree;
import com.xxxlboot.system.service.IMenuService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @auther: Easy
 * @Date: 18-9-25 23:52
 * @Description:
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/menu")

public class MenuController extends BaseController {
    @Autowired
    IMenuService menuService;
    private Logger logger = LoggerFactory.getLogger(MenuController.class);
    /**
     * 返回当前用户的树形菜单集合
     *
     * @return 当前用户的树形菜单
     */
    @ApiOperation(value="获取当前用户的树形菜单集合",notes = "前端进入首页执行，根据用户角色查询的属性菜单集合")
    @GetMapping(value = "/userMenu")
    public List<MenuTree> userMenu(String type) {
        // 获取符合条件得菜单
        Set<MenuVO> all = new HashSet<>();
        getRole().forEach(roleName -> all.addAll(menuService.findMenuByRoleName(roleName,type)));
        List<MenuTree> menuTreeList = new ArrayList<MenuTree>();
        all.forEach(menuVo -> {
            if (CommonConstant.MENU.equals(menuVo.getType())||"2".equals(menuVo.getType())) {
                menuTreeList.add(new MenuTree(menuVo));
            }
        });
        CollUtil.sort(menuTreeList, Comparator.comparingInt(MenuTree::getSort));
        return TreeUtil.bulid(menuTreeList, "-1");
    }
    @ApiOperation(value="获取所有菜单",notes ="查询数据库菜单集合")
    @GetMapping(value="menuTree")
    public Result menuTree(){
        List<Menu> list = new ArrayList<>();
        QueryWrapper queryWrapper  = new QueryWrapper();
        list = menuService.list(queryWrapper);
        List<MenuTree> menuTreeList = new ArrayList<>();
        list.forEach(menu -> {
            menuTreeList.add(new MenuTree(menu));
        });
         return Results.successWithData(TreeUtil.bulid(menuTreeList, "-1"), BaseEnums.SUCCESS.desc());
    }
    @ApiOperation(value="获取符合自身权限的菜单",notes ="查询数据库菜单集合")
    @GetMapping(value="permessionTree")
    public Result  permessionTree(){
        // 获取符合条件得菜单
        Set<MenuVO> all = new HashSet<>();
        getRole().forEach(roleName -> all.addAll(menuService.findMenuByRoleName(roleName,null)));
        List<MenuTree> menuTreeList = new ArrayList<>();
        all.forEach(menu -> {
            menuTreeList.add(new MenuTree(menu));
        });
        CollUtil.sort(menuTreeList, Comparator.comparingInt(MenuTree::getSort));
        return Results.successWithData(TreeUtil.bulid(menuTreeList, "-1"), BaseEnums.SUCCESS.desc());
    }

    /**
     * @param id 菜单编号
     * @return 菜单实体数据
     */
    @ApiOperation(value="根据ID查询菜单详情",notes = "在数据库中查询某个菜单数据")
    @ApiImplicitParam(name="id",value="菜单ID",required = true,dataType = "String",paramType ="path" )
    @GetMapping("/{id}")
    public Result getObj(@PathVariable String id){
        Menu menu = menuService.getById(id);
        return Results.successWithData(menu,BaseEnums.SUCCESS.desc());
    }
    /**
     * 添加
     *
     * @param menu 菜单实体
     * @return success/false
     */
    @ApiOperation(value="新增菜单数据",notes = "在数据库中增加一个菜单")
    @ApiImplicitParam(name="menu",value="菜单实体",required = true,dataType = "Menu",paramType ="path" )
    @PostMapping("/add")
    public Result add(@RequestBody Menu menu, UserVO userVO) {
        menu.setSystemType("0");
        boolean  boo = menuService.insertMenu(menu,userVO);
        return Results.successWithData(boo, BaseEnums.SUCCESS.desc());
    }
    @ApiOperation(value="修改菜单数据",notes = "在数据库中修改一个菜单")
    @ApiImplicitParam(name="menu",value="菜单实体",required = true,dataType = "Menu",paramType ="path" )
    @PutMapping("/edit")
    public Result edit (@RequestBody Menu menu, UserVO userVO){
        boolean boo = menuService.updateById(menu,userVO);
        return  Results.success();
    }
    /**
     * 返回角色的菜单集合
     * @param roleName 角色名称
     * @return 属性集合
     */
    @ApiOperation(value="根据角色code查询下级菜单",notes = "在数据库中查询符合规则的菜单")
    @ApiImplicitParam(name="roleName",value="角色code",required = true,dataType = "String",paramType ="path" )
    @GetMapping("/roleTree/{roleName}")
    public Result roleTree(@PathVariable String roleName) {
        List<MenuVO> menus = menuService.findMenuByRoleName(roleName,null);
        List<String> menuList = new ArrayList<>();
        for (MenuVO menuVo : menus) {
            menuList.add(menuVo.getId());
        }
        return  Results.successWithData(menuList,BaseEnums.SUCCESS.desc(),BaseEnums.SUCCESS.code());
    }
    /**
     * @param id 菜单编号
     * @return 是否删除成功
     */
    @ApiOperation(value="根据ID删除菜单详情",notes = "在数据库中查询某个菜单数据并删除，并且将其所包含的权限移除")
    @ApiImplicitParam(name="id",value="菜单ID",required = true,dataType = "String",paramType ="path" )
    @DeleteMapping("/{id}")
    public Result delMenu(@PathVariable String id){
        boolean status = menuService.removeByIdAndRoleMenu(id);
        return Results.successWithData(status,BaseEnums.SUCCESS.desc());
    }
}
