package org.dataone.daks.pbase;

import java.util.ArrayList;
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
		coverIdx.createNodeNameList();
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
	
	
	public void shutdownDB() {
		this.graphDB.shutdown();
	}
	
	
}



