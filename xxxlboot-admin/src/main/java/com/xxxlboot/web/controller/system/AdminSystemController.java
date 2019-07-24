package com.xxxlboot.web.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxlboot.common.base.BaseController;
import com.xxxlboot.common.base.BaseEnums;
import com.xxxlboot.common.base.Result;
import com.xxxlboot.common.util.Results;
import com.xxxlboot.common.vo.UserVO;
import com.xxxlboot.system.dto.AdminSystem;
import com.xxxlboot.system.service.IAdminSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 类描述:     [控制器]
 * 创建人:     [江雪立]
 * 创建时间:   [2018-12-14 02:14:09]
 * 版本:       [v1.0]
 */
@RestController
@RequestMapping("/adminSystem")
public class AdminSystemController extends BaseController {
    @Autowired
    private IAdminSystemService adminSystemService;
    private Logger logger = LoggerFactory.getLogger(AdminSystemController.class);
    /**
     * 分页 PAGE
     * @param current 当前页数
     * @return 分页数据
     */
    @GetMapping(value="/pageList")
    public Result pageList(Integer current){
        Page page = new Page();
        page.setCurrent(current);
        page.setSize(20);
        IPage<AdminSystem> adminSystemIPage = adminSystemService.page(page,null);
        return Results.successWithData(adminSystemIPage , BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
    }
    /**
     * 全部list
     * @return List实体集合
     */
    @GetMapping(value="/allList")
    public Result allList(){
        List<AdminSystem> adminSystemList =  adminSystemService.list(new QueryWrapper<>());
        return Results.successWithData(adminSystemList, BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
    }
    /**
     * 通过ID查询
     * @param id ID
     * @return Dept
     */
    @GetMapping("/{id}")
    public Result get(@PathVariable String id) {
        AdminSystem adminSystem = new AdminSystem ();
        adminSystem=adminSystemService.getById(id);
        return Results.successWithData(adminSystem, BaseEnums.SUCCESS.desc());
    }

    /**
     * 根据ID删除
     * @param id 编号
     * @return success/false
     */

    @DeleteMapping("/{id}")
    public Result del(@PathVariable String  id) {
        boolean  boo = adminSystemService.removeById(id);
        return Results.successWithData(boo, BaseEnums.SUCCESS.desc());
    }

    /**
     * 添加{data.describe}
     *
     * @param adminSystem {data.describe}
     * @return success/false
     */

    @PostMapping("/add")
    public Result add(@RequestBody AdminSystem  adminSystem ) {
        boolean  boo = adminSystemService.save(adminSystem);
        return Results.successWithData(boo, BaseEnums.SUCCESS.desc());
    }
    /**
     * 更新
     * @param adminSystem {data.describe} UserVO user操作用户
     * @return success/false
     */
    @PutMapping("/edit")
    public Result edit(@RequestBody AdminSystem   adminSystem , UserVO userVO){
        adminSystem .setUpdateDate(new Date());
        adminSystem .setUpdateBy(userVO.getId());
        adminSystem .setUpdater(userVO.getUserName());
        boolean boo = adminSystemService.updateById( adminSystem);
        return  Results.successWithData(boo,BaseEnums.SUCCESS.desc());
    }
}
