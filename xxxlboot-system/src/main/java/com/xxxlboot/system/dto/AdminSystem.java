package com.xxxlboot.system.dto;


import com.baomidou.mybatisplus.annotation.TableName;
import com.xxxlboot.common.base.BaseDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Explain:     [实体类]
 * Date:        [2018-12-14 02:14:09]
 * Coder:       [江雪立]
 * Version:     [1.0]
 */
 @SuppressWarnings("serial")

 @TableName("admin_system")
 @Getter
 @Setter
 @ToString
 @EqualsAndHashCode
public class  AdminSystem extends BaseDTO<AdminSystem> {

     private String name;

    /**系统路径*/
    private String path;


}
