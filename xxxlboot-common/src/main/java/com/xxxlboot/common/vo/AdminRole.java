package com.xxxlboot.common.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther: Easy
 * @Date: 18-9-21 19:29
 * @Description:
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AdminRole implements Serializable {
    private static final long serialVersionUID = 1L;

    private String  id;
    private String roleName;
    private String roleCode;
    private String roleDesc;
    private Date createTime;
    private Date updateTime;
    private String delFlag;

}
