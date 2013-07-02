package org.dataone.daks.provxml2json;


public class Edge {
	
	String label;
	String startId;
	String endId;
	String startName;
	String endName;
	
	
	public Edge() {
		
	}
	
	
	public Edge(String startId, String endId, String label, String startName, String endName) {
		this.startId = startId;
		this.endId = endId;
		this.label = label;
		this.startName = startName;
		this.endName = endName;
	}


}
