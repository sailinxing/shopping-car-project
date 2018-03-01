package com.lixinxin.Utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Utils {
	private static ComboPooledDataSource pool=new ComboPooledDataSource();
	public static Connection getConnection(){
		Connection conn=null;
		try {
			conn=pool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static DataSource getDataSource(){		
		return pool;
	}
}
