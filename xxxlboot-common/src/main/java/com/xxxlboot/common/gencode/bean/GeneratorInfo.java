package com.xxxlboot.common.gencode.bean;



import java.util.List;

/**
 * Explain:     [生成信息Bean]
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 */
public class GeneratorInfo {

    private List<GeneratorColumnInfo> columns;

    private GeneratorTableInfo tableInfo;

    ///////////配置信息///////////
    private String tableName;

    /**作者*/
    private String author;
    /**说明*/
    private String describe;
    /**类名称*/
    private String className;
    /**首字母小写类名*/
    private String lowerClassName;
    /**包名*/
    private String packageName;

    private String functionName;

    private String projectName;
    /**模块名*/
    private String model;
    /**时间*/
    private String date;

    /**序号*/
    private int sequence;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<GeneratorColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<GeneratorColumnInfo> columns) {
        this.columns = columns;
    }

    public GeneratorTableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(GeneratorTableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLowerClassName() {
        return lowerClassName;
    }

    public void setLowerClassName(String lowerClassName) {
        this.lowerClassName = lowerClassName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }



    @Override
    public String toString() {
        return "GeneratorInfo{" +
                "columns=" + columns +
                ", tableInfo=" + tableInfo +
                ", author='" + author + '\'' +
                ", describe='" + describe + '\'' +
                ", className='" + className + '\'' +
                ", lowerClassName='" + lowerClassName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", dto='" + model + '\'' +
                ", sequence=" + sequence +
                '}';
    }
}
