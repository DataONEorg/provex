package org.dataone.daks.pbase.neo4j;

import java.util.Map;
import java.util.Hashtable;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.kernel.impl.util.StringLogger;
import org.neo4j.cypher.javacompat.*;
import org.json.*;


public class GraphDAO {
	
	
	private GraphDatabaseService graphDB;
	
	private static final GraphDAO instance = new GraphDAO();
	
	
	public GraphDAO() {

	}
	
	
	public static GraphDAO getInstance() {
    	return instance;
    }
	
	
	public void init(String dbFile) {
		if( this.graphDB == null ) {
			GraphDatabaseFactory factory = new GraphDatabaseFactory();
			GraphDatabaseBuilder builder = factory.newEmbeddedDatabaseBuilder(dbFile);
			builder.setConfig(GraphDatabaseSettings.read_only, "true");
			GraphDatabaseService graphDB = builder.newGraphDatabase();
			this.graphDB = graphDB;
		}
	}
	
	
	public void shutdownDB() {
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
		JSONArray nodesArray = new JSONArray();
		Hashtable<String, Boolean> nodesHT = new Hashtable<String, Boolean>();
		while (dataNodesIt.hasNext()) {
			Node node = dataNodesIt.next();
			JSONObject nodeObj = new JSONObject();
			nodeObj.put("nodeId", node.getProperty("name"));
			nodesArray.put(nodeObj);
			nodesHT.put(node.getProperty("name").toString(), new Boolean(true));
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
			nodesArray.put(nodeObj);
			nodesHT.put(node.getProperty("name").toString(), new Boolean(true));
		}
		resultObj.put("nodes", nodesArray);
		//Get the edges
		String edgesQuery = "START n=node(*) " +
							"MATCH m-[r:used|wasGeneratedBy]->n " +
							"WHERE HAS(m.name) AND HAS(n.name) " +
							"RETURN distinct m.name, r, n.name;";
		ExecutionResult edgesResult = engine.execute(edgesQuery);
		ResourceIterator<Map<String,Object>> edgesIt = edgesResult.iterator();
		JSONArray edgesArray = new JSONArray();
		while (edgesIt.hasNext()) {
			Map<String,Object> map = edgesIt.next();
			JSONObject edgeObj = new JSONObject();
			edgeObj.put("startNodeId", map.get("m.name").toString());
			edgeObj.put("edgeLabel", ((Relationship)map.get("r")).getType().name().toString());
			edgeObj.put("endNodeId", map.get("n.name").toString());
			if( nodesHT.get(map.get("m.name").toString()) != null 
					&& nodesHT.get(map.get("m.name").toString()) != null )
				edgesArray.put(edgeObj);
		}
		resultObj.put("edges", edgesArray);
        return resultObj.toString();
	}
	
	
}




