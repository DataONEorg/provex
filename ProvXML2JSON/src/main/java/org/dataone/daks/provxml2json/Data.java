package org.dataone.daks.provxml2json;


public class Data {
	
	String id;
	String name;
	String version;
	String type;
	String desc;
	String value;
	String runID;
	
	public Data() {
		
	}
	
	public Data(String id, String name, String version, String type, String desc, String value, String runID) {
		 this.id = id;
		 this.name = name;
		 this.version=version;
		 this.type=type;
		 this.desc=desc;
		 this.value=value;
		 this.runID=runID;
	} 
}
