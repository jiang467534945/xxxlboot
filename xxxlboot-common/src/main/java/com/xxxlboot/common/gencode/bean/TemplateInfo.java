package com.xxxlboot.common.gencode.bean;


import org.beetl.core.Template;

/**
 * @author Easy
 * @version 1.0
 * @Email xiyousuiyuan@163.com
 * @Date 2018/09/28
 */
public class TemplateInfo {
    private Template template;
    private String templatePath;
    private String outputPath;

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    @Override
    public String toString() {
        return "TemplateInfo{" +
                "template=" + template +
                ", templatePath='" + templatePath + '\'' +
                ", outputPath='" + outputPath + '\'' +
                '}';
    }
}
