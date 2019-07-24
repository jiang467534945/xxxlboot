package com.xxxlboot.system.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xxxlboot.common.base.BaseDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author : Easy
 * @Date: 18-9-25 21:29
 * @Description:
 */
@TableName("admin_dept_relation")
@Getter
@Setter
@ToString
public class DeptRelation extends BaseDTO<DeptRelation> {

    private static final long serialVersionUID = 1L;

    /**
     * 祖先节点
     */
    private String ancestor;
    /**
     * 后代节点
     */
    private String descendant;


    @Override
    protected Serializable pkVal() {
        return this.ancestor;
    }

}