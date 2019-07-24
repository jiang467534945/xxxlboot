package com.xxxlboot.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xxxlboot.common.base.BaseDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @auther: Easy
 * @Date: 18-9-25 21:02
 * @Description:
 */
@TableName("admin_role")
@Getter
@Setter
@ToString
public class Role extends BaseDTO<Role> {
    private String roleName;
    private String roleCode;
    private String roleDesc;
    private String deptId;
    @TableField(exist = false)
    private String deptName;


}
