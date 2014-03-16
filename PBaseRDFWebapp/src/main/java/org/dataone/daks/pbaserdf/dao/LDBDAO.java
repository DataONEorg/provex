package org.dataone.daks.pbaserdf.dao;

import com.hp.hpl.jena.query.Dataset ;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.tdb.TDBFactory;

import org.json.*;
import org.dataone.daks.pbase.treecover.*;


public class LDBDAO {
	
	
	private Dataset ds;
	
	private String directory;
	
	private Model model;
	
	private static final LDBDAO instance = new LDBDAO();
	
	
	public LDBDAO() {

	}
	
	
	public static LDBDAO getInstance() {
    	return instance;
    }
	
	
	public synchronized void init(String directory) {
		if( this.ds == null || ( this.directory != null && ! this.directory.equals(this.directory) ) ) {
			if( this.ds != null )
				this.ds.close();
			this.directory = directory;
			Dataset ds = TDBFactory.createDataset(this.directory);
			this.ds = ds;
			this.directory = directory;
			this.model = this.ds.getDefaultModel();
		}
	}
	
	
	public synchronized void shutdown() {
		this.model.close();
		this.ds.close();
		this.ds = null;
		this.model = null;
	}
	
	
	public void addModel(Model m) {
		this.model.add(m);
	}
	
	
	/**
	 * Returns a String representation of a JSON array containing the wfIDs of the workflows in the database.
	 */
	public String getWfIDs() {
		String sparqlQueryString = "SELECT ?v WHERE {  ?s " +
        		"<http://purl.org/dc/terms/identifier> ?v . " +
        		"?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> " +
        		"<http://purl.org/provone/ontology#Workflow> .}";
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, this.ds);
        ResultSet results = qexec.execSelect();
        JSONArray array = new JSONArray();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            String id = soln.getLiteral("v").getString();
            array.put(id);
        }
        qexec.close();
		return array.toString();
	}
	
	
    public JSONArray getProcesses(String wfID) {
    	String sparqlQueryString = "PREFIX provone: <http://purl.org/provone/ontology#> \n" +
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        		"PREFIX dc: <http://purl.org/dc/terms/> \n" +
        		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        		"SELECT ?i ?t WHERE {  ?process dc:identifier ?i . " +
        		"?process dc:title ?t . " +
        		"?process rdf:type provone:Process . " + 
        		"?wf rdf:type provone:Workflow . " +
        		"?wf dc:identifier " + "\"" + wfID + "\"^^xsd:string . " +
        		"?wf provone:hasSubProcess ?process . }";
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, this.ds);
        ResultSet results = qexec.execSelect();
        JSONArray nodesArray = new JSONArray();
        try {
        	for ( ; results.hasNext() ; ) {
        		JSONObject nodeObj = new JSONObject();
        		QuerySolution soln = results.nextSolution();
        		String id = soln.getLiteral("i").getString();
				nodeObj.put("nodeId", id);
				String title = soln.getLiteral("t").getString();
	            nodeObj.put("title", title);
	            nodesArray.put(nodeObj);
			}
        }
        catch (JSONException e) {
			e.printStackTrace();
		}
        qexec.close();
        return nodesArray;
    }
    
    
    public JSONArray getDataLinks(String wfID) {
    	String sparqlQueryString = "PREFIX provone: <http://purl.org/provone/ontology#> \n" +
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        		"PREFIX dc: <http://purl.org/dc/terms/> \n" +
        		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        		"SELECT ?op_process_id ?ip_process_id WHERE {  ?dl rdf:type provone:DataLink . " +
        		"?dl provone:outPortToDL ?op . " +
        		"?dl provone:DLToInPort ?ip . " +
        		"?op_process provone:hasOutputPort ?op . " + 
        		"?ip_process provone:hasInputPort ?ip . " + 
        		"?wf rdf:type provone:Workflow . " +
        		"?wf dc:identifier " + "\"" + wfID + "\"^^xsd:string . " +
        		"?wf provone:hasSubProcess ?op_process . " + 
        		"?wf provone:hasSubProcess ?ip_process . " +
        		"?op_process dc:identifier ?op_process_id . " +
        		"?ip_process dc:identifier ?ip_process_id . } ";
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, this.ds);
        ResultSet results = qexec.execSelect();
        JSONArray edgesArray = new JSONArray();
        try {
        	for ( ; results.hasNext() ; ) {
        		JSONObject edgeObj = new JSONObject();
        		QuerySolution soln = results.nextSolution();
        		String start = soln.getLiteral("op_process_id").getString();
				edgeObj.put("startNodeId", start);
				String end = soln.getLiteral("ip_process_id").getString();
	            edgeObj.put("endNodeId", end);
	            edgesArray.put(edgeObj);
			}
        }
        catch (JSONException e) {
			e.printStackTrace();
		}
        qexec.close();
        return edgesArray;
    }
    
    
    public String getWorkflow(String wfID) {
    	JSONArray processesArray = this.getProcesses(wfID);
    	JSONArray edgesArray = this.getDataLinks(wfID);
    	JSONObject jsonObj = new JSONObject();
    	try {
			jsonObj.put("nodes", processesArray);
			jsonObj.put("edges", edgesArray);
		}
    	catch (JSONException e) {
			e.printStackTrace();
		}
    	return jsonObj.toString();
    }
    
    
    public String getWorkflowReachEncoding(String wfID) {
    	JSONArray nodesArray = this.getProcesses(wfID);
    	JSONArray edgesArray = this.getDataLinks(wfID);
    	JSONObject jsonObj = new JSONObject();
    	try {
    		Digraph coverDigraph = new Digraph();
        	for( int i = 0; i < edgesArray.length(); i++ ) {
        		JSONObject edgeObj = edgesArray.getJSONObject(i);
        		coverDigraph.addEdge(edgeObj.getString("startNodeId"), edgeObj.getString("endNodeId"));	
        	}
        	TreeCover cover = new TreeCover();
    		cover.createCover(coverDigraph);
    		for(int i = 0; i < nodesArray.length(); i++) {
    			JSONObject nodeObj = nodesArray.getJSONObject(i);
    			String nodeIdStr = nodeObj.getString("nodeId");
    			nodeObj.put("intervals", "[" + cover.getCode(nodeIdStr).toString() + "]");
    			nodeObj.put("postorder", cover.getPostorder(nodeIdStr));
    		}
			jsonObj.put("nodes", nodesArray);
			jsonObj.put("edges", edgesArray);
		}
    	catch (JSONException e) {
			e.printStackTrace();
		}
    	return jsonObj.toString();
    }
	
	
}




