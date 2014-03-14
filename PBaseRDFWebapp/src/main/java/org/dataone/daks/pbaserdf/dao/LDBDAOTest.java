package org.dataone.daks.pbaserdf.dao;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.tdb.TDBFactory ;


public class LDBDAOTest {
	
    public static void main(String args[]) {
    	
        // Direct way: Make a TDB-back Jena model in the named directory.
        String directory = "C:\\devp\\apache-tomcat-7.0.50\\bin\\" + args[0];
        Dataset dataset = TDBFactory.createDataset(directory);
        LDBDAOTest test = new LDBDAOTest();
        //test.countQuery(dataset);
        test.wfIDsQuery(dataset);
        //test.triplesQuery(dataset);
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
    
    
}

