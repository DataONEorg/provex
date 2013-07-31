package org.dataone.daks.provxml2json;


public class Actor {
	
	String id;
	String name;
	String vtType;
	String cache;
	String completed;
	String runID;
	
	public Actor() {
		
	}
	
	
	public Actor(String id, String name, String vtType, String cache, String completed, String runID) {
		this.id = id;
		this.name = name;
		this.vtType=vtType;
		this.cache=cache;
		this.completed=completed;
		this.runID=runID;
	}

}
