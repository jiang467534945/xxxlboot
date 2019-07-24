package com.xxxlboot.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xxxlboot.common.base.BaseDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @auther: Easy
 * @Date: 18-9-7 22:30
 * @Description:
 */
@TableName("admin_user")
@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User extends BaseDTO <User>{
    private static final long serialVersionUID = -7395431342743009038L;

    public String userName;
    public String password;
    public String nickName;
    public String tenantId;
    public String avatar;
    public String salt;
    public String deptId;
    public String phone;
    @TableField(exist = false)
    public String   deptName;

    @TableField(exist = false)
    public String  [] roleStr;
    @TableField(exist = false)
    public String      role;

}
