package com.xxxlboot.common.gencode.bean;

/**
 * Explain:     [数据表列信息]
 * Date:        [2018/09/28
 * Version:     [1.0]
 */
public class ColumnInfo {

    /**列名称*/
    private String column;

    /**列类型*/
    private String columnType;

    /**数据类型*/
    private String dataType;

    /**列长度*/
    private int columnLen;

    /**是否为主键*/
    private boolean isPrimaryKey = false;

    /**是否唯一*/
    private boolean isUnique = false;

    /**是否允许为空*/
    private boolean isNull = true;

    /**列注释*/
    private String columnComment;

    /**列默认值*/
    private String columnDefault;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public int getColumnLen() {
        return columnLen;
    }

    public void setColumnLen(int columnLen) {
        this.columnLen = columnLen;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean unique) {
        isUnique = unique;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColumnDefault() {
        return columnDefault;
    }

    public void setColumnDefault(String columnDefault) {
        this.columnDefault = columnDefault;
    }
}
