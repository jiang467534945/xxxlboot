package com.xxxlboot.web.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxlboot.common.base.BaseController;
import com.xxxlboot.common.base.BaseEnums;
import com.xxxlboot.common.base.Result;
import com.xxxlboot.common.constats.CommonConstant;
import com.xxxlboot.common.util.Results;
import com.xxxlboot.common.vo.UserVO;
import com.xxxlboot.system.dto.Dept;
import com.xxxlboot.system.dto.User;
import com.xxxlboot.system.service.IDeptService;
import com.xxxlboot.system.service.IUserService;
import com.xxxlboot.system.vo.DeptTree;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @auther: Easy
 * @Date: 18-10-18 23:04
 * @Description:
 */

/**
 * @auther: Easy
 * @Date: 18-9-25 23:52
 * @Description:
 */
@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IUserService userService;
    @ApiOperation(value="获取部门分页数据",notes = "根据current控制分页")
    @ApiImplicitParam(name="current",value="页码",required = true,dataType = "Integer",paramType ="path" )
    @GetMapping(value="/pageList")
    public Result pageList(Integer current) throws Exception{
        Page page = new Page();
        page.setCurrent(current);
        page.setSize(20);
        IPage<Dept> userVOIPage = deptService.page(page,null);
        return Results.successWithData(userVOIPage, BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
    }
    @ApiOperation(value="获取所有部门")
    @GetMapping(value="/allList")
    public Result allList(){
        List<Dept> deptList =  deptService.list(new QueryWrapper<>());
        return Results.successWithData(deptList, BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
    }
    @ApiOperation(value="获取前端部门字典数据",notes = "根据所属部门进行查询")
    @GetMapping(value="/selDeptLIst")
    public  List<DeptTree> selDeptLIst(UserVO userVO){
        User contion = new User();
        contion.setUserName(userVO.getUserName());
        User user =  userService.getOne(new QueryWrapper<>(contion));
        List<DeptTree> treeList = deptService.selectListTree(new QueryWrapper<Dept>().eq("del_flag",CommonConstant.STATUS_NORMAL),user.getDeptId());
        logger.info("treeList查出有+："+treeList.size());
        return treeList;
    }
    @ApiOperation(value="获取部门树结构数据",notes = "根据所属部门进行查询")
    @GetMapping(value="/tree")
    public Result getTree(UserVO userVO) {
        User contion = new User();
        contion.setUserName(userVO.getUserName());
        User user =  userService.getOne(new QueryWrapper<>(contion));

        List<DeptTree> treeList = deptService.selectListTree(new QueryWrapper<Dept>().eq("del_flag",CommonConstant.STATUS_NORMAL),user.getDeptId());
        logger.info("treeList查出有+："+treeList.size());
        return Results.successWithData(treeList, BaseEnums.SUCCESS.desc());
    }
    @ApiOperation(value="获取所有部门树结构数据")
    @GetMapping(value="/treeData")
    public List<DeptTree> treeData() {
        List<DeptTree> treeList = deptService.selectListTree(new QueryWrapper<Dept>().eq("del_flag",CommonConstant.STATUS_NORMAL),"-1");
        logger.info("treeList查出有+："+treeList.size());
        return treeList;
    }
    /**
     * 通过ID查询
     *
     * @param id ID
     * @return Dept
     */
    @ApiOperation(value="根据ID查询部门信息",notes = "查询数据库中某个部门的信息")
    @ApiImplicitParam(name="id",value="部门ID",required = true,dataType = "String",paramType ="path" )
    @GetMapping("/{id}")
    public Result get(@PathVariable String id) {
        Dept dept = new Dept();
        dept=deptService.getById(id);
        return Results.successWithData(dept, BaseEnums.SUCCESS.desc());
    }
    /**
     * 添加
     *
     * @param sysDept 实体
     * @return success/false
     */
    @ApiOperation(value="新增部门",notes = "在数据库增加一个新部门")
    @ApiImplicitParam(name="sysDept",value="部门实体",required = true,dataType = "Dept",paramType ="path" )
    @PostMapping("/add")
    public Result add(@RequestBody Dept  sysDept,UserVO userVO) {

        boolean  boo = deptService.insertDept(sysDept,userVO);
        return Results.successWithData(boo, BaseEnums.SUCCESS.desc());
    }
    /**
     * 删除部门
     * 确定部门下无实际用户则可删除
     *
     * @param id 实体
     * @return success/false
     */
    @ApiOperation(value="根据ID删除部门信息",notes = "删除数据库中某个部门的信息")
    @ApiImplicitParam(name="id",value="部门ID",required = true,dataType = "String",paramType ="path" )
    @DeleteMapping("/{id}")
    public Result del(@PathVariable String  id) {
        boolean  boo = deptService.delDept(id);
        return Results.successWithData(boo, BaseEnums.SUCCESS.desc());
    }
    @ApiOperation(value="修改部门信息",notes = "删除数据库中某个部门的信息")
    @ApiImplicitParam(name="dept",value="部门实体信息",required = true,dataType = "Dept",paramType ="path" )
    @PutMapping("/edit")
    public Result edit(@RequestBody Dept dept , UserVO userVO){

        boolean boo = deptService.updateById(dept,userVO);
        return  Results.successWithData(boo,BaseEnums.SUCCESS.desc());
    }
}

