package org.dataone.daks.provxml2json;


public class Data {
	
	String id;
	String name;
	String vtType;
	String desc;
	String value;
	String runID;
	
	public Data() {
		
	}
	
	public Data(String id, String name, String vtType, String desc, String value, String runID) {
		 this.id = id;
		 this.name = name;
		 this.vtType=vtType;
		 this.desc=desc;
		 this.value=value;
		 this.runID=runID;
	} 
}
