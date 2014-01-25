package org.dataone.daks.provxml2rdf;

import org.dom4j.Document;
import org.dom4j.Element;


public class ProvXML2RDF {
	
	private String filePath;
	
	private PROVRDFBuilder provBuilder;
	
	public ProvXML2RDF(String filePath) {
		this.filePath = filePath;
	}
	
	public static void main(String args[]) {
		ProvXML2RDF mapper = new ProvXML2RDF(args[0]);
		mapper.doMapping();
	}
	
	private void doMapping() {
		Document doc = XMLLoader.getDocument(this.filePath);
		Element provRoot = doc.getRootElement();
		this.provBuilder = new PROVRDFBuilder();
		this.provBuilder.processDocument(provRoot);
		//remove the .xml suffix from the filePath
		this.filePath = this.filePath.substring(0, this.filePath.length()-4);
		this.provBuilder.generateRDFTurtleFile(this.filePath + ".ttl");
	}
	
	
}



