package org.dataone.daks.pbase.provxml2neo4j;

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
	
	public Module() {
		
	}
	
	public Module(String id, String name, String vtType, String desc, String vtPackage, String version, String cache, UUID wfID) {
		this.id = id;
		this.name = name;
		this.vtType=vtType;
		this.cache=cache;
		this.vtPackage=vtPackage;
		this.desc=desc;
		this.version=version;
		this.wfID=wfID;
	}

}
