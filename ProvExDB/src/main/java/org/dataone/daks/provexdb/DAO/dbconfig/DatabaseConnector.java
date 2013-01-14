package org.dataone.daks.provexdb.DAO.dbconfig;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.dataone.daks.provexdb.DAOException.NoConnectionException;

public class DatabaseConnector implements Serializable {
	protected static final String driver = "org.postgresql.Driver";
	protected static final String user = "provex";
	protected static final String psw = "abc123";
	protected static final String url = "jdbc:postgresql://127.0.0.1:5432/test";
	private static Connection conn;

	public static Connection getConnection() {
		if(conn!=null){
			return conn;
		}
		conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, psw);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		if(conn==null){
			System.out.println("Get connection to database fail.");
		}
		return conn;
	}
	
	public static void removeConnection(){
		conn = null;
	}
}
