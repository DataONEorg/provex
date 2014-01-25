package org.dataone.daks.provxml2rdf;

import java.util.UUID;

public class Module {

	String id;
	String name;
	String vtType;
	String desc ;
	String vtPackage;
	String version;
	String cache;
	UUID wfID;
	String entityId;
	
	public Module() {
		
	}
	
	public Module(String id, String name, String vtType, String desc, String vtPackage, String version, String cache, 
			UUID wfID, String entityId) {
		this.id = id;
		this.name = name;
		this.vtType = vtType;
		this.cache = cache;
		this.vtPackage = vtPackage;
		this.desc = desc;
		this.version = version;
		this.wfID = wfID;
		this.entityId = entityId;
	}

}

