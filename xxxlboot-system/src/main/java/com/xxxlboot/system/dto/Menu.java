package com.xxxlboot.system.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xxxlboot.common.base.BaseDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @auther: Easy
 * @Date: 18-9-25 21:43
 * @Description:
 */
@TableName("admin_menu")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Menu extends BaseDTO<Menu> {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单权限标识
     */
    private String permission;
    /**
     * 请求链接
     */
    private String url;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 父菜单ID
     */
    private String parentId;
    /**
     * 图标
     */
    private String icon;
    /**
     * VUE页面
     */
    private String component;
    /**
     * 排序值
     */
    private Integer sort;
    /**
     * 菜单类型 （0菜单 1按钮）
     */
    private String type;

    /**
     * 前端URL
     */
    private String path;
    private String systemType;


}
