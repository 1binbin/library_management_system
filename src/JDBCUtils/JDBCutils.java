package JDBCUtils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author hongxiaobin
 * @create 2021/12/11-20:46
 * @function 用于数据库连接和针对注册登录的操作
 */
@SuppressWarnings("ALL")
public class JDBCutils {
    /*连接数据库*/
    public static Connection getConnection() throws Exception {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("JDBCUtils/xctest.properties");
        Properties properties = new Properties();
        properties.load(inputStream);

        String driver = properties.getProperty("driver");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    /*关闭查询资源*/
    public static void getClose(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*关闭插入资源*/
    public static void getClose(Connection connection, PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*定义第一个数据是否存在
     * 返回值：true表示存在，false表示不存在*/
    public static boolean isexistence(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCutils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            //要加上这个判断，判断下一行有没有数据先
            if (resultSet.next()) {
                if (resultSet.getObject(1) != null) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCutils.getClose(connection, preparedStatement, resultSet);
        }
        return false;
    }

    /*定义第一个数据是否存在
     * 返回值：true表示存在，false表示不存在*/
    public static boolean isexistence(String sql) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCutils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            //要加上这个判断，判断下一行有没有数据先
            if (resultSet.next()) {
                if (resultSet.getObject(1) != null) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCutils.getClose(connection, preparedStatement, resultSet);
        }
        return false;
    }

    /*
     * 定义判断借阅证编号是否存在和密码是否正确,用户判断可否登录
     * 返回值：一个包含借阅证编号和密码的数组
     */
    public static String[] Login(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String[] arrays = new String[2];
        try {
            connection = JDBCutils.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) { //要加上这个判断，判断下一行有没有数据先
                arrays[0] = resultSet.getString(1);
                arrays[1] = resultSet.getString(2);
            }
            return arrays;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCutils.getClose(connection, preparedStatement, resultSet);
        }
        return null;
    }

    /*
     * 定义增删改
     */
    public static void setADM(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCutils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCutils.getClose(connection, preparedStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 查询全部全部记录
     */
    public static <T> List<T> getselectlist(Class<T> clazz, String sql) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            ArrayList<T> arrayList = new ArrayList<>();
            while (resultSet.next()) {
                T t = clazz.newInstance();
                //遍历每一列
                for (int i = 0; i < columnCount; i++) {
//                    获取列值
                    Object columnVal = resultSet.getObject(i + 1);
//                    获取列名，列的别名使用类的属性充当
                    String columnLabel = resultSetMetaData.getColumnLabel(i + 1);
//                    利用反射，给对象的属性赋值
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnVal);
                }
//                将每一个对象传到数组中
                arrayList.add(t);
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            关闭资源
            getClose(connection, preparedStatement, resultSet);
        }
        return null;
    }

    /*
    聚集函数查询*/
    public static long getcount(String sql) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            long count = 0;
            while (resultSet.next()) {
                count = resultSet.getLong(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            关闭资源
            getClose(connection, preparedStatement, resultSet);
        }
        return 0;
    }
}