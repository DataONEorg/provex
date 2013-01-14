package org.dataone.daks.provexdb.DAO.procedure;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.dataone.daks.provexdb.DAO.dbconfig.DatabaseConnector;

public class CustomizationProcedure {
	private Statement stmt;
	private Connection conn;
	
	//Perform the customization process using the algoType and return the final stageId
	public String customize(String traceId, String reqId){
		conn = DatabaseConnector.getConnection();
		String stageId = "0";
		try {
			stmt = conn.createStatement();
			
			stmt.execute("SELECT dp_prov_cust("+traceId+","+reqId+");");
			ResultSet rs = stmt.executeQuery("SELECT max(stageId) FROM provenance_stage;");
			stageId = rs.getString(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stageId;
		
	}
}
