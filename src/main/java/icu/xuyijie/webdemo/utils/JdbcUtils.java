package icu.xuyijie.webdemo.utils;

import java.sql.*;
import java.util.*;

/**
 * @author 徐一杰
 * @date 2022/9/23
 * @description 操作数据库的工具类
 */
public class JdbcUtils {
    private JdbcUtils() {

    }

    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet resultSet;

    /**
     * 连接到数据库
     */
    private static void getConnection() {
        // 检查驱动是否存在
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            System.out.println("驱动加载失败：" + DRIVER_CLASS);
            throw new RuntimeException(e);
        }
        // 创建连接
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("创建连接失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 检查是否已经连接到数据库，如果没有则新建连接
     */
    private static void checkAndInitConnection() {
        if (connection == null) {
            getConnection();
        }
    }

    /**
     * 执行查询语句
     * @param sql SQL 查询语句
     * @param params SQL 语句中的参数值
     * @return 查询结果
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        // 检查是否已经连接到数据库，如果没有则新建连接
        checkAndInitConnection();
        System.out.println("执行SQL：" + sql + "，参数：" + Arrays.toString(params));
        try {
            // 获得statement对象
            statement = connection.prepareStatement(sql);
            // 设置参数值
            setParams(statement, params);
            // 执行sql 得到结果集
            resultSet = statement.executeQuery();
            // 处理结果集
            List<Map<String, Object>> list = new ArrayList<>();
            while (resultSet.next()) {
                // 取出元数据，元数据里包含表拥有的字段名
                ResultSetMetaData metaData = resultSet.getMetaData();
                // 查询结果有几条数据
                int columnCount = metaData.getColumnCount();
                Map<String, Object> map = new HashMap<>(columnCount);
                for (int i = 0; i < columnCount; i++) {
                    // jdbc比较特殊，下标从 1 开始，columnName 返回列名，也就是 id 这个字符串（安全起见，使用getColumnLabel，可以获取 as 的别名）
                    String columnName = metaData.getColumnLabel(i + 1);
                    Object value = resultSet.getObject(i + 1);
                    // 下面的写法和上一行一样，一个是用下标获取值，一个是用表列名获取值
                    //Object value = resultSet.getObject(columnName)
                    map.put(columnName, value);
                }
                list.add(map);
            }
            System.out.println("查询结果：" + list);
            return list;
        } catch (SQLException e) {
            System.out.println("执行SQL出现异常：" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行 插入、更新、删除 语句
     * @param sql SQL 语句
     */
    public static void execute(String sql, Object... params) {
        // 检查是否已经连接到数据库，如果没有则新建连接
        checkAndInitConnection();
        System.out.println("执行SQL：" + sql + "，参数：" + Arrays.toString(params));
        try {
            // 获得statement对象
            statement = connection.prepareStatement(sql);
            // 设置参数值
            setParams(statement, params);
            // 执行sql 得到结果集
            int i = statement.executeUpdate();
            System.out.println("影响行数：" + i);
        } catch (SQLException e) {
            System.out.println("执行SQL出现异常：" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 给sql语句设置参数值
     * @param preparedStatement SQL处理器
     * @param params 参数值
     */
    private static void setParams(PreparedStatement preparedStatement, Object... params) throws SQLException {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; ++i) {
                // SELECT * FROM user WHERE name = ? AND age = ?; 第一个问号的参数下标是1，第二个是2，以此类推
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
    }

}
