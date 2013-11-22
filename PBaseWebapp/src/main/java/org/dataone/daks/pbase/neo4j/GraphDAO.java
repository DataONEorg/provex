package org.dataone.daks.pbase.neo4j;

import java.util.Map;
import java.util.Hashtable;
import java.util.List;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.kernel.impl.util.StringLogger;
import org.neo4j.cypher.javacompat.*;
import org.json.*;

import org.dataone.daks.pbase.treecover.*;


public class GraphDAO {
	
	
	private GraphDatabaseService graphDB;
	
	private String dbFile;
	
	private static final GraphDAO instance = new GraphDAO();
	
	
	public GraphDAO() {

	}
	
	
	public static GraphDAO getInstance() {
    	return instance;
    }
	
	
	public synchronized void init(String dbFile) {
		if( this.graphDB == null || ( this.dbFile != null && ! this.dbFile.equals(dbFile) ) ) {
			if( this.graphDB != null )
				this.graphDB.shutdown();
			GraphDatabaseFactory factory = new GraphDatabaseFactory();
			GraphDatabaseBuilder builder = factory.newEmbeddedDatabaseBuilder(dbFile);
			builder.setConfig(GraphDatabaseSettings.read_only, "true");
			GraphDatabaseService graphDB = builder.newGraphDatabase();
			this.graphDB = graphDB;
			this.dbFile = dbFile;
		}
	}
	
	
	public synchronized void shutdownDB() {
		this.graphDB.shutdown();
		this.graphDB = null;
	}
	
	
	public String outputGraphNodes() {
		ExecutionEngine engine = new ExecutionEngine(graphDB, StringLogger.SYSTEM); 
		String query = "START m=node(*) WHERE HAS(m.name) RETURN distinct m;";
		ExecutionResult result = engine.execute(query);
		ResourceIterator<Node> it = result.columnAs("m");
		StringBuilder builder = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		while (it.hasNext()) {
			Node node = it.next();
			builder.append(node.getProperty("name") + newLine);
		}
		return builder.toString();
	}
	
	
	public String getWorkflow(String wfID) throws JSONException {
		ExecutionEngine engine = new ExecutionEngine(graphDB, StringLogger.SYSTEM); 
		//Get the modules
		String modulesQuery = "START n=node(*) WHERE HAS(n.name) AND HAS(n.type) AND HAS(n.wfID) " +
					   "AND n.type='module' AND n.wfID='" + wfID + "' " +
					   "RETURN distinct n;";
		ExecutionResult nodesResult = engine.execute(modulesQuery);
		ResourceIterator<Node> nodesIt = nodesResult.columnAs("n");
		JSONObject resultObj = new JSONObject();
		JSONArray nodesArray = new JSONArray();
		while (nodesIt.hasNext()) {
			Node node = nodesIt.next();
			JSONObject nodeObj = new JSONObject();
			nodeObj.put("nodeId", node.getProperty("name"));
			for (String propertyKey : node.getPropertyKeys())
				if( ! (propertyKey.equals("wfID") || propertyKey.equals("name")) ) 
					nodeObj.put(propertyKey, node.getProperty(propertyKey) );
			nodesArray.put(nodeObj);
		}
		resultObj.put("nodes", nodesArray);
		//Get the modules edges
		String edgesQuery = "START n=node(*) " +
							"MATCH m-[:isConnectedWith]->n " +
							"WHERE HAS(m.name) AND HAS(n.name) " +
							"RETURN distinct m.name, n.name;";
		ExecutionResult edgesResult = engine.execute(edgesQuery);
		ResourceIterator<Map<String,Object>> edgesIt = edgesResult.iterator();
		JSONArray edgesArray = new JSONArray();
		while (edgesIt.hasNext()) {
			Map<String,Object> map = edgesIt.next();
			JSONObject edgeObj = new JSONObject();
			edgeObj.put("startNodeId", map.get("m.name").toString());
			edgeObj.put("edgeLabel", "");
			edgeObj.put("endNodeId", map.get("n.name").toString());
			edgesArray.put(edgeObj);
		}
		resultObj.put("edges", edgesArray);
        return resultObj.toString();
	}
	
	
	public String getTrace(String wfID, String runID) throws JSONException {
		ExecutionEngine engine = new ExecutionEngine(graphDB, StringLogger.SYSTEM);
		//Get the data nodes
		String dataNodesQuery = "START n=node(*) WHERE HAS(n.name) AND HAS(n.type) AND HAS(n.wfID) " +
					   "AND n.type='data' AND n.wfID='" + wfID + "' " +
					   "RETURN distinct n;";
		ExecutionResult dataNodesResult = engine.execute(dataNodesQuery);
		ResourceIterator<Node> dataNodesIt = dataNodesResult.columnAs("n");
		JSONObject resultObj = new JSONObject();
		Hashtable<String, JSONObject> nodesHT = new Hashtable<String, JSONObject>();
		while (dataNodesIt.hasNext()) {
			Node node = dataNodesIt.next();
			JSONObject nodeObj = new JSONObject();
			nodeObj.put("nodeId", node.getProperty("name"));
			for (String propertyKey : node.getPropertyKeys())
				if( ! (propertyKey.equals("wfID") || propertyKey.equals("name")) ) 
					nodeObj.put(propertyKey, node.getProperty(propertyKey) );
			nodesHT.put(node.getProperty("name").toString(), nodeObj);
		}
		//Get the activity nodes
		String activityNodesQuery = "START n=node(*) WHERE HAS(n.name) AND HAS(n.type) AND HAS(n.wfID) AND HAS(n.runID) " +
							   "AND n.type='activity' AND n.wfID='" + wfID + "' " + "AND n.runID='" + runID + "' " +
							   "RETURN distinct n;";
		ExecutionResult activityNodesResult = engine.execute(activityNodesQuery);
		ResourceIterator<Node> activityNodesIt = activityNodesResult.columnAs("n");
		while (activityNodesIt.hasNext()) {
			Node node = activityNodesIt.next();
			JSONObject nodeObj = new JSONObject();
			nodeObj.put("nodeId", node.getProperty("name"));
			for (String propertyKey : node.getPropertyKeys())
				if( ! (propertyKey.equals("wfID") || propertyKey.equals("name") || propertyKey.equals("runID")) ) 
					nodeObj.put(propertyKey, node.getProperty(propertyKey) );
			nodesHT.put(node.getProperty("name").toString(), nodeObj);
		}
		//Get the edges
		String edgesQuery = "START n=node(*) " +
							"MATCH m-[r:used|wasGeneratedBy]->n " +
							"WHERE HAS(m.name) AND HAS(n.name) " +
							"RETURN distinct m.name, r, n.name;";
		ExecutionResult edgesResult = engine.execute(edgesQuery);
		ResourceIterator<Map<String,Object>> edgesIt = edgesResult.iterator();
		JSONArray edgesArray = new JSONArray();
		Digraph digraph = new Digraph();
		Digraph coverDigraph = new Digraph();
		while (edgesIt.hasNext()) {
			Map<String,Object> map = edgesIt.next();
			JSONObject edgeObj = new JSONObject();
			edgeObj.put("startNodeId", map.get("m.name").toString());
			edgeObj.put("edgeLabel", ((Relationship)map.get("r")).getType().name().toString());
			edgeObj.put("endNodeId", map.get("n.name").toString());
			if( nodesHT.get(map.get("m.name").toString()) != null 
					&& nodesHT.get(map.get("n.name").toString()) != null ) {
				edgesArray.put(edgeObj);
				digraph.addEdge(map.get("m.name").toString(), map.get("n.name").toString());
				coverDigraph.addEdge(map.get("m.name").toString(), map.get("n.name").toString());
			}
		}
		resultObj.put("edges", edgesArray);
		JSONArray nodesArray = new JSONArray();
		
		TreeCover cover = new TreeCover();
		cover.createCover(coverDigraph);
		
		List<String> revTopSortList = digraph.reverseTopSort();
		for(String nodeStr : revTopSortList) {
			JSONObject nodeObj = nodesHT.get(nodeStr);
			nodeObj.put("treecover", cover.getPostorder(nodeStr) + ":" + cover.getCode(nodeStr).toString());
			nodesArray.put(nodeObj);
		}
		resultObj.put("nodes", nodesArray);
        return resultObj.toString();
	}
	
	
	/**
	 * Returns a String representation of a JSON array containing the runIDs associated with a given workflow.
	 * 
	 * @param wfID
	 * @return
	 */
	public String getRunIDs(String wfID) {
		ExecutionEngine engine = new ExecutionEngine(this.graphDB, StringLogger.SYSTEM); 
		String query = "START n=node(*) WHERE HAS(n.wfID) AND HAS(n.runID) AND HAS(n.type) " +
					   "AND n.wfID='" + wfID + "' " +
					   "RETURN distinct n.runID;";
		ExecutionResult result = engine.execute(query);
		ResourceIterator<Map<String,Object>> it = result.iterator();
		JSONArray array = new JSONArray();
		while (it.hasNext()) {
			Map<String,Object> map = it.next();
			array.put(map.get("n.runID").toString());
		}
		return array.toString();
	}
	
	
	/**
	 * Returns a String representation of a JSON array containing the wfIDs of the workflows in the database.
	 * 
	 * @param wfID
	 * @return
	 */
	public String getWfIDs() {
		ExecutionEngine engine = new ExecutionEngine(this.graphDB, StringLogger.SYSTEM); 
		String query = "START n=node(*) WHERE HAS(n.wfID) AND HAS(n.type) " +
					   "AND n.type='workflow' " +
					   "RETURN distinct n.wfID;";
		ExecutionResult result = engine.execute(query);
		ResourceIterator<Map<String,Object>> it = result.iterator();
		JSONArray array = new JSONArray();
		while (it.hasNext()) {
			Map<String,Object> map = it.next();
			array.put(map.get("n.wfID").toString());
		}
		return array.toString();
	}
	
	
	/**
	 * Execute a cypher query provided as a String.
	 * 
	 * @param query
	 * @return
	 */
	public String executeQuery(String query) {
		ExecutionEngine engine = new ExecutionEngine(this.graphDB, StringLogger.SYSTEM); 
		ExecutionResult result = engine.execute(query);
		String retVal = null;
		try {
			JSONObject jsonResult = new JSONObject();
			JSONArray columnsArray = new JSONArray();
			List<String> columns = result.columns();
			for(String s: columns) {
				JSONObject colVal = new JSONObject();
				colVal.put(s, "string");
				columnsArray.put(colVal);
			}
			JSONArray dataArray = new JSONArray();
			ResourceIterator<Map<String,Object>> it = result.iterator();
			boolean first = true;
			int counter = 0;
			while (it.hasNext()) {
				Map<String,Object> map = it.next();
				JSONArray row = new JSONArray();
				for(String key: columns) {
					Object obj = map.get(key);
					if ( obj instanceof Node ) {
						row.put(this.generateNodeJSON((Node)obj));
						if( first )
							columnsArray.getJSONObject(counter).put(columns.get(counter), "node");
					}
					else
						row.put(obj.toString());
					counter++;
				}
				dataArray.put(row);
				first = false;
			}
			jsonResult.put("columns", columnsArray);
			jsonResult.put("data", dataArray);
			retVal = jsonResult.toString(); 
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	
	public JSONObject generateNodeJSON(Node node) {
		JSONObject nodeObj = new JSONObject();
		try {
			nodeObj.put("nodeId", node.getProperty("name"));
			for (String propertyKey : node.getPropertyKeys())
				if( ! (propertyKey.equals("wfID") || propertyKey.equals("name")) ) 
					nodeObj.put(propertyKey, node.getProperty(propertyKey) );
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		return nodeObj;
	}
	
	
}




