package com.xxxlboot.common.gencode.config;

/**
 * Explain:     [全局配置]
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 */
public class GlobalConfig {

    /**文件生成完毕后是否打开输出目录*/
    private boolean openFileManager = true;

    /**模板语言起始标识符*/
    private String templateStatementStart = "@";
    /**模板语言结束标识符*/
    private String templateStatementEnd = null;//默认回车换行
    /**模板语言起始占位符*/
    private String templatePlaceholderStart = "${";
    /**模板语言结束占位符*/
    private String templatePlaceholderEnd = "}";

    public boolean isOpenFileManager() {
        return openFileManager;
    }

    public void setOpenFileManager(boolean openFileManager) {
        this.openFileManager = openFileManager;
    }

    public String getTemplateStatementStart() {
        return templateStatementStart;
    }

    public void setTemplateStatementStart(String templateStatementStart) {
        this.templateStatementStart = templateStatementStart;
    }

    public String getTemplateStatementEnd() {
        return templateStatementEnd;
    }

    public void setTemplateStatementEnd(String templateStatementEnd) {
        this.templateStatementEnd = templateStatementEnd;
    }

    public String getTemplatePlaceholderStart() {
        return templatePlaceholderStart;
    }

    public void setTemplatePlaceholderStart(String templatePlaceholderStart) {
        this.templatePlaceholderStart = templatePlaceholderStart;
    }

    public String getTemplatePlaceholderEnd() {
        return templatePlaceholderEnd;
    }

    public void setTemplatePlaceholderEnd(String templatePlaceholderEnd) {
        this.templatePlaceholderEnd = templatePlaceholderEnd;
    }
}
