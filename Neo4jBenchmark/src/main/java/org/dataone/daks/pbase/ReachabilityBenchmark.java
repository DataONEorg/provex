package org.dataone.daks.pbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.neo4j.cypher.*;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.kernel.impl.util.StringLogger;


public class ReachabilityBenchmark {
	
	private GraphDatabaseService graphDB;
	private BufferedWriter bw;
	private List<String> nodeNamesList;
	private int[] fromPosList;
	private int[] toPosList;
	
	
	ReachabilityBenchmark() {
		this.bw = Util.openWriteFile("benchmarkResults.txt");
	}
	
	
	public ReachabilityBenchmark(GraphDatabaseService graphDB) {
		this();
		this.graphDB = graphDB;
	}
	
	
	public ReachabilityBenchmark(String dbFile) {
		this();
		GraphDatabaseFactory factory = new GraphDatabaseFactory();
		GraphDatabaseBuilder builder = factory.newEmbeddedDatabaseBuilder(dbFile);
		builder.setConfig(GraphDatabaseSettings.read_only, "true");
		GraphDatabaseService graphDB = builder.newGraphDatabase();
		this.graphDB = graphDB;
	}
	
	
	public void runFromScratch(int benchmarkReps, int queryReps, int testCases) {
		System.out.println("Creating test cases");
		this.createNodeNameList();
		this.createTestCases(testCases);
		this.printTestCases();
		this.reachabilityBenchmark(benchmarkReps, queryReps);
	}
	
	
	public void runFromfile(String benchmarkFile, int benchmarkReps, int queryReps) {
		System.out.println("Reading test cases");
		this.createNodeNameList();
		this.readTestCasesFromFile(benchmarkFile);
		this.printTestCases();
		this.reachabilityBenchmark(benchmarkReps, queryReps);
	}
	
	
	public void createBenchmarkFile(String benchmarkFile, int testCases) {
		System.out.println("Creating test cases file");
		this.createNodeNameList();
		this.createTestCases(testCases);
		this.printTestCasesToFile(benchmarkFile);
		this.readTestCasesFromFile(benchmarkFile);
	}
	
	
	public void reachabilityBenchmark(int benchmarkReps, int queryReps) {
		
		long startTime;
		long endTime;
		
		System.out.println("Initial test with cold caches");
		
		startTime = System.currentTimeMillis();
		this.reachabilityCypher(queryReps);
		endTime = System.currentTimeMillis();
		System.out.println("Evaluated queries " + queryReps + " times, total time: " + (endTime-startTime)/1000.0);

		System.out.println("Second test with warm caches");
		
		long aggregatedCypherTime = 0;
		long tmp = 0;
		
		for (int i = 0 ; i < benchmarkReps; i++) {
			startTime = System.currentTimeMillis();
			this.reachabilityCypher(queryReps);
			endTime = System.currentTimeMillis();
			tmp = (endTime-startTime);
			aggregatedCypherTime += tmp;
			System.out.println("Evaluated queries " + queryReps + " times, total time: " + tmp/1000.0);  
		}
		
		System.out.println("Aggregated time: " + aggregatedCypherTime/1000.0 + "\n");
		
		try {
			bw.write("Aggregated time: " + aggregatedCypherTime/1000.0 + "\n");
			bw.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/*
	private int reachabilityCypher(int numQueries) {
		int qCnt = 0;
		int resCnt = 0;
		ExecutionEngine engine = new ExecutionEngine(graphDB, StringLogger.SYSTEM);
		for ( int i = 0; i < numQueries; i++ ) {
			
			//String query = "START n=node:node_auto_index(name='e34') " +
			//			   "MATCH n-[*]->m " +
			//			   "WHERE m.name='e36' " +
			//			   "RETURN distinct m; "; 
			String query = "START n=node:node_auto_index(name={name1}) " +
					   "MATCH n-[*]->m " +
					   "WHERE m.name={name2} " +
					   "RETURN distinct m; "; 
			//query = "START m=node(*) WHERE HAS(m.name) RETURN distinct m;";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put( "name1", "e34" );
			params.put( "name2", "e36" );
			ExecutionResult result = engine.execute(query, params);
			//ExecutionResult result = engine.execute(query);
			scala.collection.Iterator<Node> it = result.columnAs("m");
			while (it.hasNext()) {
				Node reachable = it.next();
				System.out.println(reachable.getId());
				//for (String propertyKey : reachable.getPropertyKeys()) {
		        //    System.out.println("\t" + propertyKey + " : " +
		        //       reachable.getProperty(propertyKey));
		        //}
				resCnt++;
			}
		}
		System.out.println("resCnt: " + resCnt);
		return resCnt;
	}
	*/
	
	
	private void reachabilityCypher(int queryReps) {
		int resultCount = 0;
		ExecutionEngine engine = new ExecutionEngine(graphDB, StringLogger.SYSTEM);
		for(int i = 0; i < this.fromPosList.length; i++) {
			String query = "START n=node:node_auto_index(name={name1}) " +
					"MATCH n-[*]->m " +
					"WHERE m.name={name2} " +
					"RETURN distinct m; "; 
			Map<String, Object> params = new HashMap<String, Object>();
			params.put( "name1", this.nodeNamesList.get(this.fromPosList[i]) );
			params.put( "name2", this.nodeNamesList.get(this.toPosList[i]) );
			for(int j = 0; j < queryReps; j++) {
				ExecutionResult result = engine.execute(query, params);
				scala.collection.Iterator<Node> it = result.columnAs("m");
				while (it.hasNext()) {
					Node reachable = it.next();
					resultCount++;
				}
				//System.out.println("query " + j + " number of results : " + resultCount);
				resultCount = 0;
			}
		}
	}
	
	
	private void createNodeNameList() {
		ArrayList<String> list = new ArrayList<String>();
		ExecutionEngine engine = new ExecutionEngine(graphDB, StringLogger.SYSTEM);
		String query = "START n=node(*) WHERE HAS(n.name) RETURN distinct n;";
		ExecutionResult result = engine.execute(query);
		scala.collection.Iterator<Node> it = result.columnAs("n");
		while (it.hasNext()) {
			Node reachable = it.next();
			String name = reachable.getProperty("name").toString();
			list.add(name);
		}
		this.nodeNamesList = list;
	}
	
	
	public void createTestCases(int nCases) {
		Random randomGenerator = new Random();
		this.fromPosList = new int[nCases];
		this.toPosList = new int[nCases];
		int listSize = this.nodeNamesList.size();
		for(int i = 0; i < nCases; i++) {
			int fromPos =  randomGenerator.nextInt(listSize);
			int toPos =  randomGenerator.nextInt(listSize);
			this.fromPosList[i] = fromPos;
			this.toPosList[i] = toPos;
		}
	}
	
	
	public void printTestCases() {
		for(int i = 0; i < this.fromPosList.length; i++) {
			System.out.println(this.nodeNamesList.get(this.fromPosList[i]) + " -> " + 
					this.nodeNamesList.get(this.toPosList[i]) );
		}
	}
	
	
	public HashMap<String, Integer> createNodeNamePosHT() {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for(int i = 0; i < this.nodeNamesList.size(); i++) {
			map.put(this.nodeNamesList.get(i), i);
		}
		return map;
	}
	
	
	public void printTestCasesToFile(String fileName) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(fileName));
			for(int i = 0; i < this.fromPosList.length; i++) {
				pw.println(this.nodeNamesList.get(this.fromPosList[i]) + " -> " + 
						this.nodeNamesList.get(this.toPosList[i]) );
				pw.flush();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			pw.close();
		}
	}
	
	
	public void readTestCasesFromFile(String benchmarkFile) {
		String line = null;
		BufferedReader br;
		HashMap<String, Integer> map = this.createNodeNamePosHT();
		List<Integer> fromPosArrayList = new ArrayList<Integer>();
		List<Integer> toPosArrayList = new ArrayList<Integer>();
		try {
			br = Util.openReadFile(benchmarkFile);
			while( (line = br.readLine()) != null ) {
				line = line.trim();
				if( line.length() == 0 )
					continue;
				else if( (! line.contains("->")) )
					continue;
				else {
					String nodeId1 = line.substring(0, line.indexOf("->")).trim();
					String nodeId2 = line.substring(line.indexOf("->")+2, line.length()).trim();
					//System.out.println(line + " | " + nodeId1 + " " + nodeId2);
					fromPosArrayList.add(map.get(nodeId1));
					toPosArrayList.add(map.get(nodeId2));
				}
			}
			this.fromPosList = new int[fromPosArrayList.size()];
			this.toPosList = new int[toPosArrayList.size()];
			for(int i = 0; i < fromPosArrayList.size(); i++) {
				this.fromPosList[i] = fromPosArrayList.get(i);
				this.toPosList[i] = toPosArrayList.get(i);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void shutdownDB() {
		this.graphDB.shutdown();
	}
	
	
}




