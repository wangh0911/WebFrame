package cn.roboteco.chapter2.helper;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.roboteco.chapter2.utils.PropUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Properties;

public class DatabaseHelper {
    private static  final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties properties = PropUtil.loadProperties("config.properties");
        DRIVER = properties.getProperty("jdbc.driver");
        URL = properties.getProperty("jdbc.url");
        USERNAME = properties.getProperty("jdbc.username");
        PASSWORD = properties.getProperty("jdbc.password");

        try {
            Class.forName(DRIVER);
        } catch (Exception e){
            logger.error("load jdbc driver error:{}", ExceptionUtil.stacktraceToString(e));
        }
    }

    public static Connection getConnection(){
        Connection connection  = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }catch (SQLException e){
            logger.error(ExceptionUtil.stacktraceToString(e));
        }
        return connection;
    }
    public static void closeConnection(Connection connection){
        if(connection != null){
            try {
                connection.close();
            }catch (SQLException e){
                logger.error("close connection failure", e);
            }
        }
    }
}
