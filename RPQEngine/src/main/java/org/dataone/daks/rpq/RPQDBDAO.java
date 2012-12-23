package org.dataone.daks.rpq;

import java.sql.*;

public class RPQDBDAO {

	
    private Connection conn;
    private static final RPQDBDAO instance = new RPQDBDAO();

    
    private RPQDBDAO() {
    }
    
    
    private void init(String dbname, String username, String password) {
        try { 
            DriverManager.registerDriver(new org.postgresql.Driver());
            conn = DriverManager.getConnection(
               "jdbc:postgresql://hostname:port/" + dbname, username, password);  
        } 
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
    
    
    public static RPQDBDAO getInstance() {
    	return instance;
    }


	public static void main(String args[]){
		RPQDBDAO db = new RPQDBDAO();
		System.out.println("Running RPQDBDAO");
	}
	
	
}


