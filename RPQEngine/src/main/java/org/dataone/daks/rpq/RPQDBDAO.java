package org.dataone.daks.rpq;

import java.sql.*;
import java.io.*;
import java.util.StringTokenizer;
import org.json.*;



public class RPQDBDAO {

	
    private Connection conn;
    private static final RPQDBDAO instance = new RPQDBDAO();

    
    private RPQDBDAO() {
    }
    
    
    public static RPQDBDAO getInstance() {
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


	public static void main(String args[]){
		System.out.println("Running RPQDBDAO");
		if(args.length != 3) {
			System.out.println("Usage: java RPQDBDAO (input file) (tablename) (config file)");
			System.exit(0);
		}
		RPQDBDAO dao = new RPQDBDAO();
		dao.initFromConfigFile(args[2]);
		dao.createGraphFromCSV(args[0], args[1]);
	}
	
	
	public void createGraphFromCSV(String csvFile, String tableName) {
		BufferedReader input = null;
		try {
			input =  new BufferedReader(new FileReader(new File(csvFile)));
			String line = null;
			StringTokenizer tokenizer = null;
			Statement stmt = this.conn.createStatement();
			stmt.execute("DROP TABLE IF EXISTS " + tableName);
			stmt.execute("CREATE TABLE " + tableName + "(startNode character varying(100), " +
			"label character varying(45), endNode character varying(100))" );
			String insertSQL = "INSERT INTO " + tableName
					+ "(startnode, label, endnode) VALUES(?,?,?)";
			PreparedStatement prepStmt = this.conn.prepareStatement(insertSQL);
			while ( (line = input.readLine()) != null ) {
				if(line.trim().length() == 0)
					continue;
				tokenizer = new StringTokenizer(line, ",");
				String startnode = tokenizer.nextToken();
				String label = tokenizer.nextToken();
				String endnode = tokenizer.nextToken();
				prepStmt.setString(1, startnode);
				prepStmt.setString(2, label);
				prepStmt.setString(3, endnode);
				prepStmt .executeUpdate();
			}
			input.close();
		}
		catch (IOException e) {
				e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void createBinaryTable(String baseTable) {
		try {
			Statement stmt = this.conn.createStatement();
			stmt.execute("DROP TABLE IF EXISTS g");
			stmt.execute("CREATE TABLE g(compstart character varying(100), " +
			"label1 character varying(45), compend character varying(100))" );
			stmt.execute("INSERT INTO g SELECT * FROM " + baseTable);	
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void createQuaternaryTable(String baseTable) {
		try {
			Statement stmt = this.conn.createStatement();
			stmt.execute("DROP TABLE IF EXISTS g");
			stmt.execute("CREATE TABLE g(compstart character varying(100), " +
			"label1 character varying(45), compend character varying(100), " + 
			"basestart character varying(100), " +
			"label2 character varying(45), baseend character varying(100) )" );
			stmt.execute("INSERT INTO g SELECT *, * FROM " + baseTable);	
		}
		catch (SQLException e) {
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
	

	public String getGraphJSON(String baseTable) {
		String query = "SELECT * FROM " + baseTable;
		String retVal = null;
        try {
        	Statement stmt = this.conn.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	JSONArray jsonArray = new JSONArray();
        	while (rs.next()) {
        		String compstart = rs.getString(1);
            	String label1 = rs.getString(2);
            	String compend = rs.getString(3);
            	jsonArray.put(ternaryTupleToJSON(compstart, label1, compend));
        	}
        	retVal = jsonArray.toString();
        	rs.close();
            stmt.close();
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
        return retVal;
	}
	
	
	public void outputResults(String expr, boolean isFour, boolean useJSON) {
		System.out.println("Closing Query.");
		String query = null;
        if(isFour)     
        	query = "SELECT DISTINCT * FROM g WHERE label1 = '" + expr + "'";
        else
        	query = "SELECT DISTINCT compstart, compend FROM g WHERE label1 = '" + expr + "'";
        int counter = 0;
        PrintWriter output = null;
        try {
        	output =  new PrintWriter(new FileWriter(new File("results.txt")));
        	Statement stmt = this.conn.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	JSONArray jsonArray = new JSONArray();
        	while (rs.next()) {
        		if(isFour) {
        			String compstart = rs.getString(1);
            		String label1 = rs.getString(2);
            		String compend = rs.getString(3);
        			String basestart = rs.getString(4);
            		String label2 = rs.getString(5);
            		String baseend = rs.getString(6);
            		if(useJSON)
            			jsonArray.put( quaternaryTupleToJSON(compstart, label1, compend,
                				basestart, label2, baseend) );
            		else
            			output.println(compstart + "," + label1 + "," + compend + "," + 
            				basestart + "," + label2 + "," + baseend);
        		}
        		else {
        			String compstart = rs.getString(1);
            		String compend = rs.getString(2);
            		if(useJSON)
            			jsonArray.put( binaryTupleToJSON(compstart, compend) );
            		else
            			output.println(compstart + "," + compend);
        		}
        		counter = counter + 1;
        	}
        	if(useJSON)
        		output.println(jsonArray.toString());
        	rs.close();
            output.close();
            stmt.execute("DROP TABLE g");
            stmt.close();
            System.out.println("Number of results: " + counter);
        }
        catch(IOException e) {
        	e.printStackTrace();
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
	}
	
	
	public JSONObject quaternaryTupleToJSON(String compstart, String label1, String compend,
											String basestart, String label2, String baseend) {
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject();
			jsonObj.put("compstart", compstart);
			jsonObj.put("label1", label1);
			jsonObj.put("compend", compend);
			jsonObj.put("basestart", basestart);
			jsonObj.put("label2", label2);
			jsonObj.put("baseend", baseend);
		}
		catch(JSONException je) {
			je.printStackTrace();
		}
		return jsonObj;
	}

	
	public JSONObject binaryTupleToJSON(String compstart, String compend) {
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject();
			jsonObj.put("compstart", compstart);
			jsonObj.put("compend", compend);
		}
		catch(JSONException je) {
			je.printStackTrace();
		}
		return jsonObj;
	}
	
	
	public JSONObject ternaryTupleToJSON(String compstart, String label1, String compend) {
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject();
			jsonObj.put("compstart", compstart);
			jsonObj.put("label1", label1);
			jsonObj.put("compend", compend);
		}
		catch(JSONException je) {
			je.printStackTrace();
		}
		return jsonObj;
	}
	
	
}


