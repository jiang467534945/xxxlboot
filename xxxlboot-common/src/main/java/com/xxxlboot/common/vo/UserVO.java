package com.xxxlboot.common.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @auther: Easy
 * @Date: 18-9-13 17:30
 * @Description:
 */

/**
 * @author easy
 * @date 2018/09/28
 */
@TableName("admin_user")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserVO implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 主键ID
         */
        private String id;
        /**
         * 用户名
         */
        private String userName;
        /**
         * 用户名
         */
        private String nickName;
        /**
         * 密码
         */
        private String password;
        /**
         * 随机盐
         */
        private String salt;
        /**
         * 创建时间
         */
        private Date createTime;
        /**
         * 修改时间
         */
        private Date updateTime;
        /**
         * 0-正常，1-删除
         */
        private String delFlag;
        /**
         * 简介
         */
        private String phone;
        /**
         * 头像
         */
        private String avatar;

        /**
         * 部门ID
         */
        private String deptId;
        /**
         * 部门名称
         */
        private String deptName;

        /**
         * 角色列表
         */
        private List<AdminRole> roleList;


}


