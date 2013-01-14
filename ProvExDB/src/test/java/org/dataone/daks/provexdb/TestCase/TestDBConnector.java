package org.dataone.daks.provexdb.TestCase;

import java.sql.Connection;

import org.dataone.daks.provexdb.DAO.dbconfig.DatabaseConnector;

import junit.framework.TestCase;

public class TestDBConnector extends TestCase {
	public void testConnect(){
		Connection conn = DatabaseConnector.getConnection();
		assertNotNull(conn);
	}
	public static void main(String args[]){
		junit.textui.TestRunner.run(TestDBConnector.class);
	}
}
