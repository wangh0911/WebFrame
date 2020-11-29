package cn.roboteco.chapter2.helper;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.roboteco.chapter2.utils.PropUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DatabaseHelperBasicDataSource {
    private static  final Logger logger = LoggerFactory.getLogger(DatabaseHelperBasicDataSource.class);
    private static final QueryRunner QUERY_RUNNER ;
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;
    private static final BasicDataSource DATA_SOURCE;

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        QUERY_RUNNER = new QueryRunner();
        CONNECTION_HOLDER = new ThreadLocal<>();

        Properties properties = PropUtil.loadProperties("config.properties");
        DRIVER = properties.getProperty("jdbc.driver");
        URL = properties.getProperty("jdbc.url");
        USERNAME = properties.getProperty("jdbc.username");
        PASSWORD = properties.getProperty("jdbc.password");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);
    }

    public static Connection getConnection(){
        Connection connection  = CONNECTION_HOLDER.get();
        if(connection == null){
            try {
                connection = DATA_SOURCE.getConnection();
            }catch (SQLException e){
                logger.error(ExceptionUtil.stacktraceToString(e));
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
        return connection;
    }


    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params){
        List<T> entityList;
        try {
            entityList = QUERY_RUNNER.query(getConnection(), sql, new BeanListHandler<T>(entityClass), params);
        }catch (SQLException e){
            logger.error(ExceptionUtil.stacktraceToString(e));
            throw new RuntimeException(e);
        }
        return entityList;
    }

    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params){
        T entity;
        try {
            entity = QUERY_RUNNER.query(getConnection(), sql, new BeanHandler<T>(entityClass), params);
        } catch (Exception e){
            logger.error(ExceptionUtil.stacktraceToString(e));
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * Map表示列名与列值的映射关系
     *
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params){
        List<Map<String, Object>> result;
        try {
            result = QUERY_RUNNER.query(getConnection(), sql, new MapListHandler(), params);
        } catch (Exception e){
            logger.error(ExceptionUtil.stacktraceToString(e));
            throw new RuntimeException(e);
        }
        return result;

    }

    /**
     * 执行更新（包括insert、delete、update）
     * @param sql
     * @param params
     * @return 返回执行结果的行数
     */
    public static int executeUpdate(String sql, Object... params){
        int rows = 0;
        try {
            rows = QUERY_RUNNER.update(getConnection(), sql, params);
        } catch (Exception e){
            logger.error(ExceptionUtil.stacktraceToString(e));
            throw new RuntimeException(e);
        }
        return rows;
    }

    public static <T> boolean insertEntity(Class<T> entityClass, Map<String,Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            logger.error("fieldMap is null");
            return false;
        }
        String sql = "insert into " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName:fieldMap.keySet()){
            columns.append(fieldName).append(",");
            values.append("?,");
        }
        columns.replace(columns.lastIndexOf(","),columns.length(),")");
        values.replace(values.lastIndexOf(","), values.length(),")");
        sql+=columns+" values " + values;
        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }

    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            logger.error("fieldMap is null");
            return false;
        }
        String sql = "update " + getTableName(entityClass) + " set ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName:fieldMap.keySet()){
            columns.append(fieldName).append("=?,");
        }
        sql += columns.substring(0,columns.lastIndexOf(",")) + " where id=?";
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();
        return executeUpdate(sql,params) == 1;
    }

    public static <T> boolean deleteEntity(Class<T> entityClass, long id){
        String sql = "delete from "+ getTableName(entityClass) + " where id = ?";
        return executeUpdate(sql,id) == 1;
    }
    private static String getTableName(Class<?> entityClass){
        return entityClass.getSimpleName();
    }
}
