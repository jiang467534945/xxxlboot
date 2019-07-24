package com.xxxlboot.common.gencode.constant;

/**
 * Explain:     []
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 */
public class GeneratorConst {
    /**
     * 作者
     */
    public static final String AUTHOR = "author";
    /**
     * 说明
     */
    public static final String DESCRIBE = "describe";
    /**
     * 类名
     */
    public static final String CLASS_NAME = "className";
    /**
     * 小写类名
     */
    public static final String INJECT_CLASS_NAME = "lowerClassName";
    /**
     * 包名
     */
    public static final String PACKAGE_NAME = "packageName";
    /**
     * 模块名
     */
    public static final String MODEL_NAME = "dto";
    /**
     * 模板根路径
     */
    public static final String TEMPLATE_ROOT = "templateRoot";
    /**
     * 模板id(对应TEMPLATE_ROOT下的文件夹名)
     */
    public static final String TEMPLATE_ID = "templateId";
    /**
     * 模板文件后缀名
     */
    public static final String TEMPLATE_FILE_SUFFIX = "templateFileSuffix";
    /**
     * 文件输出路径
     */
    public static final String OUTPUT_PATH = "outputPath";
    /**
     * 数据库类型
     */
    public static final String DATABASE_TYPE = "dbType";
    /**
     * 数据库连接url
     */
    public static final String CONNECTION_URL = "dbUrl";
    /**
     * 数据库名
     */
    public static final String DATABASE_NAME = "dbName";
    /**
     * 驱动类
     */
    public static final String JDBC_DRIVER = "jdbcDriver";
    /**
     * 用户名
     */
    public static final String USERNAME = "username";
    /**
     * 密码
     */
    public static final String PASSWORD = "password";
    /**
     * 表名
     */
    public static final String TABLE_NAME = "table";
    /**
     * 表前缀
     */
    public static final String TABLE_PREFIX = "tablePrefix";
    /**
     * excel模板id
     */
    public static final String EXCEL_TEMPLATE_ID = "excelTemplateId";
    /**
     * excel导出文件保存路径
     */
    public static final String EXCEL_EXPORT_SAVE_PATH = "excelExportSavePath";
    /**
     * 数据列命名规则
     */
    public static final String COLUMN_NAMING_RULE = "columnNamingRule";

    /////////////表相关配置//////////////
    /**
     * 表:逻辑删除
     */
    public static final String TABLE_LOGIC_DEL_ORDER = "logicDel";
    public static final String TABLE_SIMPLE_LOGIC_DEL_ORDER = "d";
    public static final String TABLE_DEL_FILED_ORDER = "delFiled";
    public static final String TABLE_SIMPLE_DEL_FILED_ORDER = "df";
    /**
     * 表:乐观锁
     */
    public static final String TABLE_USE_LOCK_ORDER = "useLock";
    public static final String TABLE_SIMPLE_USE_LOCK_ORDER = "l";
    public static final String TABLE_LOCK_FILED_ORDER = "lockFiled";
    public static final String TABLE_SIMPLE_LOCK_FILED_ORDER = "lf";

    /////////////列相关配置//////////////
    public static final String COLUMN_HIDDEN_ORDER = "hidden";
    public static final String COLUMN_BLANK_ORDER = "blank";
    public static final String COLUMN_REGEX_ORDER = "regex";
    public static final String COLUMN_INTERVAL_ORDER = "interval";
    public static final String COLUMN_COVER_ORDER = "cover";
    public static final String COLUMN_LIKE_ORDER = "like";

    public static final String COLUMN_SIMPLE_HIDDEN_ORDER = "h";
    public static final String COLUMN_SIMPLE_BLANK_ORDER = "b";
    public static final String COLUMN_SIMPLE_REGEX_ORDER = "r";
    public static final String COLUMN_SIMPLE_INTERVAL_ORDER = "i";
    public static final String COLUMN_SIMPLE_COVER_ORDER = "c";
    public static final String COLUMN_SIMPLE_LIKE_ORDER = "l";
}
