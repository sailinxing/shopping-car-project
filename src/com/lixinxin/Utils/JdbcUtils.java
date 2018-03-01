package com.lixinxin.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtils {
	private static String driverClass=null;
	private static String url=null;
	private static String user=null;
	private static String password=null;
	static{
		InputStream input=Thread.currentThread().getContextClassLoader().getResourceAsStream("JdbcUtils.properties");
		Properties p=new Properties();
		try {
			p.load(input);
			driverClass=p.getProperty("driverClass");
			url=p.getProperty("url");
			user=p.getProperty("user");
			password=p.getProperty("password");
			Class.forName(driverClass);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url, user, password);
	}
	public static void closeAll(Connection conn,Statement sttm,ResultSet rs){
		try {
			if (conn != null) {
				conn.close();
			}
			if (sttm != null) {
				sttm.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
