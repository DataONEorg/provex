package org.dataone.daks.provexdb.JDBCDAO;

import java.sql.*;
import java.io.*;
import java.util.StringTokenizer;

import org.json.*;


public class ProvExDBJDBCDAO {

	
    private Connection conn;
    private static final ProvExDBJDBCDAO instance = new ProvExDBJDBCDAO();

    
    private ProvExDBJDBCDAO() {
    }
    
    
    public static ProvExDBJDBCDAO getInstance() {
    	return instance;
    }
    
    
    private void init(String dbname, String username, String password) {
    	try { 
            DriverManager.registerDriver(new org.postgresql.Driver());
            conn = DriverManager.getConnection(
               "jdbc:postgresql://localhost:5432/" + dbname, username, password);  
        } 
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
    
    
    public void initFromConfigFile(String filename) {
		BufferedReader input = null;
		try {
			InputStream inputStream = Thread.currentThread().getContextClassLoader().
					getResourceAsStream(filename);
			input =  new BufferedReader(new InputStreamReader(inputStream));
			String dbname = input.readLine();
			String username = input.readLine();
			String password = input.readLine();
			input.close();
			this.init(dbname, username, password);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void executeStatement(String statement) {
		try {
			Statement stmt = this.conn.createStatement();
			stmt.execute(statement);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public String getTraceJSON() {
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject();
			JSONArray nodesArray = this.getNodesJSON();
			JSONArray edgesArray = this.getEdgesJSON();
			jsonObj.put("nodes", nodesArray);
			jsonObj.put("edges", edgesArray);
		}
		catch(JSONException je) {
			je.printStackTrace();
		}
		return jsonObj.toString();
	}
	
	
	public String getProvenanceStageJSON(String stageId) {
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject();
			JSONArray nodesArray = this.getNodesJSON();
			JSONArray edgesArray = this.getProvenanceStageEdgesJSON(stageId);
			jsonObj.put("nodes", nodesArray);
			jsonObj.put("edges", edgesArray);
		}
		catch(JSONException je) {
			je.printStackTrace();
		}
		return jsonObj.toString();
	}
	

	public JSONArray getEdgesJSON() {
		String query = "SELECT * FROM edge";
		JSONArray jsonArray = new JSONArray();
        try {
        	Statement stmt = this.conn.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		String startNodeId = rs.getString(2);
            	String endNodeId = rs.getString(3);
            	String edgeLabel = rs.getString(4);
            	jsonArray.put(createEdgeJSON(startNodeId, endNodeId, edgeLabel));
        	}
        	rs.close();
            stmt.close();
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
        return jsonArray;
	}
	
	
	public boolean executePrepareLineage() {
		String query = "SELECT dp_prepare_lineage();";
		boolean retVal = false;
        try {
        	Statement stmt = this.conn.createStatement();
        	retVal = stmt.execute(query);
            stmt.close();
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
        return retVal;
	}
	
	
	public boolean executeApplyURHide() {
		String query = "SELECT dp_apply_ur_hide();";
		boolean retVal = false;
        try {
        	Statement stmt = this.conn.createStatement();
        	retVal = stmt.execute(query);
            stmt.close();
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
        return retVal;
	}
	
	
	public JSONArray getProvenanceStageEdgesJSON(String stageId) {
		String query = "SELECT * FROM provenance_stage WHERE stageId='" + stageId + "'";
		JSONArray jsonArray = new JSONArray();
        try {
        	Statement stmt = this.conn.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		String startNodeId = rs.getString(4);
            	String endNodeId = rs.getString(5);
            	String edgeLabel = rs.getString(6);
            	jsonArray.put(createEdgeJSON(startNodeId, endNodeId, edgeLabel));
        	}
        	rs.close();
            stmt.close();
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
        return jsonArray;
	}
	
	
	public JSONArray getNodesJSON() {
		String query = "SELECT * FROM node";
		JSONArray jsonArray = new JSONArray();
        try {
        	Statement stmt = this.conn.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		String nodeId = rs.getString(1);
            	String nodeType = rs.getString(2);
            	jsonArray.put(createNodeJSON(nodeId, nodeType));
        	}
        	rs.close();
            stmt.close();
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
        return jsonArray;
	}
	
	
	public JSONObject createNodeJSON(String nodeId, String nodeType) {
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject();
			jsonObj.put("nodeId", nodeId);
			jsonObj.put("nodeType", nodeType);
		}
		catch(JSONException je) {
			je.printStackTrace();
		}
		return jsonObj;
	}
	
	
	public JSONObject createEdgeJSON(String startNodeId, String endNodeId, String edgeLabel) {
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject();
			jsonObj.put("startNodeId", startNodeId);
			jsonObj.put("endNodeId", endNodeId);
			jsonObj.put("edgeLabel", edgeLabel);
		}
		catch(JSONException je) {
			je.printStackTrace();
		}
		return jsonObj;
	}
	
	
	public int insertURMaster(String traceId, String reqId, String evalType ) {
		String update = "INSERT INTO user_request_master VALUES ('" + traceId + "','" + reqId + "','" + evalType + "');";
		int retVal = -1;
        try {
        	Statement stmt = this.conn.createStatement();
        	retVal = stmt.executeUpdate(update);
            stmt.close();
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
        return retVal;
	}
	
	
	public int insertURDetail(String reqId, String stageId, String reqType, String nodeId, String targetNodeId ) {
		String update = "INSERT INTO user_request_master VALUES ('" + reqId + "','" + stageId + "','" + reqType + 
				 "','" + nodeId + "','" + targetNodeId + "');";
		int retVal = -1;
        try {
        	Statement stmt = this.conn.createStatement();
        	retVal = stmt.executeUpdate(update);
            stmt.close();
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
        return retVal;
	}
	
	
}


