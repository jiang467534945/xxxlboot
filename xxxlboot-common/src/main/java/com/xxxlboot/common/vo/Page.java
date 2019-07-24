package com.xxxlboot.common.vo;  /**
 * @title: Page
 * @projectName tracer
 * @description: TODO
 * @author xy
 * @date 19-6-17下午7:09
 */

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *@title Page
 *@author [xy]
 *@date 2019
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Page {

    private int total;
    private int  currentPage;


}
