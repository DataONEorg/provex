package org.dataone.daks.provxml2rdf;

import java.util.UUID;
import java.util.HashMap;

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
	
	HashMap<String, String> inputPorts;
	HashMap<String, String> outputPorts;
	
	public Module() {
		this.inputPorts = new HashMap<String, String>();
		this.outputPorts = new HashMap<String, String>();	
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
		this.inputPorts = new HashMap<String, String>();
		this.outputPorts = new HashMap<String, String>();
	}

}

