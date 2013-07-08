package org.dataone.daks.provxml2json;

import org.dom4j.Document;
import org.dom4j.Element;


public class ProvXML2JSON {
	
	private String filePath;
	
	private PROVBuilder provBuilder;
	
	public ProvXML2JSON(String filePath) {
		this.filePath = filePath;
	}
	
	public static void main(String args[]) {
		ProvXML2JSON mapper = new ProvXML2JSON(args[0]);
		mapper.doMapping();
	}
	
	public void doMapping() {
		Document doc = XMLLoader.getDocument(this.filePath);
		Element provRoot = doc.getRootElement();
		this.provBuilder = new PROVBuilder();
		this.provBuilder.processDocument(provRoot);
		this.provBuilder.createJSONFile("out.json");
		this.provBuilder.createDOTFile("out.dot");
		this.provBuilder.createRESTCypherFile("test.txt");
		this.provBuilder.createTextCypherFile("batch.txt");
	}
	
	
}



