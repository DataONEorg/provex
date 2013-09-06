package org.dataone.daks.provxml2json;

import java.util.UUID;


public class Actor {
	
	String id;
	String name;
	String vtType;
	String cache;
	String completed;
	String module;
	String runID;
	UUID wfID;
	
	public Actor() {
		
	}
	
	
	public Actor(String id, String name, String vtType, String cache, String completed, String module, String runID, UUID wfID) {
		this.id = id;
		this.name = name;
		this.vtType=vtType;
		this.cache=cache;
		this.completed=completed;
		this.module=module;
		this.runID=runID;
		this.wfID=wfID;
	}

}
