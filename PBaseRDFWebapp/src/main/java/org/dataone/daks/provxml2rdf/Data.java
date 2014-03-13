package org.dataone.daks.provxml2rdf;

import java.util.UUID;


public class Data {
	
	String id;
	String name;
	String vtType;
	String desc;
	String value;
	String runID;
	UUID wfID;
	String entityId;
	String label;
	
	public Data() {
		
	}
	
	public Data(String id, String name, String vtType, String desc, String value, String runID, 
			UUID wfID, String entityId, String label) {
		 this.id = id;
		 this.name = name;
		 this.vtType = vtType;
		 this.desc = desc;
		 this.value = value;
		 this.runID = runID;
		 this.wfID = wfID;
		 this.entityId = entityId;
		 this.label = label;
	}
	
}

