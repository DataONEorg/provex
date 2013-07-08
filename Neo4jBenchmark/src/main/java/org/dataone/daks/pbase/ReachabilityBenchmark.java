package org.dataone.daks.pbase;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.neo4j.cypher.*;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.traversal.*;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.kernel.*;
import org.neo4j.kernel.impl.util.StringLogger;


public class ReachabilityBenchmark {
	
	private GraphDatabaseService graphDB;
	private BufferedWriter bw;
	
	
	ReachabilityBenchmark(){
		this.bw = Util.openWriteFile("benchmarkStats");
	}
	
	
	public ReachabilityBenchmark(EmbeddedReadOnlyGraphDatabase graphDB) {
		this();
		this.graphDB = graphDB;
	}
	
	
	public void reachabilityBenchmark(int numQueries) {
		
		long startTime;
		long endTime;

		System.out.println("Initial test with cold caches");
		
		startTime = System.currentTimeMillis();
		this.reachabilityCypher(numQueries);
		endTime = System.currentTimeMillis();
		System.out.println("Cypher: " + numQueries + "\ttime: " + ( endTime - startTime ));

		System.out.println("Second test with warm caches");
		
		long aggregatedCypherTime = 0;
		long tmp = 0;
		int resCnt = 0;
		
		for (int i = 0 ; i < 10; i++) {
			startTime = System.currentTimeMillis();
			resCnt = this.reachabilityCypher(numQueries);
			endTime = System.currentTimeMillis();
			tmp = (endTime-startTime);
			aggregatedCypherTime += tmp;
			System.out.println("Cypher: " + numQueries + "\ttime: " + tmp + "\t resCnt" + resCnt);
		}
		
		System.out.println("Cypher queries: " + aggregatedCypherTime);
		
		try {
			bw.write(numQueries + "\t" + aggregatedCypherTime + "\n");
			bw.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private int reachabilityCypher(int numQueries) {
		int qCnt = 0;
		int resCnt = 0;
		ExecutionEngine engine = new ExecutionEngine(graphDB, StringLogger.SYSTEM);
		for ( Node author:graphDB.getAllNodes() ) {
			if (!author.hasProperty("name"))continue;
			if (++qCnt>numQueries)break;
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put( "node", author );
			//ExecutionResult result = engine.execute( "start n=node({node}) return n.name", params );
			
			//String query = "START author=node({node}) MATCH author-[:"+DBRelationshipTypes.WRITTEN_BY.name()+"]-()-[:"+DBRelationshipTypes.WRITTEN_BY.name()+"]- coAuthor RETURN coAuthor";  
			String query = null;
			ExecutionResult result = engine.execute( query, params);
			scala.collection.Iterator<Node> it = result.columnAs("coAuthor");
			while (it.hasNext()){
				Node coAuthor = it.next();
				resCnt++;
			}
		}
		return resCnt;
	}	
	
	
}




