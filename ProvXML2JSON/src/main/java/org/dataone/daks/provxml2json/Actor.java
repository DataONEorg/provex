package org.dataone.daks.provxml2json;


public class Actor {
	
	String id;
	String name;
	String version;
	String type;
	String desc;
	String cache;
	String completed;
	String runID;
	
	public Actor() {
		
	}
	
	
	public Actor(String id, String name, String version, String type, String desc, String cache, String completed, String runID) {
		this.id = id;
		this.name = name;
		this.version=version;
		this.type=type;
		this.desc=desc;
		this.cache=cache;
		this.completed=completed;
		this.runID=runID;
	}

}
