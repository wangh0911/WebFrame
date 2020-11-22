package cn.roboteco.chapter2.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropUtil.class);

    public static Properties loadProperties(String fileName){
        Properties properties = null;
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(inputStream == null){
                throw new FileNotFoundException(fileName + " file is not found");
            }
            properties = new Properties();
            properties.load(inputStream);
        }catch (IOException e){
            logger.error("load property file fail",e);
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                }catch (IOException e1){
                    logger.error("close inputstream fail", e1);
                }
            }
        }
        return properties;
    }

    public static String getString(Properties properties, String key){
        return getString(properties, key, "");
    }

    public static String getString(Properties properties, String key, String defaultValue){
        String value = defaultValue;
        if(properties.containsKey(key)){
            value = properties.getProperty(key);
        }
        return value;
    }

    public static Integer getInt(Properties properties, String key){
        return getInt(properties, key, 0);
    }
    public static Integer getInt(Properties properties, String key, Integer defaultValue){
        int value = defaultValue;
        if(properties.containsKey(key)){
            value = Integer.valueOf(properties.getProperty(key));

        }
        return value;
    }
}
