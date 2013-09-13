package org.dataone.daks.pbase.provxml2neo4j;

import org.dom4j.Document;
import org.dom4j.Element;


public class ProvXML2Neo4j {
	
	private String filePath;
	
	private PROVBuilder provBuilder;
	
	public ProvXML2Neo4j(String filePath) {
		this.filePath = filePath;
	}
	
	public static void main(String args[]) {
		ProvXML2Neo4j mapper = new ProvXML2Neo4j(args[0]);
		mapper.doMapping();
	}
	
	private void doMapping() {
		Document doc = XMLLoader.getDocument(this.filePath);
		Element provRoot = doc.getRootElement();
		this.provBuilder = new PROVBuilder();
		this.provBuilder.processDocument(provRoot);
		this.provBuilder.createJSONFile("out.json");
		//remove the .xml suffix from the filePath
		this.filePath = this.filePath.substring(0, this.filePath.length()-4);
		this.provBuilder.createDOTFileAllRuns(this.filePath + ".dot", filePath.toString());
		this.provBuilder.createDOTFile(this.filePath.toString());
		this.provBuilder.createRESTCypherFile("test.txt");
		this.provBuilder.createTextCypherFile(this.filePath + ".cql");
	}
	
	public void createTextCypherFile() {
		Document doc = XMLLoader.getDocument(this.filePath);
		Element provRoot = doc.getRootElement();
		this.provBuilder = new PROVBuilder();
		this.provBuilder.processDocument(provRoot);
		//remove the .xml suffix from the filePath
		this.filePath = this.filePath.substring(0, this.filePath.length()-4);  
		this.provBuilder.createTextCypherFile(this.filePath + ".cql");
	}
	
	
}



