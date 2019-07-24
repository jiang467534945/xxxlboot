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
 * @Date: 18-9-25 21:04
 * @Description:
 */
@TableName("admin_role_dept")
@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RoleDept extends BaseDTO<RoleDept> {
    /**
     * 角色ID
     */
    @TableField("role_id")
    private String roleId;
    /**
     * 部门ID
     */
    @TableField("dept_id")
    private String deptId;
}
