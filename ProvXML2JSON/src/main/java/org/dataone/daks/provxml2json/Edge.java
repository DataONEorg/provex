package org.dataone.daks.provxml2json;


public class Edge {
	
	String label;
	String startId;
	String endId;
	String startName;
	String endName;
	String runID;

	
	
	public Edge() {
		
	}
	
	
	public Edge(String startId, String endId, String label, String startName, String endName, String runID) {
		this.startId = startId;
		this.endId = endId;
		this.label = label;
		this.startName = startName;
		this.endName = endName;
		this.runID=runID;
	}


}
