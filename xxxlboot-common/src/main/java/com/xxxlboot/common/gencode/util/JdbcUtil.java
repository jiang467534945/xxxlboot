package com.xxxlboot.common.gencode.util;





import com.xxxlboot.common.gencode.constant.DbConst;
import com.xxxlboot.common.util.StringUtil;

import java.sql.*;
import java.util.*;

/**
 * Explain:     [jdbc工具类,使用了简单的connection缓存,避免重复连接]
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 */
public class JdbcUtil {

    private static final Queue<Connection> poll = new LinkedList<Connection>();//数据库连接池

    /**
     * 获取数据库连接
     */
    public static Connection getConnection(String url, String username, String password) {
        if (poll != null && !poll.isEmpty()) {//从数据库连接池获取
            return poll.poll();
        }
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * 执行sql,返回结果集
     * @param con 数据库连接
     * @param sql sql语句
     * @param param 参数
     * @return
     */
    public static List<Map> executeSql(Connection con, String sql, Object... param) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map> mapList = null;
        try {
            ps = con.prepareStatement(sql);
            int index = 1;
            for (Object o : param) {
                ps.setObject(index++, o);
            }

            ps.execute();
            rs = ps.getResultSet();
            mapList = convertResultSetToMap(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, con);
        }
        return mapList;
    }

    private static List<Map> convertResultSetToMap(ResultSet rs) throws SQLException {
        List<Map> listMap = new ArrayList<Map>();
        if (rs != null) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            while (rs.next()) {
                Map map = new HashMap();
                for (int i = 1; i <= count; ++i) {
                    String key = rsmd.getColumnLabel(i);
                    Object value = rs.getObject(i);
                    map.put(key, value);
                }
                listMap.add(map);
            }
        }
        return listMap;
    }

    /**
     * 关闭连接
     */
    public static void close(ResultSet rs, Statement stm, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (stm != null) {
                stm.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (con != null) {
//                con.close();
                poll.add(con);//Connection返回连接池复用
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据数据库连接自动识别驱动类
     * @param dbUrl
     * @return
     */
    public static String automaticJdbcDriver(String dbUrl) {
        if (StringUtil.notEmpty(dbUrl)) {
            String url = dbUrl.trim().toLowerCase();
            if (url.startsWith("jdbc:mysql")) {
                return "com.mysql.cj.jdbc.Driver";
            } else if (url.startsWith("jdbc:oracle")) {
                return "oracle.jdbc.driver.OracleDriver";
            } else if (url.startsWith("jdbc:sqlserver")) {
                return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            } else if (url.startsWith("jdbc:postgresql")) {
                return "org.postgresql.Driver";
            } else if (url.startsWith("jdbc:sybase")) {
                return "com.sybase.jdbc.SybDriver";
            } else if (url.startsWith("jdbc:db2")) {
                return "com.ibm.db2.jcc.DB2Driver";
            }
        }
        return null;
    }

    /**
     * 根据驱动类识别数据库类型
     * @param jdbcDriver
     * @return
     */
    public static String automaticDbType(String jdbcDriver) {
        if(jdbcDriver.contains("mysql")) {
            return DbConst.DB_TYPE_MYSQL;
        } else if(jdbcDriver.contains("oracle")) {
            return DbConst.DB_TYPE_ORACLE;
        }
        return null;
    }
}
