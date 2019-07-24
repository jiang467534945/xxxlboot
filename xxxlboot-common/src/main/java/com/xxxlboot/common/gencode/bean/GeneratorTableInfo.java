package com.xxxlboot.common.gencode.bean;



import java.util.Map;

/**
 * Explain:     [生成文件时的表信息]
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 */
public class GeneratorTableInfo extends TableInfo {

    private Map<String, String> tableConfig;
    /**表注释简写(去除配置项)*/
    private String simpleTableComment;
    /**使用乐观锁*/
    private boolean useLock = false;
    /**用于实现乐观锁的字段*/
    private String lockFiled;
    /**实现乐观锁的字段Java属性名*/
    private String lockFiledProperty;
    /**实现乐观锁的字段Java属性名(首字母大写)*/
    private String upperLockFiledProperty;
    /**使用逻辑删除*/
    private boolean logicDel = false;
    /**实现逻辑删除的字段*/
    private String delFiled;
    /**实现逻辑删除的字段Java属性名*/
    private String delFiledProperty;
    /**实现逻辑删除的字段Java属性名(首字母大写)*/
    private String upperDelFiledProperty;

    public String getSimpleTableComment() {
        return simpleTableComment;
    }

    public void setSimpleTableComment(String simpleTableComment) {
        this.simpleTableComment = simpleTableComment;
    }

    public boolean isUseLock() {
        return useLock;
    }

    public void setUseLock(boolean useLock) {
        this.useLock = useLock;
    }

    public String getLockFiled() {
        return lockFiled;
    }

    public void setLockFiled(String lockFiled) {
        this.lockFiled = lockFiled;
    }

    public boolean isLogicDel() {
        return logicDel;
    }

    public void setLogicDel(boolean logicDel) {
        this.logicDel = logicDel;
    }

    public String getDelFiled() {
        return delFiled;
    }

    public void setDelFiled(String delFiled) {
        this.delFiled = delFiled;
    }

    public String getLockFiledProperty() {
        return lockFiledProperty;
    }

    public void setLockFiledProperty(String lockFiledProperty) {
        this.lockFiledProperty = lockFiledProperty;
    }

    public String getUpperLockFiledProperty() {
        return upperLockFiledProperty;
    }

    public void setUpperLockFiledProperty(String upperLockFiledProperty) {
        this.upperLockFiledProperty = upperLockFiledProperty;
    }

    public String getDelFiledProperty() {
        return delFiledProperty;
    }

    public void setDelFiledProperty(String delFiledProperty) {
        this.delFiledProperty = delFiledProperty;
    }

    public String getUpperDelFiledProperty() {
        return upperDelFiledProperty;
    }

    public void setUpperDelFiledProperty(String upperDelFiledProperty) {
        this.upperDelFiledProperty = upperDelFiledProperty;
    }

    public Map<String, String> getTableConfig() {
        return tableConfig;
    }

    public void setTableConfig(Map<String, String> tableConfig) {
        this.tableConfig = tableConfig;
    }

    @Override
    public String toString() {
        return "GeneratorTableInfo{" +
                "tableConfig=" + tableConfig +
                ", simpleTableComment='" + simpleTableComment + '\'' +
                ", useLock=" + useLock +
                ", lockFiled='" + lockFiled + '\'' +
                ", lockFiledProperty='" + lockFiledProperty + '\'' +
                ", upperLockFiledProperty='" + upperLockFiledProperty + '\'' +
                ", logicDel=" + logicDel +
                ", delFiled='" + delFiled + '\'' +
                ", delFiledProperty='" + delFiledProperty + '\'' +
                ", upperDelFiledProperty='" + upperDelFiledProperty + '\'' +
                '}';
    }
}
