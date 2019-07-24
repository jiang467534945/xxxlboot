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
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private String id;
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
     * 一个路径
     */
    private String path;
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
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    private String systemType;
    /**
     * 0--正常 1--删除
     */
    private String delFlag;



    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * menuId 相同则相同
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MenuVO) {
            String targetMenuId = ((MenuVO) obj).getId();
            return id.equals(targetMenuId);
        }
        return super.equals(obj);
    }


}
