package com.example.demo_spring_boot_mysql.util;



import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.io.IOException;
import java.io.Reader;
import com.example.demo_spring_boot_mysql.util.Constants;
import java.io.InputStream;
public class DB {
    private static SqlSessionFactory sqlSessionFactory;
    private static String environment = "development";
    private static String readProperty(ResourceBundle resourceBundle, String key, String defaultValue) {
		String value = defaultValue;
		try {
			value = resourceBundle.getString(key);
		} catch (Exception e) {

		}
		return value;
	}
    private static Properties initProperties() {
    	System.out.println("initProperties ------------------------ ");
    	
    	
        ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.dataBaseConfigFile);
		ResourceBundle resourceAppBundle = ResourceBundle.getBundle(Constants.appConfigFileName);
		
        Properties props = new Properties();
        String env = readProperty(resourceAppBundle, "spring.profiles.active", "dev");
			String driver = "", url = "", encryptedUsername = "", encryptedPassword = "", isEnscrypt = "",
					mysql_maximum_active_connections = "50";
		System.out.println("env " + env);
        switch (env) {
            case "prod":
				driver = readProperty(resourceBundle, "prod_driver", "com.mysql.jdbc.Driver");
				url = readProperty(resourceBundle, "prod_url", "");
				encryptedUsername = readProperty(resourceBundle, "prod_username", "");
				encryptedPassword = readProperty(resourceBundle, "prod_password", "");
				isEnscrypt = readProperty(resourceBundle, "prod_encrypt", "");
				mysql_maximum_active_connections = readProperty(resourceBundle, "prod_mysql_maximum_active_connections",
						"50");
				break;
            default:
				driver = readProperty(resourceBundle, "dev_driver", "com.mysql.jdbc.Driver");
				url = readProperty(resourceBundle, "dev_url", "");
				encryptedUsername = readProperty(resourceBundle, "dev_username", "");
				encryptedPassword = readProperty(resourceBundle, "dev_password", "");
				isEnscrypt = readProperty(resourceBundle, "dev_encrypt", "");
				mysql_maximum_active_connections = readProperty(resourceBundle, "dev_mysql_maximum_active_connections",
						"50");
			}
            props.setProperty("database.driver", driver);
			props.setProperty("database.url", url);
			props.setProperty("database.username", encryptedUsername);
			props.setProperty("database.password", encryptedPassword);
            return props;
    }
    
	static {
        try {
            Reader configReader = null;
            configReader = Resources.getResourceAsReader(Constants.sqlMapconfigXml);
            Properties props = initProperties();
            System.out.println("props " + props);
            System.out.println("configReader: "+configReader);
            System.out.println("environment: "+environment);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(configReader, environment, props);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing SqlSessionFactory. Cause: " + e);
        }
    }

    public static SqlSession getSession() {
        return sqlSessionFactory.openSession();
    }
}
