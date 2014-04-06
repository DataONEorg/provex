package org.dataone.daks.pbaserdf.dao;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.tdb.TDBFactory;


public class LDBDAOTest {
	
	
    public static void main(String args[]) {
    	
        // Direct way: Make a TDB-back Jena model in the named directory.
        String directory = "C:\\devp\\apache-tomcat-7.0.50\\bin\\" + args[0];
        Dataset dataset = TDBFactory.createDataset(directory);
        LDBDAOTest test = new LDBDAOTest();
        //test.countQuery(dataset);
        //test.wfIDsQuery(dataset);
        //test.triplesQuery(dataset);
        //test.processesQuery(dataset, "e0");
        //test.wfIDQuery(dataset, "e0");
        //test.getProcessExecNodes(dataset, "spatialtemporal_summary", "a0");
        //test.getUsedDataNodes(dataset, "spatialtemporal_summary", "a0");
        //test.getWasGenByDataNodes(dataset, "spatialtemporal_summary", "a0");
        //test.getWasGenByEdges(dataset, "spatialtemporal_summary", "a0");
        //test.getUsedEdges(dataset, "spatialtemporal_summary", "a0");
        //test.query1(dataset, "spatialtemporal_summary", "e14");
        //test.query2(dataset, "spatialtemporal_summary");
        //test.query3(dataset, "eva", "a0");
        //test.query4(dataset, "eva");
        //test.query5(dataset, "spatialtemporal_summary", "e17");
        test.query6(dataset, "spatialtemporal_summary", "e3");
    }
    
    
    private void countQuery(Dataset dataset) {
    	// Potentially expensive query.
        String sparqlQueryString = "SELECT (count(*) AS ?count) { ?s ?p ?o }";
        // See http://incubator.apache.org/jena/documentation/query/app_api.html
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qexec.execSelect();
        ResultSetFormatter.out(results);
        qexec.close();
        dataset.close();
    }
    
    
    private void wfIDsQuery(Dataset dataset) {
        String sparqlQueryString = "SELECT ?v WHERE {  ?s " +
        		"<http://purl.org/dc/terms/identifier> ?v . " +
        		"?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> " +
        		"<http://purl.org/provone/ontology#Workflow> .}";
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            String id = soln.getLiteral("v").getString();
            System.out.println("wfID = " + id);
        }
        qexec.close();
        dataset.close();
    }
    
    
    private void triplesQuery(Dataset dataset) {
        String sparqlQueryString = "SELECT ?s ?p ?o WHERE { ?s ?p ?o }";
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qexec.execSelect();
        ResultSetFormatter.out(results);
        qexec.close();
        dataset.close();
    }
    
    
    private void processesQuery(Dataset dataset, String wfID) {
    	String sparqlQueryString = "PREFIX provone: <http://purl.org/provone/ontology#> \n" +
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        		"PREFIX dc: <http://purl.org/dc/terms/> \n" +
        		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        		"SELECT ?v WHERE {  ?process dc:identifier ?v . " +
        		"?process rdf:type provone:Process . " + 
        		"?wf rdf:type provone:Workflow . " +
        		"?wf dc:identifier " + "\"" + wfID + "\"^^xsd:string . " +
        		"?wf provone:hasSubProcess ?process . }";
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            String id = soln.getLiteral("v").getString();
            System.out.println("processID = " + id);
        }
        qexec.close();
        dataset.close();
    }
    
    private void wfIDQuery(Dataset dataset, String wfID) {
        String sparqlQueryString = "PREFIX provone: <http://purl.org/provone/ontology#> \n" +
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        		"PREFIX dc: <http://purl.org/dc/terms/> \n" +
        		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        		"SELECT ?t WHERE {  " +
        		"?v rdf:type provone:Workflow . " +
        		"?v dc:identifier " + "\"" + wfID + "\"^^xsd:string . " +
        		"?v dc:title ?t . " +
        		"}";
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            String title = soln.getLiteral("t").getString();
            System.out.println("title = " + title);
        }
        qexec.close();
        dataset.close();
    }
    
    
	public void getProcessExecNodes(Dataset dataset, String wfID, String runID) {
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
        QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            String id = soln.getLiteral("id").getString();
            System.out.println(id);
        }
        qexec.close();
	}
    
	
	public void getUsedDataNodes(Dataset dataset, String wfID, String runID) {
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
        QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            String id = soln.getLiteral("id").getString();
            System.out.println("id: " + id);
        }
        qexec.close();
	}
	
	
	public void getWasGenByDataNodes(Dataset dataset, String wfID, String runID) {
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
        QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            String id = soln.getLiteral("id").getString();
            System.out.println(id);
        }
        qexec.close();
	}
	
	
	public void getWasGenByEdges(Dataset dataset, String wfID, String runID) {
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
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            String dataId = soln.getLiteral("data_id").getString();
            String pexecId = soln.getLiteral("pexec_id").getString();
            String label = "wasGenBy";
            System.out.println(dataId + " -> " + pexecId + " : " + label);
        }
        qexec.close();
	}
	
	
	public void getUsedEdges(Dataset dataset, String wfID, String runID) {
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
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            String dataId = soln.getLiteral("data_id").getString();
            String pexecId = soln.getLiteral("pexec_id").getString();
            String label = "used";
            System.out.println(dataId + " -> " + pexecId + " : " + label);
        }
        qexec.close();
	}
	
	
	//1. Compute the number of invocations of a process
	public void query1(Dataset dataset, String wfID, String processID) {
		System.out.println("Executing query 1");
		String sparqlQueryString = "PREFIX provone: <http://purl.org/provone/ontology#> \n" +
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        		"PREFIX dc: <http://purl.org/dc/terms/> \n" +
        		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        		"PREFIX prov: <http://www.w3.org/ns/prov#> \n" +
				"SELECT (count(?pexec) AS ?c) WHERE {  " + 
        		"?wf rdf:type provone:Workflow . " +
        		"?wf dc:identifier " + "\"" + wfID + "\"^^xsd:string . " +
        		"?wf provone:hasSubProcess ?p . " +
        		"?p dc:identifier " + "\"" + processID + "\"^^xsd:string . " +
        		"?pexec prov:wasAssociatedWith ?p . " +
        		"?pexec dc:identifier ?pexec_id . " +
        		"}";
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            String pexecId = soln.getLiteral("c").getString();
            System.out.println(pexecId);
        }
        qexec.close();
	}
	
	//2. Find all inputs of a workflow across executions
	public void query2(Dataset dataset, String wfID) {
		System.out.println("Executing query 2");
		String sparqlQueryString = "PREFIX provone: <http://purl.org/provone/ontology#> \n" +
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        		"PREFIX dc: <http://purl.org/dc/terms/> \n" +
        		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        		"PREFIX prov: <http://www.w3.org/ns/prov#> \n" +
				"SELECT DISTINCT ?data_id WHERE {  " + 
				"?wf rdf:type provone:Workflow . " +
				"?wf dc:identifier " + "\"" + wfID + "\"^^xsd:string . " +
				"?wf provone:hasSubProcess ?p . " +
				"?pexec prov:wasAssociatedWith ?p . " +
				"?pexec prov:used ?data . " +
				"?data dc:identifier ?data_id . " +
				"FILTER NOT EXISTS { ?data prov:wasGeneratedBy ?other_pexec } " +
        		"} ";
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            String dataId = soln.getLiteral("data_id").getString();
            System.out.println(dataId);
        }
        qexec.close();
	}
	
	
	//3. Find the processes of workflows that were not executed
	public void query3(Dataset dataset, String wfID, String processExecID) {
		System.out.println("Executing query 3");
		String sparqlQueryString = "PREFIX provone: <http://purl.org/provone/ontology#> \n" +
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        		"PREFIX dc: <http://purl.org/dc/terms/> \n" +
        		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        		"PREFIX prov: <http://www.w3.org/ns/prov#> \n" +
        		"PREFIX wfms: <http://www.vistrails.org/registry.xsd> \n" +
				"SELECT DISTINCT ?p_id WHERE {  " + 
        		"?wf rdf:type provone:Workflow . " +
        		"?wf dc:identifier " + "\"" + wfID + "\"^^xsd:string . " +
        		"?wfexec prov:wasAssociatedWith ?wf . " +
        		"?wfexec dc:identifier " + "\"" + processExecID + "\"^^xsd:string . " +
        		"?pexec provone:isPartOf ?wfexec . " +
        		"?pexec prov:wasAssociatedWith ?p . " +
        		"?p dc:identifier ?p_id . " +
        		"?pexec wfms:completed -1 . " +
        		"}";
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            String pId = soln.getLiteral("p_id").getString();
            System.out.println(pId);
        }
        qexec.close();
	}
	
	
	//4. Find the process executions that were not completed
	public void query4(Dataset dataset, String wfID) {
		System.out.println("Executing query 4");
		String sparqlQueryString = "PREFIX provone: <http://purl.org/provone/ontology#> \n" +
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        		"PREFIX dc: <http://purl.org/dc/terms/> \n" +
        		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        		"PREFIX prov: <http://www.w3.org/ns/prov#> \n" +
        		"PREFIX wfms: <http://www.vistrails.org/registry.xsd> \n" +
				"SELECT DISTINCT ?pexec_id WHERE {  " + 
        		"?wf rdf:type provone:Workflow . " +
        		"?wf dc:identifier " + "\"" + wfID + "\"^^xsd:string . " +
        		"?wfexec prov:wasAssociatedWith ?wf . " +
        		"?pexec provone:isPartOf ?wfexec . " +
        		"?pexec dc:identifier ?pexec_id . " +
        		"?pexec wfms:completed -1 . " +
        		"}";
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            String pexecId = soln.getLiteral("pexec_id").getString();
            System.out.println(pexecId);
        }
        qexec.close();
	}
	
	
	//5. Find the processes that used data item x
	public void query5(Dataset dataset, String wfID, String dataID) {
		System.out.println("Executing query 5");
		String sparqlQueryString = "PREFIX provone: <http://purl.org/provone/ontology#> \n" +
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        		"PREFIX dc: <http://purl.org/dc/terms/> \n" +
        		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        		"PREFIX prov: <http://www.w3.org/ns/prov#> \n" +
				"SELECT DISTINCT ?p_id WHERE {  " + 
				"?wf rdf:type provone:Workflow . " +
				"?wf dc:identifier " + "\"" + wfID + "\"^^xsd:string . " +
				"?wf provone:hasSubProcess ?p . " +
				"?p dc:identifier ?p_id . " +
				"?pexec prov:wasAssociatedWith ?p . " +
				"?pexec prov:used ?data . " +
				"?data dc:identifier " + "\"" + dataID + "\"^^xsd:string . " +
        		"} ";
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            String pId = soln.getLiteral("p_id").getString();
            System.out.println(pId);
        }
        qexec.close();
	}
	
	
	//6. Find the data products influenced by process x
	public void query6(Dataset dataset, String wfID, String processID) {
		System.out.println("Executing query 6");
		String sparqlQueryString = "PREFIX provone: <http://purl.org/provone/ontology#> \n" +
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        		"PREFIX dc: <http://purl.org/dc/terms/> \n" +
        		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        		"PREFIX prov: <http://www.w3.org/ns/prov#> \n" +
				"SELECT DISTINCT ?derdata_id WHERE {  " + 
				"?wf rdf:type provone:Workflow . " +
				"?wf dc:identifier " + "\"" + wfID + "\"^^xsd:string . " +
				"?wf provone:hasSubProcess ?p . " +
				"?p dc:identifier " + "\"" + processID + "\"^^xsd:string . " +
				"?pexec prov:wasAssociatedWith ?p . " +
				"?data prov:wasGeneratedBy ?pexec . " +
				"?data dc:identifier ?data_id . " +
				"?derdata (prov:used | prov:wasGeneratedBy)* ?data . " +
				"?derdata rdf:type provone:Data . " +
				"?derdata dc:identifier ?derdata_id . " +
        		"} ";
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qexec.execSelect();
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            String dataId = soln.getLiteral("derdata_id").getString();
            System.out.println(dataId);
        }
        qexec.close();
	}
    
	
}

