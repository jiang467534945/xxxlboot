package com.xxxlboot.web.controller.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxlboot.system.dto.User;
import com.xxxlboot.system.dto.UserInfo;
import com.xxxlboot.system.service.IUserService;
import com.xxxlboot.common.base.BaseController;
import com.xxxlboot.common.base.BaseEnums;
import com.xxxlboot.common.base.Result;
import com.xxxlboot.common.util.Results;
import com.xxxlboot.common.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

/**
 * @auther: Easy
 * @Date: 18-9-7 22:31
 * @Description:
 */
@RestController
@RequestMapping("/user")

public class UserController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ResponseBody
    public Result userInfo(UserVO userVO){
        logger.info("get by id from db");
        QueryWrapper<User> ew = new QueryWrapper<User>();
        ew.eq("user_name",userVO.getUserName());
        UserInfo user = iUserService.getUserInfo(userVO);

        return Results.successWithData(user, BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
    }

    @GetMapping(value="/allUser")
    public Result allUser(Integer current,Integer size,UserVO userVO){
        System.out.println(userVO.getId());
        Page page = new Page();
        page.setCurrent(current);
        page.setSize(size);
        IPage<UserVO> userVOIPage = iUserService.selectUserPage(page);
        return Results.successWithData(userVOIPage, BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
    }
    @PostMapping(value="/add")
    public Result add(@RequestBody User user, UserVO userVO){
        iUserService.installUser(user,userVO);

        return  Results.success();
    }
    @PostMapping(value="/edit")
    public Result edit(@RequestBody User user, UserVO userVO){
        iUserService.updateUser(user,userVO);

        return  Results.success();
    }
}