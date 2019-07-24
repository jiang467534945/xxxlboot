package com.xxxlboot.common.base;

import java.io.Serializable;

/**
 * 用户文件上传的返回类
 */
public class FileResult extends Result implements Serializable  {
    private static final long serialVersionUID = 1430633339880116031L;

    /**
     * 参数表述：回显图片路径
     */
    private String url;
    /**
     * 参数表述：回显图片名称
     */
    private String label;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public FileResult(String label, String url) {
        this.url = url;
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

