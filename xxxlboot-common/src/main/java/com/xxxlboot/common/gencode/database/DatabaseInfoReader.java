package com.xxxlboot.common.gencode.database;






import com.xxxlboot.common.gencode.bean.ColumnInfo;
import com.xxxlboot.common.gencode.bean.JavaType;
import com.xxxlboot.common.gencode.bean.TableInfo;
import com.xxxlboot.common.gencode.config.DatabaseConfig;

import java.util.List;

/**
 * Explain:     [数据库信息读取接口]
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 */
public interface DatabaseInfoReader {

    /**
     * 获取数据表的所有列信息
     * @param dbConfig
     * @return
     */
     List<ColumnInfo> getColumnsInfo(DatabaseConfig dbConfig);

    /**
     * 批量生成时获取数据库所有表的信息
     * @param dbConfig
     * @return
     */
     List<TableInfo> getTablesInfo(DatabaseConfig dbConfig);

    /**
     * 单表生成时获取数据库表的信息
     * @param dbConfig
     * @return
     */
     List<TableInfo> getTableInfo(DatabaseConfig dbConfig);

    /**
     * 将数据库类型转换为java类型
     * @param dbType
     * @return
     */
     JavaType dbTypeCoverToJavaType(String dbType);

}
