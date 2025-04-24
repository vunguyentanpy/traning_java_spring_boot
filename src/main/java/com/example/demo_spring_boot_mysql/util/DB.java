package com.example.demo_spring_boot_mysql.util;



import org.apache.ibatis.io.Resources;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.util.Properties;
import java.util.ResourceBundle;
import java.io.IOException;
import java.io.Reader;


public class DB {
    private static SqlSessionFactory sqlSessionFactory;
    private static String environment = "development";

    private static String readProperty(ResourceBundle resourceBundle, String key, String defaultValue) {
		String value = defaultValue;
		try {
			value = resourceBundle.getString(key);
		} catch (Exception e) {
            return defaultValue;
		}
		return value;
	}
    private static Properties initProperties() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.dataBaseConfigFile);
		ResourceBundle resourceAppBundle = ResourceBundle.getBundle(Constants.appConfigFileName);
		
        Properties props = new Properties();
        String env = readProperty(resourceAppBundle, "spring.profiles.active", "dev");
        String driver = "";
        String url = "";
        String encryptedUsername = "";
        String encryptedPassword = "";
		System.out.println("env " + env);
        switch (env) {
            case "prod":
				break;
            default:
				driver = readProperty(resourceBundle, "dev_driver", "com.mysql.jdbc.Driver");
				url = readProperty(resourceBundle, "dev_url", "");
				encryptedUsername = readProperty(resourceBundle, "dev_username", "");
				encryptedPassword = readProperty(resourceBundle, "dev_password", "");
			}
        props.setProperty("database.driver", driver);
        props.setProperty("database.url", url);
        props.setProperty("database.username", encryptedUsername);
        props.setProperty("database.password", encryptedPassword);
        return props;
    }
    
	static {
        try {
            Reader configReader = Resources.getResourceAsReader(Constants.sqlMapconfigXml);
            Properties props = initProperties();
            System.out.println("props " + props);
            System.out.println("configReader: "+configReader);
            System.out.println("environment: "+environment);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(configReader, environment, props);
        } catch (IOException e) {
            throw new RuntimeException("Error initializing SqlSessionFactory. Cause: " + e);
        }
    }

    public static SqlSession getSession() {
        return sqlSessionFactory.openSession();
    }
}
