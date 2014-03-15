package org.dataone.daks.provxml2rdf;

import org.dom4j.Document;
import org.dom4j.Element;


public class ProvXML2RDF {
	
	
	public ProvXML2RDF() {
	}
	
	public static void main(String args[]) {
		ProvXML2RDF mapper = new ProvXML2RDF();
		mapper.doMapping(args[0], args[1]);
	}
	
	public String doMapping(String filePath, String wfID) {
		Document doc = XMLLoader.getDocument(filePath);
		Element provRoot = doc.getRootElement();
		PROVRDFBuilder provBuilder = new PROVRDFBuilder();
		provBuilder.processDocument(provRoot);
		//remove the .xml suffix from the filePath
		filePath = filePath.substring(0, filePath.length()-4);
		String tempXMLRDFFile = provBuilder.generateRDFTurtleFile(filePath + ".ttl", wfID);
		return tempXMLRDFFile;
	}
	
	
}



