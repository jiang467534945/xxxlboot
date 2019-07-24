package com.xxxlboot.common.gencode.config;






import cn.hutool.core.util.StrUtil;
import com.xxxlboot.common.gencode.constant.DbConst;
import com.xxxlboot.common.gencode.util.JdbcUtil;

import java.sql.Connection;

/**
 * Explain:     [数据库配置信息]
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 */
public class DatabaseConfig {

    /**数据库连接url*/
    private String dbUrl;
    /**数据库驱动*/
    private String jdbcDriver;
    /**数据库用户名*/
    private String username;
    /**数据库密码*/
    private String password;
    /**数据库*/
    private String dbName;
    /**数据库类型*/
    private String dbType;
    /**表名*/
    private String tableName;
    /**表名前缀*/
    private String tablePrefix;
    /**表命名规则*/
    private String tableNamingRule;
    /**列命名规则*/
    private String columnNamingRule;
    /**排除的表*/
    private String removeTables;

    public Connection getConnection(){
        try {
            Class.forName(this.jdbcDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return JdbcUtil.getConnection(this.dbUrl, this.username, this.password);
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getColumnNamingRule() {
        return columnNamingRule;
    }

    public void setColumnNamingRule(String columnNamingRule) {
        this.columnNamingRule = columnNamingRule;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemoveTables() {
        return removeTables;
    }

    public void setRemoveTables(String removeTables) {
        this.removeTables = removeTables;
    }

    public String getTableNamingRule() {
        return tableNamingRule;
    }

    public void setTableNamingRule(String tableNamingRule) {
        this.tableNamingRule = tableNamingRule;
    }

    /**
     * 验证配置,并自动补齐配置
     */
    public void verifyConfig() {
        if (StrUtil.isEmpty(dbName)) {
            throw new RuntimeException("[Exception]databaseConfig.dbName不能为空");
        }
        if (StrUtil.isEmpty(dbUrl)) {
            throw new RuntimeException("[Exception]databaseConfig.dbUrl不能为空");
        }
        if (StrUtil.isEmpty(jdbcDriver)) {
            String driver = JdbcUtil.automaticJdbcDriver(dbUrl);
            if (driver != null) {
                this.setJdbcDriver(driver);
            } else {
                throw new RuntimeException("[Exception]databaseConfig.jdbcDriver不能为空");
            }
        }
        if (StrUtil.isEmpty(dbType)) {
            String type = JdbcUtil.automaticDbType(this.getJdbcDriver());
            if (type != null) {
                this.setDbType(type);
            } else {
                throw new RuntimeException("[Exception]databaseConfig.dbType不能为空");
            }
        }
        if (StrUtil.isEmpty(tableNamingRule)) {
            tableNamingRule = DbConst.NAMING_AUTO;
        }
        if (StrUtil.isEmpty(columnNamingRule)) {
            columnNamingRule = DbConst.NAMING_AUTO;
        }
    }
}
