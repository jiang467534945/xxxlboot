package com.xxxlboot.common.gencode.database.impl;





import com.xxxlboot.common.gencode.bean.ColumnInfo;
import com.xxxlboot.common.gencode.bean.JavaType;
import com.xxxlboot.common.gencode.bean.TableInfo;
import com.xxxlboot.common.gencode.config.DatabaseConfig;
import com.xxxlboot.common.gencode.constant.JavaTypeConst;
import com.xxxlboot.common.gencode.database.DatabaseInfoReader;
import com.xxxlboot.common.gencode.util.JdbcUtil;
import com.xxxlboot.common.util.StringUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Explain:     [MySql数据库信息读取实现类]
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 */
public class MySqlDatabaseInfoReader implements DatabaseInfoReader {

    /**SQL:查询数据表列信息*/
    private static final String SQL_QUERY_COLUMN_INFO = "SELECT COLUMN_NAME, COLUMN_KEY, IS_NULLABLE, DATA_TYPE, COLUMN_TYPE, COLUMN_DEFAULT, COLUMN_COMMENT " +
            "FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name=? AND table_schema=?";
    /**SQL:查询数据表信息*/
    private static final String SQL_QUERY_TABLE_INFO = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE table_schema=? AND table_type='base table'";
    private  static  final  String SQL_MYSQL_TABLE_INFO=	"SELECT TABLE_NAME, TABLE_COMMENT  FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = ? AND table_name LIKE ?";
    private static final String typeLenRegex = "\\(\\d+\\)";
    private static final Pattern typeLenPattern = Pattern.compile(typeLenRegex);

    @Override
    public List<ColumnInfo> getColumnsInfo(DatabaseConfig dbConfig) {
        String dbName = dbConfig.getDbName();
        String tableName = dbConfig.getTableName();
        Connection connection = dbConfig.getConnection();
        List<Map> mapList = JdbcUtil.executeSql(connection, SQL_QUERY_COLUMN_INFO, tableName, dbName);
        List<ColumnInfo> list = new ArrayList<ColumnInfo>();
        for (Map map : mapList) {
            ColumnInfo columnInfo = new ColumnInfo();
            String column = StringUtil.trim(map.get("COLUMN_NAME"));//数据库字段名
            String [] backB= column.split("_");
             if("system".equals(backB[0])||column.equals("id")||column.equals("del_flag")||column.equals("version_number")||column.equals("create_date")||column.equals("create_by")||column.equals("update_by")||column.equals("update_date")||column.equals("tenant_id")||column.equals("enabled")){

            }
            else{
                String columnKey = StringUtil.trim(map.get("COLUMN_KEY"));
                String isNull = StringUtil.trim(map.get("IS_NULLABLE"));
                String dataType = StringUtil.trim(map.get("DATA_TYPE"));
                String columnType = StringUtil.trim(map.get("COLUMN_TYPE"));
                String columnDefault = (String) map.get("COLUMN_DEFAULT");
                String columnComment = StringUtil.trim(map.get("COLUMN_COMMENT"));
                columnInfo.setColumn(column);
                columnInfo.setDataType(dataType);
                columnInfo.setColumnType(columnType);
                columnInfo.setColumnDefault(columnDefault);
                columnInfo.setColumnComment(columnComment);
                if (columnKey.contains("PRI")) {
                    columnInfo.setPrimaryKey(true);
                    columnInfo.setUnique(true);
                }
                if (columnKey.contains("UNI")) {
                    columnInfo.setUnique(true);
                }
                if (isNull.equalsIgnoreCase("NO")) {
                    columnInfo.setNull(false);
                }
                columnInfo.setColumnLen(extractColumnLen(columnType));
                list.add(columnInfo);
            }

        }
        return list;
    }

    /**
     * 从columnType中提取数据长度,例如varchar(60),返回整数60
     * @param columnType
     * @return
     */
    private int extractColumnLen(String columnType) {
        Matcher matcher = typeLenPattern.matcher(columnType);
        if (matcher.find()) {
            String group = matcher.group();
            String s = group.substring(1, group.length() - 1);
            return Integer.parseInt(s);
        }
        return 0;
    }

    @Override
    public List<TableInfo> getTablesInfo(DatabaseConfig dbConfig) {
        String dbName = dbConfig.getDbName();
        StringBuilder sql = new StringBuilder(SQL_MYSQL_TABLE_INFO);
        /**
         * 生成排除设定表的sql
         */
        String removeTables = StringUtil.trim(dbConfig.getRemoveTables());
        String[] split = removeTables.split(",");
        if (split.length > 0) {
            sql.append(" AND TABLE_NAME NOT IN(");
            for (int i = 0; i < split.length; i++) {
                if (i != 0) {
                    sql.append(",");
                }
                sql.append("'" + StringUtil.trim(split[i]) + "'");
            }
            sql.append(")");
        }


        Connection connection = dbConfig.getConnection();
        List<Map> mapList = JdbcUtil.executeSql(connection, sql.toString(),dbName,dbConfig.getTablePrefix());
        List<TableInfo> list = new ArrayList<TableInfo>();
        for (Map map : mapList) {
            String table = StringUtil.trim(map.get("TABLE_NAME"));
            String tableComment = StringUtil.trim(map.get("TABLE_COMMENT"));
            TableInfo tableInfo = new TableInfo();
            tableInfo.setTable(table);
            tableInfo.setTableComment(tableComment);
            list.add(tableInfo);
        }
        return list;
    }

    @Override
    public List<TableInfo> getTableInfo(DatabaseConfig dbConfig) {
        String dbName = dbConfig.getDbName();
        String tableName = StringUtil.trim(dbConfig.getTableName());
        StringBuilder sql = new StringBuilder(SQL_QUERY_TABLE_INFO);
        /**
         * 生成查询指定表的sql
         */
        String[] split = tableName.split(",");
        if (split.length > 0) {
            sql.append(" AND TABLE_NAME IN(");
            for (int i = 0; i < split.length; i++) {
                if (i != 0) {
                    sql.append(",");
                }
                sql.append("'" + StringUtil.trim(split[i]) + "'");
            }
            sql.append(")");
        }
        Connection connection = dbConfig.getConnection();
        List<Map> mapList = JdbcUtil.executeSql(connection, sql.toString(), dbName);
        List<TableInfo> list = new ArrayList<TableInfo>();
        for (Map map : mapList) {
            String table = StringUtil.trim(map.get("TABLE_NAME"));
            String tableComment = StringUtil.trim(map.get("TABLE_COMMENT"));
            TableInfo tableInfo = new TableInfo();
            tableInfo.setTable(table);
            tableInfo.setTableComment(tableComment);
            list.add(tableInfo);
        }
        return list;
    }

    @Override
    public JavaType dbTypeCoverToJavaType(String dbType) {
        dbType = StringUtil.trim(dbType);
        if (StringUtil.whereStrInIgnoreCase(dbType, "int", "integer")) {
            return JavaTypeConst.INT;
        } else if (StringUtil.whereStrInIgnoreCase(dbType, "bigint")) {
            return JavaTypeConst.LONG;
        } else if (StringUtil.whereStrInIgnoreCase(dbType, "varchar", "text", "longtext", "char")) {
            return JavaTypeConst.STRING;
        } else if (StringUtil.whereStrInIgnoreCase(dbType, "decimal", "float", "numeric", "double")) {
            return JavaTypeConst.DOUBLE;
        } else if (StringUtil.whereStrInIgnoreCase(dbType, "datetime", "timestamp", "date", "time")) {
            return JavaTypeConst.DATE;
        } else if (StringUtil.whereStrInIgnoreCase(dbType, "boolean", "bit")) {
            return JavaTypeConst.BOOLEAN;
        } else {
            return JavaTypeConst.OBJECT;
        }
    }
}
