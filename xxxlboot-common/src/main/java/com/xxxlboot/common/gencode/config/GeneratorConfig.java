package com.xxxlboot.common.gencode.config;

import cn.hutool.core.util.StrUtil;


/**
 * Explain:     [生成代码配置信息]
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 */
public class GeneratorConfig {

    public static final String DEFAULT_TEMPLATE_PATH= "/generator/defaultTemplate/SSM";//默认模板路径

    /**文件输出路径*/
    private String outputPath;
    /**模板根，若为null则使用Jar包中的默认模板生成*/
    private String templateRoot;
    /**模板id，相对于模板根的子文件夹*/
    private String templateId;
    /**模板文件后缀*/
    private String templateFileSuffix;

    private String vuePath;
    /**作者*/
    private String author;
    /**说明*/
    private String describe;
    /**类名称*/
    private String className;
    /**包名*/
    private String packageName;
    /**模块名*/
    private String functionName;

    private String projectName;
    /**模块名*/
    private String model;
    /**时间*/
    private String date;

    public void verifyConfig() {
        if (StrUtil.isEmpty(outputPath)) {
            throw new RuntimeException("[Exception]GeneratorConfig.outputPath不能为空");
        }
        if (StrUtil.isEmpty(templateId)) {
            templateId = "";
        }
        if (templateFileSuffix == null) {
            templateFileSuffix = ".tl";
        }
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getTemplateRoot() {
        return templateRoot;
    }

    public void setTemplateRoot(String templateRoot) {
        this.templateRoot = templateRoot;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateFileSuffix() {
        return templateFileSuffix;
    }

    public void setTemplateFileSuffix(String templateFileSuffix) {
        this.templateFileSuffix = templateFileSuffix;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getVuePath() {
        return vuePath;
    }

    public void setVuePath(String vuePath) {
        this.vuePath = vuePath;
    }
}
