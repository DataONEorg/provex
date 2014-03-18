package org.dataone.daks.pbaserdf.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;

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
	            edgeObj.put("edgeLabel", "");
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
	
    
	/**
	 * Returns a String representation of a JSON array containing the runIDs of the workflows in the database.
	 */
	public String getRunIDs(String wfID) {
		String sparqlQueryString = "PREFIX provone: <http://purl.org/provone/ontology#> \n" +
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        		"PREFIX dc: <http://purl.org/dc/terms/> \n" +
        		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        		"PREFIX prov: <http://www.w3.org/ns/prov#> \n" +
				"SELECT ?v WHERE {  " + 
        		"?pexec dc:identifier ?v . " +
        		"?pexec prov:wasAssociatedWith ?wf . " +
        		"?wf rdf:type provone:Workflow . " +
        		"?wf dc:identifier " + "\"" + wfID + "\"^^xsd:string . " +
        		"}";
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
	
	
	public void getProcessExecNodes(String wfID, String runID, Hashtable<String, JSONObject> nodesHT) 
			throws JSONException {
		String sparqlQueryString = "PREFIX provone: <http://purl.org/provone/ontology#> \n" +
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        		"PREFIX dc: <http://purl.org/dc/terms/> \n" +
        		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        		"PREFIX prov: <http://www.w3.org/ns/prov#> \n" +
				"SELECT ?id WHERE {  " + 
        		"?wfpexec prov:wasAssociatedWith ?wf . " +
        		"?wf rdf:type provone:Workflow . " +
        		"?wf dc:identifier " + "\"" + wfID + "\"^^xsd:string . " +
        		"?wfpexec dc:identifier " + "\"" + runID + "\"^^xsd:string . " +
        		"?pexec dc:isPartOf ?wfpexec . " +
        		"?pexec dc:identifier ?id . " +
        		"}";
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, this.ds);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            JSONObject jsonObj = new JSONObject();
            String id = soln.getLiteral("id").getString();
            jsonObj.put("nodeId", id);
            jsonObj.put("type", "activity");
            nodesHT.put(id, jsonObj);
        }
        qexec.close();
	}
	
	
	public void getUsedDataNodes(String wfID, String runID, Hashtable<String, JSONObject> nodesHT) 
			throws JSONException {
		String sparqlQueryString = "PREFIX provone: <http://purl.org/provone/ontology#> \n" +
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        		"PREFIX dc: <http://purl.org/dc/terms/> \n" +
        		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        		"PREFIX prov: <http://www.w3.org/ns/prov#> \n" +
				"SELECT ?id WHERE {  " + 
        		"?wfpexec prov:wasAssociatedWith ?wf . " +
        		"?wf rdf:type provone:Workflow . " +
        		"?wf dc:identifier " + "\"" + wfID + "\"^^xsd:string . " +
        		"?wfpexec dc:identifier " + "\"" + runID + "\"^^xsd:string . " +
        		"?pexec dc:isPartOf ?wfpexec . " +
        		"?data rdf:type provone:Data . " +
        		"?pexec prov:used ?data . " +
        		"?data dc:identifier ?id . " +
        		"}";
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, this.ds);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            JSONObject jsonObj = new JSONObject();
            String id = soln.getLiteral("id").getString();
            jsonObj.put("nodeId", id);
            jsonObj.put("type", "data");
            nodesHT.put(id, jsonObj);
        }
        qexec.close();
	}
	
	
	public void getWasGenByDataNodes(String wfID, String runID, Hashtable<String, JSONObject> nodesHT)
			throws JSONException {
		String sparqlQueryString = "PREFIX provone: <http://purl.org/provone/ontology#> \n" +
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        		"PREFIX dc: <http://purl.org/dc/terms/> \n" +
        		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        		"PREFIX prov: <http://www.w3.org/ns/prov#> \n" +
				"SELECT ?id WHERE {  " + 
        		"?wfpexec prov:wasAssociatedWith ?wf . " +
        		"?wf rdf:type provone:Workflow . " +
        		"?wf dc:identifier " + "\"" + wfID + "\"^^xsd:string . " +
        		"?wfpexec dc:identifier " + "\"" + runID + "\"^^xsd:string . " +
        		"?pexec dc:isPartOf ?wfpexec . " +
        		"?data rdf:type provone:Data . " +
        		"?data prov:wasGeneratedBy ?pexec . " +
        		"?data dc:identifier ?id . " +
        		"}";
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, this.ds);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            JSONObject jsonObj = new JSONObject();
            String id = soln.getLiteral("id").getString();
            jsonObj.put("nodeId", id);
            jsonObj.put("type", "data");
            nodesHT.put(id, jsonObj);
        }
        qexec.close();
	}
	
	
	public List<JSONObject> getWasGenByEdges(String wfID, String runID) throws JSONException {
		String sparqlQueryString = "PREFIX provone: <http://purl.org/provone/ontology#> \n" +
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        		"PREFIX dc: <http://purl.org/dc/terms/> \n" +
        		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        		"PREFIX prov: <http://www.w3.org/ns/prov#> \n" +
				"SELECT ?data_id ?pexec_id WHERE {  " + 
        		"?wfpexec prov:wasAssociatedWith ?wf . " +
        		"?wf rdf:type provone:Workflow . " +
        		"?wf dc:identifier " + "\"" + wfID + "\"^^xsd:string . " +
        		"?wfpexec dc:identifier " + "\"" + runID + "\"^^xsd:string . " +
        		"?pexec dc:isPartOf ?wfpexec . " +
        		"?data rdf:type provone:Data . " +
        		"?data prov:wasGeneratedBy ?pexec . " +
        		"?data dc:identifier ?data_id . " +
        		"?pexec dc:identifier ?pexec_id . " +
        		"}";
		List<JSONObject> edgesList = new ArrayList<JSONObject>();
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, this.ds);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            JSONObject jsonObj = new JSONObject();
            String dataId = soln.getLiteral("data_id").getString();
            String pexecId = soln.getLiteral("pexec_id").getString();
            String label = "wasGenBy";
            jsonObj.put("startNodeId", dataId);
            jsonObj.put("endNodeId", pexecId);
            jsonObj.put("edgeLabel", label);
            edgesList.add(jsonObj);
        }
        qexec.close();
		return edgesList;
	}
	
	
	public List<JSONObject> getUsedEdges(String wfID, String runID) throws JSONException {
		String sparqlQueryString = "PREFIX provone: <http://purl.org/provone/ontology#> \n" +
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        		"PREFIX dc: <http://purl.org/dc/terms/> \n" +
        		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        		"PREFIX prov: <http://www.w3.org/ns/prov#> \n" +
				"SELECT ?data_id ?pexec_id WHERE {  " + 
        		"?wfpexec prov:wasAssociatedWith ?wf . " +
        		"?wf rdf:type provone:Workflow . " +
        		"?wf dc:identifier " + "\"" + wfID + "\"^^xsd:string . " +
        		"?wfpexec dc:identifier " + "\"" + runID + "\"^^xsd:string . " +
        		"?pexec dc:isPartOf ?wfpexec . " +
        		"?data rdf:type provone:Data . " +
        		"?pexec prov:used ?data . " +
        		"?data dc:identifier ?data_id . " +
        		"?pexec dc:identifier ?pexec_id . " +
        		"}";
		List<JSONObject> edgesList = new ArrayList<JSONObject>();
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, this.ds);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            JSONObject jsonObj = new JSONObject();
            String dataId = soln.getLiteral("data_id").getString();
            String pexecId = soln.getLiteral("pexec_id").getString();
            String label = "used";
            jsonObj.put("startNodeId", pexecId);
            jsonObj.put("endNodeId", dataId);
            jsonObj.put("edgeLabel", label);
            edgesList.add(jsonObj);
        }
        qexec.close();
		return edgesList;
	}
	
	
	public String getTrace(String wfID, String runID) throws JSONException {
		JSONObject resultObj = new JSONObject();
		//Get the data nodes
		Hashtable<String, JSONObject> nodesHT = new Hashtable<String, JSONObject>();
		this.getUsedDataNodes(wfID, runID, nodesHT);
		this.getWasGenByDataNodes(wfID, runID, nodesHT);
		//Get the activity nodes
		this.getProcessExecNodes(wfID, runID, nodesHT);
		//Get the edges
		List<JSONObject> usedEdges = getUsedEdges(wfID, runID);
		List<JSONObject> wasGenByEdges = getWasGenByEdges(wfID, runID);
		JSONArray edgesArray = new JSONArray();
		Digraph digraph = new Digraph();
		Digraph coverDigraph = new Digraph();
		for(JSONObject usedEdge: usedEdges) {
			edgesArray.put(usedEdge);
			digraph.addEdge(usedEdge.getString("startNodeId"), usedEdge.getString("endNodeId"));
			coverDigraph.addEdge(usedEdge.getString("startNodeId"), usedEdge.getString("endNodeId"));
		}
		for(JSONObject wasGenByEdge: wasGenByEdges) {
			edgesArray.put(wasGenByEdge);
			digraph.addEdge(wasGenByEdge.getString("startNodeId"), wasGenByEdge.getString("endNodeId"));
			coverDigraph.addEdge(wasGenByEdge.getString("startNodeId"), wasGenByEdge.getString("endNodeId"));
		}
		resultObj.put("edges", edgesArray);
		JSONArray nodesArray = new JSONArray();
		TreeCover cover = new TreeCover();
		cover.createCover(coverDigraph);
		List<String> revTopSortList = digraph.reverseTopSort();
		for(String nodeStr : revTopSortList) {
			JSONObject nodeObj = nodesHT.get(nodeStr);
			nodeObj.put("intervals", "[" + cover.getCode(nodeStr).toString() + "]");
			nodeObj.put("postorder", cover.getPostorder(nodeStr));
			nodesArray.put(nodeObj);
		}
		resultObj.put("nodes", nodesArray);
        return resultObj.toString();
	}
	
	
}




