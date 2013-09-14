package org.dataone.daks.pbase.neo4j;

import org.neo4j.cypher.*;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.kernel.impl.util.StringLogger;


public class GraphDAO {
	
	
	private GraphDatabaseService graphDB;
	
	
	public GraphDAO() {

	}
	
	
	public GraphDAO(GraphDatabaseService graphDB) {
		this();
		this.graphDB = graphDB;
	}
	
	
	public GraphDAO(String dbFile) {
		this();
		GraphDatabaseFactory factory = new GraphDatabaseFactory();
		GraphDatabaseBuilder builder = factory.newEmbeddedDatabaseBuilder(dbFile);
		builder.setConfig(GraphDatabaseSettings.read_only, "true");
		GraphDatabaseService graphDB = builder.newGraphDatabase();
		this.graphDB = graphDB;
	}
	
	
	public void shutdownDB() {
		this.graphDB.shutdown();
	}
	
	
	public String outputGraphNodes() {
		ExecutionEngine engine = new ExecutionEngine(graphDB, StringLogger.SYSTEM); 
		String query = "START m=node(*) WHERE HAS(m.name) RETURN distinct m;";
		ExecutionResult result = engine.execute(query);
		scala.collection.Iterator<Node> it = result.columnAs("m");
		StringBuilder builder = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		while (it.hasNext()) {
			Node node = it.next();
			builder.append(node.getProperty("name") + newLine);
		}
		return builder.toString();
	}
	
	
	
}




