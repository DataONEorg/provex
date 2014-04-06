package org.dataone.daks.provxml2rdf;

import java.util.UUID;


public class Actor {
	
	String id;
	String name;
	String vtType;
	int cached;
	int completed;
	String module;
	String runID;
	UUID wfID;
	String activityId;
	String startTime;
	String endTime;
	
	public Actor() {
		this.cached = Integer.MIN_VALUE;
		this.completed = Integer.MIN_VALUE;
	}
	
	
	public Actor(String id, String name, String vtType, int cached, int completed, String module, String runID, 
			UUID wfID, String activityId, String startTime, String endTime) {
		this.id = id;
		this.name = name;
		this.vtType = vtType;
		this.cached = cached;
		this.completed = completed;
		this.module = module;
		this.runID = runID;
		this.wfID = wfID;
		this.activityId = activityId;
		this.startTime = startTime;
		this.endTime = endTime;
	}

}


