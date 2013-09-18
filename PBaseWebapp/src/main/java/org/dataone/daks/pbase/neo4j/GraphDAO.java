package org.dataone.daks.pbase.neo4j;

import org.neo4j.cypher.*;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.kernel.impl.util.StringLogger;


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




