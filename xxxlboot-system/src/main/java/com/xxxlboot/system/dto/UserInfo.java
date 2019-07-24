package com.xxxlboot.system.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @auther: Easy
 * @Date: 18-9-25 21:00
 * @Description:
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserInfo  implements Serializable {
    private User user;
    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 角色集合
     */
    private String[] roles;


}
