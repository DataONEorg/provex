package org.dataone.daks.pbase.provxml2neo4j;

import java.util.UUID;


public class Data {
	
	String id;
	String name;
	String vtType;
	String desc;
	String value;
	String runID;
	UUID wfID;
	
	public Data() {
		
	}
	
	public Data(String id, String name, String vtType, String desc, String value, String runID, UUID wfID) {
		 this.id = id;
		 this.name = name;
		 this.vtType=vtType;
		 this.desc=desc;
		 this.value=value;
		 this.runID=runID;
		 this.wfID=wfID;
	} 
}
