package com.example.demo_spring_boot_mysql.util;
public class Constants {
    public static final String dataBaseConfigFile = "database";
	public static final String sqlMapconfigXml = "mybatis/mybatis-config.xml";
	public static final String appConfigFileName="application";
	public static final String AES_KEY = "P_Rh@95dv1Zx#=OS";

	public static final String SECRET_KEY="25a6f201f7f43132035d95e1b0125ec3f937ec284bd93e6f4ad17078b75b3cdf";
	public static final Integer ACCESS_TOKEN_EXPIRATION_TIME = 15*60*1000;
	public static final Integer REFRESH_TOKEN_EXPIRATION_TIME= 2*24*3600*1000;
	public static final String ALGORITHM= "AES/CBC/PKCS5Padding";
	public static final String INIT_VECTOR= "abcdef9876543210";
	public static final String CRYPTO_KEY= "0123456789abcdef";
}