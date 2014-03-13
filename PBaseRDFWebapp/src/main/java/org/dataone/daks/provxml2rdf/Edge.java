package org.dataone.daks.provxml2rdf;


public class Edge {
	
	String label;
	String startId;
	String endId;
	String startName;
	String endName;
	String runID;
	String source;
	String dest;
	String sourcePort;
	String destPort;
	String id;
	
	
	public Edge() {
		
	}
	
	
	public Edge(String startId, String endId, String label, String startName, String endName, String runID,
			String source, String dest, String sourcePort, String destPort, String id) {
		this.startId = startId;
		this.endId = endId;
		this.label = label;
		this.startName = startName;
		this.endName = endName;
		this.runID = runID;
		this.source = source;
		this.dest = dest;
		this.sourcePort = sourcePort;
		this.destPort = destPort;
		this.id = id;
	}


}

