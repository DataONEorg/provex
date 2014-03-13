package org.dataone.daks.provxml2rdf;

import java.io.*;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.FileManager;

import org.apache.jena.riot.* ;

public class JenaExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JenaExample example = new JenaExample();
		//Model model = example.createExampleModel();
		Model model = example.createProcessModel();
		try {
			FileOutputStream fos = new FileOutputStream(new File("wf.xml"));
			model.write(fos, "RDF/XML");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		model = RDFDataMgr.loadModel("wf.xml");
		//RDFDataMgr.write(System.out, model, Lang.TURTLE);
		RDFDataMgr.write(System.out, model, RDFFormat.TURTLE_PRETTY);
	}
	
	
	private Model createExampleModel() {
		Model model = ModelFactory.createDefaultModel();
		String NS = "http://example.com/test/";
		Resource r = model.createResource(NS + "r");
		Property p = model.createProperty(NS + "p");
		r.addProperty(p, "hello world", XSDDatatype.XSDstring);
		return model;
	}
	
	private Model createProcessModel() {
		Model model = ModelFactory.createDefaultModel();
		String PROVONE_NS = "http://purl.org/provone";
		String DCTERMS_NS = "http://purl.org/dc/terms/";
		String EXAMPLE_NS = "http://example.com/";
		String WFMS_NS = "http://www.vistrails.org/registry.xsd";
		String SOURCE_URL = "http://purl.org/provone/ontology";
		String SOURCE_FILE = "./provone.owl";

		OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
		//Uncomment to use file instead of the URL
		FileManager.get().getLocationMapper().addAltEntry( SOURCE_URL, SOURCE_FILE );
		Model baseOntology = FileManager.get().loadModel( SOURCE_URL );
		m.addSubModel( baseOntology );
		m.setNsPrefix( "provone", SOURCE_URL + "#" );
        
		OntClass processClass = m.getOntClass( SOURCE_URL + "#" + "Process" );
		Individual process_1 = m.createIndividual( EXAMPLE_NS + "process_1", processClass );
		//Individual process_1 = m.createIndividual( SOURCE_URL + "#" + "process_1", processClass );
		
		Property identifierP = m.createProperty(DCTERMS_NS + "identifier");
		process_1.addProperty(identifierP, "e1", XSDDatatype.XSDstring);
		
		return m;
	}
	

}


