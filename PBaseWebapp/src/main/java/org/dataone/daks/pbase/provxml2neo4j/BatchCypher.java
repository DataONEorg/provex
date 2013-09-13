package org.dataone.daks.pbase.provxml2neo4j;

import java.io.*;

import org.neo4j.cypher.*;
import org.neo4j.graphdb.*;
import org.neo4j.kernel.impl.util.*;
import org.neo4j.graphdb.factory.*;


public class BatchCypher {
	
	private GraphDatabaseService graphDB;
	
	
	BatchCypher() {
	}
	
	
	public BatchCypher(String dbFile, String nodeKeysIndexable) {
		this();
		GraphDatabaseFactory factory = new GraphDatabaseFactory();
		GraphDatabaseBuilder builder = factory.newEmbeddedDatabaseBuilder(dbFile);
		builder.setConfig(GraphDatabaseSettings.node_auto_indexing, "true");
		builder.setConfig(GraphDatabaseSettings.node_keys_indexable, nodeKeysIndexable);
		GraphDatabaseService graphDB = builder.newGraphDatabase();
		this.graphDB = graphDB;
	}
	
	
	public BatchCypher(GraphDatabaseService graphDB) {
		this();
		this.graphDB = graphDB;
	}
	
	
	public void doBatch(String batchFile) {
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		System.out.println("Starting batch file processing.");
		ExecutionEngine engine = new ExecutionEngine(graphDB, StringLogger.SYSTEM);
		String line = null;
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(batchFile));
			while( (line = br.readLine()) != null ) {
				if( line.trim().length() == 0 )
					continue;
				engine.execute(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Finishing batch file processing.");
		endTime = System.currentTimeMillis();
		System.out.println("Total time: " + (endTime-startTime)/1000.0 + " sec." );
	}
	
	
	public void shutdownDB() {
		this.graphDB.shutdown();
	}
	
	
}




