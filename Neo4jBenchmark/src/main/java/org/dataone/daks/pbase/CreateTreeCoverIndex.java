package org.dataone.daks.pbase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator; 
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.kernel.impl.util.StringLogger;
import org.neo4j.cypher.javacompat.ExecutionResult;

import org.dataone.daks.treecover.*;


public class CreateTreeCoverIndex {

	
	private GraphDatabaseService graphDB;
	
	
	public CreateTreeCoverIndex() {
		
	}
	
	
	public CreateTreeCoverIndex(String dbFile) {
		this();
		GraphDatabaseFactory factory = new GraphDatabaseFactory();
		GraphDatabaseBuilder builder = factory.newEmbeddedDatabaseBuilder(dbFile);
		builder.setConfig(GraphDatabaseSettings.read_only, "false");
		GraphDatabaseService graphDB = builder.newGraphDatabase();
		this.graphDB = graphDB;	
	}
	
	
	public static void main(String[] args) {
		CreateTreeCoverIndex coverIdx = new CreateTreeCoverIndex(args[0]);
		Digraph g = coverIdx.createDigraph();
		TreeCover cover = new TreeCover();
		cover.createCover(g);
		List<String> nodeNames = coverIdx.createNodeNameList();
		System.out.println("Adding codes: ");
		coverIdx.addCodes(nodeNames, cover);
		System.out.println();
		System.out.println("Node properties: ");
		//coverIdx.printNodes(nodeNames);
		System.out.println();
		coverIdx.shutdownDB();
	}
	
	
	private Digraph createDigraph() {
		Digraph g = new Digraph();
		ExecutionEngine engine = new ExecutionEngine(graphDB, StringLogger.SYSTEM);
		String query = "START n=node(*) " +
					   "MATCH n-[r?]->m " +
					   "WHERE HAS(n.name) AND HAS(m.name) " +
					   "RETURN distinct n.name, m.name; ";
		ExecutionResult result = engine.execute(query);
		ResourceIterator<Map<String,Object>> it = result.iterator();
		while (it.hasNext()) {
			Map<String,Object> map = it.next();
			//System.out.println(map.get("n.name") + " --> " + map.get("m.name"));
			g.addEdge(map.get("n.name").toString(), map.get("m.name").toString());
		}
		return g;
	}
	
	
	private List<String> createNodeNameList() {
		ArrayList<String> list = new ArrayList<String>();
		ExecutionEngine engine = new ExecutionEngine(graphDB, StringLogger.SYSTEM);
		String query = "START n=node(*) WHERE HAS(n.name) RETURN distinct n;";
		ExecutionResult result = engine.execute(query);
		ResourceIterator<Node> it = result.columnAs("n");
		while (it.hasNext()) {
			Node reachable = it.next();
			String name = reachable.getProperty("name").toString();
			list.add(name);
			//System.out.println(name);
		}
		return list;
	}
	
	
	//Obsolete method, kept to illustrate the use of parameters
	/*
	private void addCodes(List<String> nodeNames, TreeCover cover) {
		ExecutionEngine engine = new ExecutionEngine(graphDB, StringLogger.SYSTEM);
		for(String nodeName: nodeNames) {
			String query = "START n=node:node_auto_index(name='" + nodeName + "') SET n={idx} RETURN n; ";
			Map<String, Object> idxMap = new HashMap<String, Object>();
			idxMap.put( "postorder", cover.getPostorder(nodeName));
			idxMap.put( "code", cover.getCode(nodeName).toString());
			Map<String, Object> idxParam = new HashMap<String, Object>();
			idxParam.put( "idx", idxMap);
			ExecutionResult result = engine.execute(query, idxParam);
			ResourceIterator<Map<String, Object>> it = result.iterator();
			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				System.out.println(map.get("n"));
			}
		}
	}
	*/
	
	
	private void addCodes(List<String> nodeNames, TreeCover cover) {
		ExecutionEngine engine = new ExecutionEngine(graphDB, StringLogger.SYSTEM);
		for(String nodeName: nodeNames) {
			String query = "START n=node:node_auto_index(name='" + nodeName + "') SET n.postorder=" +
					       cover.getPostorder(nodeName) + " , n.code='" + cover.getCode(nodeName).toString() + 
					       "' RETURN n; ";
			ExecutionResult result = engine.execute(query);
			ResourceIterator<Map<String, Object>> it = result.iterator();
			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				//System.out.println(map.get("n"));
			}
		}
	}
	
	
	private void printNodes(List<String> nodeNames) {
		ExecutionEngine engine = new ExecutionEngine(graphDB, StringLogger.SYSTEM);
		String query = "START n=node:node_auto_index(name={name1}) RETURN n; "; 
		for(String nodeName: nodeNames) {
			Map<String, Object> nameMap = new HashMap<String, Object>();
			nameMap.put( "name1", nodeName);
			ExecutionResult result = engine.execute(query, nameMap);
			ResourceIterator<Map<String, Object>> it = result.iterator();
			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				StringBuilder builder = new StringBuilder();
				for (String key : map.keySet() ) {
					Node node = (Node)map.get(key);
					for(String propKey: node.getPropertyKeys())
						builder.append(propKey + ":" + node.getProperty(propKey) + ", ");
					builder.delete(builder.toString().length()-2, builder.toString().length());
					System.out.println(builder.toString());
				}
			}
		}
	}
	
	
	public void shutdownDB() {
		this.graphDB.shutdown();
	}
	
	
}



