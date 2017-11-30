package com.java.sys.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.java.sys.accessToken.AccessTokenUtil;

/**
 * @author Administrator
 * 连接管理工具类
 */
public class ConnectionManager {
	
	static final String PROPERTIES = "db.properties"; //数据库连接基础配置文件名
	
	static final String DRIVER = getValue("jdbc.driverClass");
	static final String URL = getValue("jdbc.jdbcUrl");
	static final String USERNAME = getValue("jdbc.user");
	static final String PASSWORD = getValue("jdbc.password");
	
	/**
	 * 通过静态初始化，加载数据库驱动
	 */
	static{
		try {
			//1. 用Class.forName("驱动程序类名");将驱动程序载入到JVM下这样jvm就可找到这个 驱动程序类了。
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.err.println("加载数据库驱动失败！" + e.getMessage());
		}
	}
	
	/**
	 * 获得数据库连接对象
	 * @return conn
	 */
	public static Connection getConnection(){
		try {
			Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 关闭各对象
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public static void close(Connection conn, Statement stmt, ResultSet rs){
		try {
			if(rs != null){
				rs.close();
			}
			
			if(stmt != null){
				stmt.close();
			
			}if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据properties属性文件中的键读取相应的值
	 * @param key
	 * @return value
	 */
	public static String getValue(String key){
		Properties prop = new Properties();
		InputStream in = ConnectionManager.class.getClassLoader().getResourceAsStream(PROPERTIES);
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
	
}


