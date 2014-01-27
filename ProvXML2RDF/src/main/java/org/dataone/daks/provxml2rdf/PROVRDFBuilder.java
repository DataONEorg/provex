package org.dataone.daks.provxml2rdf;

import java.io.*;
import java.util.*;

import org.dom4j.*;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;

import org.apache.jena.riot.*;


public class PROVRDFBuilder {

	private HashMap<String, Element> dataEntities;
	private HashMap<String, Element> planEntities;
	private HashMap<String, Element> activities;
	private HashMap<String, Element> modules;
	private HashMap<String, String> wasAssociatedWith;
	private HashMap<String, String> dataEntityFullName;
	private HashMap<String, String> activityFullName;
	private HashMap<String, String> dataType;
	private HashMap<String, String> dataDesc;
	private HashMap<String, String> dataValue;
	private HashMap<String, String> dataRunID;
	private HashMap<String, String> dataLabel;
	private HashMap<String, String> activityCache;
	private HashMap<String, String> activityCompleted;
	private HashMap<String, String> moduleVersion;
	private HashMap<String, String> modulePackage;
	private HashMap<String, String> moduleName;
	private HashMap<String, String> actorModule;
	private HashMap<String, String> isPartOf;
	
	private ArrayList<Actor> actors;
	private ArrayList<Data> dataObjs;
	private ArrayList<Module> moduleObjs;
	private ArrayList<Edge> edges;
	private Set<String> runIDs;
	private HashMap<String, Module> moduleHM;
	private HashMap<String, String> personHM;
	
	private Namespace provNS;
	private Namespace vtNS;
	private Namespace dcterms;
	
	UUID wfID;
	String wfVersion;
	String wfEntityId;
	String wfLabel;
	
	String PROVONE_NS = "http://purl.org/provone";
	String PROV_NS = "http://www.w3.org/ns/prov#";
	String DCTERMS_NS = "http://purl.org/dc/terms/";
	String EXAMPLE_NS = "http://example.com/";
	String WFMS_NS = "http://www.vistrails.org/registry.xsd";
	String SOURCE_URL = "http://purl.org/provone/ontology";
	String SOURCE_FILE = "./provone.owl";

	
	public PROVRDFBuilder() {
		this.dataEntities = new HashMap<String, Element>();
		this.planEntities = new HashMap<String, Element>();
		this.activities = new HashMap<String, Element>();
		this.modules = new HashMap<String, Element>();
		this.wasAssociatedWith = new HashMap<String, String>();
		this.dataEntityFullName = new HashMap<String, String>();
		this.activityFullName = new HashMap<String, String>();
		this.dataType = new HashMap<String, String>();
		this.dataDesc = new HashMap<String, String>();
		this.dataValue = new HashMap<String, String>();
		this.dataRunID = new HashMap<String, String>();
		this.dataLabel = new HashMap<String, String>();
		this.activityCompleted = new HashMap<String, String>();
		this.activityCache = new HashMap<String, String>();
		this.moduleVersion = new HashMap<String, String>();
		this.modulePackage = new HashMap<String, String>();
		this.moduleName = new HashMap<String, String>();
		this.actorModule = new HashMap<String, String>();
		this.isPartOf = new HashMap<String, String>();
		
		this.moduleHM = new HashMap<String, Module>();
		this.personHM = new HashMap<String, String>();
		
		this.runIDs = new HashSet<String>();
		this.wfID = UUID.randomUUID();
		
		this.provNS = new Namespace("prov", "http://www.w3.org/ns/prov#");
		this.vtNS = new Namespace("vt", "http://www.vistrails.org/registry.xsd");
		this.dcterms = new Namespace("dcterms", "http://purl.org/dc/terms/");
	}
	
	
	public void processDocument(Element root) {
		this.createEntitiesHT(root);
		this.createActivitiesHT(root);
		this.createWasAssociatedWithHT(root);
		this.createActivityFullNames(root);
		
		this.createPersonHM(root);
		
		this.createActorsList();
		this.createDataObjsList();
		this.createModuleObjsList();
		
		this.createEdges(root);
	}
	
	
	// Create a dictionary for the entities
	private void createEntitiesHT(Element root) {
		for ( Iterator i = root.elementIterator("entity"); i.hasNext(); ) {
			Element entityElem = (Element) i.next();
			String entityId = entityElem.attributeValue("id");
			Element elemProvType = entityElem.element(new QName("type", this.provNS));
			Element elemProvLabel = entityElem.element("label");
			Element elemVtType = entityElem.element(new QName("type", this.vtNS));
			Element elemVtDesc = entityElem.element(new QName("desc", this.vtNS));
			Element elemVtValue = entityElem.element(new QName("value", this.provNS));
			Element elemRunID = entityElem.element(new QName("isPartOf", this.dcterms));
			Element elemCache = entityElem.element(new QName("cache", this.vtNS));
			Element elemVersion = entityElem.element(new QName("version", this.vtNS));
			Element elemPackage = entityElem.element(new QName("package", this.vtNS));
			Element elemId = entityElem.element(new QName("id", this.vtNS));
            
			if (elemVtType != null)
				this.dataType.put(entityId, elemVtType.getText());
			
			if( elemProvType != null ) {
				if( elemProvType.getText().equals("prov:Plan") ) {
					if (elemId != null)
						this.wfVersion = elemId.getText();
					if( elemVtType != null ) {
						if( elemVtType.getText().equals("vt:module") || elemVtType.getText().equals("vt:workflow")  )
							this.planEntities.put(entityId, entityElem);
						if( elemVtType.getText().equals("vt:module") ) {
							this.modules.put(entityId, entityElem);
							if( elemProvLabel != null )
								this.moduleName.put(entityId, elemProvLabel.getText());
							if( elemRunID != null )
								this.isPartOf.put(entityId, elemRunID.attributeValue(new QName("ref", this.provNS)));
						}
						if( elemVtType.getText().equals("vt:workflow") ) {
							this.wfEntityId = entityId;
							if( elemProvLabel != null )
								this.wfLabel = elemProvLabel.getText();
						}
						if( elemProvLabel != null )
							this.dataEntityFullName.put(entityId, entityId + "_" + elemProvLabel.getText());
						else
							this.dataEntityFullName.put(entityId, entityId);
						if ( elemCache != null )
							this.activityCache.put(entityId, elemCache.getText());
						if (elemVersion != null)
							this.moduleVersion.put(entityId, elemVersion.getText());
						if (elemPackage != null)
							this.modulePackage.put(entityId, elemPackage.getText());
						if (elemVtDesc != null)
							this.dataDesc.put(entityId, elemVtDesc.getText());
					}
				}
				else {
					if( elemProvType.getText().equals("vt:data") ) {
						this.dataEntities.put(entityId, entityElem);
						if( elemVtDesc != null )
							this.dataDesc.put(entityId, elemVtDesc.getText());
						if( elemVtValue != null )
							this.dataValue.put(entityId, elemVtValue.getText());
						if( elemProvLabel != null ) {
							this.dataEntityFullName.put(entityId, entityId + "_" + elemProvLabel.getText());
							this.dataLabel.put(entityId, elemProvLabel.getText());
						}
						else
							this.dataEntityFullName.put(entityId, entityId);
					}
				}
			}
			if( elemRunID != null )
				this.dataRunID.put(entityId, elemRunID.attributeValue("ref"));
		}
	}
	
	
	// Create a dictionary for the activities
	private void createActivitiesHT(Element root) {
		for ( Iterator i = root.elementIterator("activity"); i.hasNext(); ) {
			Element activityElem = (Element) i.next();
			String activityId = activityElem.attributeValue("id");
			
			Element elemVtType = activityElem.element(new QName("type", this.vtNS));
			Element elementVtDesc=activityElem.element(new QName("desc", this.vtNS));
			Element elementVtCache=activityElem.element(new QName("cached", this.vtNS));
			Element elementVtCompleted=activityElem.element(new QName("completed", this.vtNS));
			Element elemDcterms = activityElem.element(new QName("isPartOf", this.dcterms));
			
			this.dataType.put(activityId, elemVtType.getText());  
			if( ! elemVtType.getText().equals("vt:wf_exec") )
				this.activities.put(activityId, activityElem);
			if( elementVtDesc != null )
				this.activityCache.put(activityId, elementVtCache.getText());
			if( elementVtCompleted != null )
				this.activityCompleted.put(activityId, elementVtCompleted.getText());
			String refType = null;
			if( elemDcterms != null ) {
				refType = this.dataType.get(elemDcterms.attributeValue("ref"));
				if ( refType.equals("vt:wf_exec") ) {
					this.dataRunID.put(activityId, elemDcterms.attributeValue("ref"));
					// add this runID to the list of runIDs for the current workflow
					this.runIDs.add (elemDcterms.attributeValue("ref"));
				}
				else {
					this.dataRunID.put(activityId, this.dataRunID.get(elemDcterms.attributeValue("ref")));
					this.runIDs.add(this.dataRunID.get(elemDcterms.attributeValue("ref")));
				}
			}
		}
	}

	
	// Create a dictionary for the persons (agents)
	private void createPersonHM(Element root) {
		for ( Iterator i = root.elementIterator("agent"); i.hasNext(); ) {
			Element agentElem = (Element) i.next();
			String agentId = agentElem.attributeValue(new QName("id", this.provNS));
			Element elemProvType = agentElem.element(new QName("type", this.provNS));
			if( elemProvType.getText().equals("prov:Person") ) {
				Element elemProvLabel = agentElem.element(new QName("label", this.provNS));
				String label = elemProvLabel.getText();
				this.personHM.put(agentId, label);
			}
		}
	}
	
	
	// Create a dictionary for the 'wasAssociatedWith' elements
	private void createWasAssociatedWithHT(Element root) {
		for ( Iterator i = root.elementIterator("wasAssociatedWith"); i.hasNext(); ) {
			Element assocWithElem = (Element) i.next();
			Element elemProvAct = assocWithElem.element("activity");
			String actRef = elemProvAct.attributeValue("ref");
			Element elemProvPlan = assocWithElem.element("plan");
			String planRef = elemProvPlan.attributeValue("ref");
			this.wasAssociatedWith.put(actRef, planRef);
		}
	}	

	
	private void createActivityFullNames(Element root) {
		for (String key : this.activities.keySet()) {
			String planRef = this.wasAssociatedWith.get(key);
			if( planRef != null ) {
				Element planEntity = this.planEntities.get(planRef);
				String name = "";
		        Element elemProvLabel = planEntity.element("label");
		        if( elemProvLabel != null )
					name = elemProvLabel.getText();
				this.activityFullName.put(key, key + "_" + name);
			}
			else {
				this.activityFullName.put(key, key);
			}
		}
	}
	
	
	private void createActorsList() {
		this.actors = new ArrayList<Actor>();
		for (String key : this.activities.keySet()) {
			String nodeId = this.activityFullName.get(key);
			Actor actor = new Actor();
			actor.id = nodeId;		
			actor.vtType = this.dataType.get(key);
			actor.cache = this.activityCache.get(key);
			actor.completed = this.activityCompleted.get(key);
			actor.module = this.actorModule.get(key);
			actor.runID = this.dataRunID.get(key);
		    actor.wfID = this.wfID;
		    actor.activityId = key;
			this.actors.add(actor);
		}
	}
	
	
	private void createDataObjsList() {
		this.dataObjs = new ArrayList<Data>();
		for (String key : this.dataEntities.keySet()) {
			String nodeId = this.dataEntityFullName.get(key);
			Data data = new Data();
			data.id = nodeId;		
			data.vtType = this.dataType.get(key);
			data.desc = this.dataDesc.get(key);
			data.value = this.dataValue.get(key);
			data.runID = this.dataRunID.get(key);
			data.wfID = this.wfID;
			data.entityId = key;
			data.label = this.dataLabel.get(key);
			dataObjs.add(data);
		}
	}
	
	
	private void createModuleObjsList() {
		this.moduleObjs = new ArrayList<Module>();
		for (String key : this.modules.keySet()) {
			String nodeId = this.dataEntityFullName.get(key);
			Module module = new Module();
			module.id = nodeId;		
			module.vtType = this.dataType.get(key);
			module.desc = this.dataDesc.get(key);
			module.vtPackage = this.modulePackage.get(key);
			module.version = this.moduleVersion.get(key);
			module.cache = this.activityCache.get(key);
			module.wfID = this.wfID;
			module.entityId = key;
			module.name = moduleName.get(key);
			this.moduleObjs.add(module);
			this.moduleHM.put(key, module);
		}
	}
	
	
	private void createEdges(Element root) {
		this.edges = new ArrayList<Edge>();
		for ( Iterator i = root.elementIterator("used"); i.hasNext(); ) {
			Element elemUsed = (Element) i.next();
			Element elemProvAct = elemUsed.element("activity");
			String actRef = elemProvAct.attributeValue("ref");
			Element elemProvEnt = elemUsed.element("entity");
			String entRef = elemProvEnt.attributeValue("ref");
			String startNodeId = this.activityFullName.get(actRef); 
			String endNodeId = this.dataEntityFullName.get(entRef);
			Edge edge = new Edge();
			edge.runID = this.dataRunID.get(actRef);
			edge.label = "used";
			edge.startId = startNodeId;
			edge.endId = endNodeId;
			this.edges.add(edge);
		}
		for ( Iterator i = root.elementIterator("wasGeneratedBy"); i.hasNext(); ) {
			Element elemGenBy = (Element) i.next();
			Element elemProvAct = elemGenBy.element("activity");
			String actRef = elemProvAct.attributeValue("ref");
			Element elemProvEnt = elemGenBy.element("entity");
			String entRef = elemProvEnt.attributeValue("ref");
			String startNodeId = this.dataEntityFullName.get(entRef);
			String endNodeId = this.activityFullName.get(actRef);
			Edge edge = new Edge();
			edge.runID = this.dataRunID.get(actRef);
			edge.label = "genBy";
			edge.startId = startNodeId;
			edge.endId = endNodeId;
			this.edges.add(edge);
		}
		for ( Iterator i = root.elementIterator("connection"); i.hasNext(); ) {
			Element elemConnect = (Element) i.next();
			Element elemSource = elemConnect.element(new QName("source", this.vtNS));
			String source = elemSource.getText();
			Element elemDest = elemConnect.element(new QName("dest", this.vtNS));
			String dest = elemDest.getText();
			Element elemSourcePort = elemConnect.element(new QName("source_port", this.vtNS));
			String sourcePort = elemSourcePort.getText();
			Element elemDestPort = elemConnect.element(new QName("dest_port", this.vtNS));
			String destPort = elemDestPort.getText();
			Element elemSourceSignature = elemConnect.element(new QName("source_signature", this.vtNS));
			String sourceSignature = elemSourceSignature.getText();
			Element elemDestSignature = elemConnect.element(new QName("dest_signature", this.vtNS));
			String destSignature = elemDestSignature.getText();
			String startNodeId = this.dataEntityFullName.get(source);
			String endNodeId = this.dataEntityFullName.get(dest);
			String id = elemConnect.attributeValue("id");
			Edge edge = new Edge();
			edge.id = id;
			edge.label = "connect";
			edge.startId = startNodeId;
			edge.endId = endNodeId;
			edge.source = source;
			edge.dest = dest;
			edge.sourcePort = sourcePort;
			edge.destPort = destPort;
			this.edges.add(edge);
			this.moduleHM.get(source).outputPorts.put(sourcePort, sourceSignature);
			this.moduleHM.get(dest).inputPorts.put(destPort, destSignature);
		}
		for ( Iterator i = root.elementIterator("wasAssociatedWith"); i.hasNext(); ) {
			Element elemassociatedWith= (Element) i.next();
			Element elemPlan = elemassociatedWith.element("plan");
			String source = elemPlan.attributeValue("ref");
			Element elemAct = elemassociatedWith.element("activity");
			String destNodeId = elemAct.attributeValue("ref");
			String startNodeId = this.dataEntityFullName.get(source);
			String endNodeId = this.activityFullName.get(destNodeId);
			this.actorModule.put(destNodeId,startNodeId);
			Edge edge = new Edge();
			edge.label = "associatedWith";
			edge.startId = startNodeId;
			edge.endId = endNodeId;
			edge.runID = this.dataRunID.get(destNodeId);
			this.edges.add(edge);
		}
		//create edges between workflow and its runs
		for( String runIDNode: this.runIDs ) {
			Edge edge = new Edge();
			edge.startId = this.wfID.toString();
			edge.endId = runIDNode;
			edge.label = "belongsTo";
			this.edges.add(edge);	
		}
		
	}
	
	
	protected void createTextCypherFile(String filename) {
		/*
       		CREATE n={name:"name_val"}
       		...
       		START n=node:node_auto_index(name='name1'), m=node:node_auto_index(name='name2') CREATE n-[r:USED/WASGENBY]-m
       		...
		*/
		try {
			StringBuilder sb = new StringBuilder();
			// Iterate over the data items and actors
			for( Data data: this.dataObjs ) {
				sb.append("CREATE n={");
				sb.append("name:\""+ data.id + "\"," );
				if (data.vtType!=null)
					sb.append("vtType:\""+ data.vtType + "\"," );
				if (data.desc!=null)
					sb.append("description:\""+ data.desc + "\"," );
				if (data.value!=null)
					sb.append("value:\""+ data.value + "\"," );
				if (data.runID!=null)
					sb.append("runID:\""+ data.runID + "\"," );		
				sb.append("wfID:\""+ data.wfID + "\"," );
				sb.append("type:\""+ "data" + "\"" );		
				sb.append("};"+"\n" );
			}
			for( Actor actor: this.actors) {
				sb.append("CREATE n={");
				sb.append("name:\""+ actor.id + "\"," );
				if (actor.vtType!=null)
					sb.append("vtType:\""+ actor.vtType + "\"," );
				if (actor.cache!=null)
					sb.append("cache:\""+ actor.cache + "\"," );
				if (actor.completed!=null)
					sb.append("completed:\""+ actor.completed + "\"," );
				if (actor.runID!=null)
					sb.append("runID:\""+ actor.runID + "\"," );
				sb.append("module:\""+ actor.module + "\"," );
				sb.append("wfID:\""+ actor.wfID + "\"," );
				sb.append("type:\""+ "activity" + "\"" );		
				sb.append("};"+"\n" );
			}
			for( Module module: this.moduleObjs) {
				sb.append("CREATE n={");
				sb.append("name:\""+ module.id + "\"," );
			    if (module.vtType!=null)
			    	sb.append("vtType:\""+ module.vtType + "\"," );
			    if (module.cache!=null)
			    	sb.append("cache:\""+ module.cache + "\"," );
			    if (module.desc!=null)
			    	sb.append("description:\""+ module.desc + "\"," );
			    if (module.vtPackage!=null)
			    	sb.append("package:\""+ module.vtPackage + "\"," );
			    if (module.version!=null)
			    	sb.append("version:\""+ module.version + "\"," );
				sb.append("wfID:\""+ module.wfID + "\"," );	
				sb.append("type:\""+ "module" + "\"" );		
				sb.append("};"+"\n" );
			}
			//Add an extra node representing the workflow to the graph
			sb.append("CREATE n={");
			sb.append("name:\""+ this.wfID + "\"," );
			sb.append("wfID:\""+ this.wfID + "\"," );
			sb.append("type:\""+ "workflow" + "\"" );				
			sb.append("};"+"\n" );
			//Add runID nodes
  			for( String runIDNode: this.runIDs ) {
  				sb.append("CREATE n={");
  				sb.append("name:\""+ runIDNode + "\"," );
  				sb.append("wfID:\""+ this.wfID + "\"," );
  				sb.append("runID:\""+ runIDNode + "\"," );
  				sb.append("type:\""+ "run" + "\"" );				
  				sb.append("};"+"\n" );
  			}
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
			//out.println("BEGIN");
			out.println(sb);
			String reqLabel = null;
			// Create the edges for the 'used' and 'wasGeneratedBy' relations
			for ( Edge edge: this.edges ) {
				if( edge.label.equals("used") )
					reqLabel = "used";
				else if( edge.label.equals("genBy") )
					reqLabel = "wasGeneratedBy";
				else if( edge.label.equals("connect") )
					reqLabel = "isConnectedWith";
				else if( edge.label.equals("associatedWith") )
					reqLabel = "wasAssociatedWith";
				else if( edge.label.equals("belongsTo") )
					reqLabel = "belongsTo";
			    out.print("START n=node:node_auto_index(name='" + edge.startId + "'), ");
			    out.print("m=node:node_auto_index(name='" + edge.endId + "') ");
			    out.println("CREATE n-[r:" + reqLabel + "]->m;");
			}
	        out.println();
			out.close();
	    }
		catch (IOException e) {
			e.printStackTrace();
	    }
	}
	
	
	protected void generateRDFTurtleFile(String filename) {
		
		OntModel m = this.createOntModel();
		HashMap<String, Individual> idToInd = new HashMap<String, Individual>();
		//Generate the Workflow entity
		OntClass workflowClass = m.getOntClass( SOURCE_URL + "#" + "Workflow" );
		Individual workflowInd = m.createIndividual( EXAMPLE_NS + "wf", workflowClass );
		Property wfIdentifierP = m.createProperty(DCTERMS_NS + "identifier");
		workflowInd.addProperty(wfIdentifierP, this.wfEntityId, XSDDatatype.XSDstring);
		idToInd.put(this.wfEntityId, workflowInd);
		Property wfTitleP = m.createProperty(DCTERMS_NS + "title");
		workflowInd.addProperty(wfTitleP, this.wfLabel, XSDDatatype.XSDstring);
		// Iterate over the modules to generate Process entities
		int i = 1;
		int ip = 1;
		int op = 1;
		for( Module module: this.moduleObjs) {
			OntClass processClass = m.getOntClass( SOURCE_URL + "#" + "Process" );
			Individual processInd = m.createIndividual( EXAMPLE_NS + "process_" + i, processClass );
			Property identifierP = m.createProperty(DCTERMS_NS + "identifier");
			processInd.addProperty(identifierP, module.entityId, XSDDatatype.XSDstring);
			idToInd.put(module.entityId, processInd);
			Property titleP = m.createProperty(DCTERMS_NS + "title");
			processInd.addProperty(titleP, module.name, XSDDatatype.XSDstring);
			//Uncomment to ad the package property
			//Property packageP = m.createProperty(WFMS_NS + "package");
			//processInd.addProperty(packageP, module.vtPackage, XSDDatatype.XSDstring);
			// Iterate over the modules to generate InputPort and OutputPort entities
			OntClass inputPortClass = m.getOntClass( SOURCE_URL + "#" + "InputPort" );
			OntClass outputPortClass = m.getOntClass( SOURCE_URL + "#" + "OutputPort" );
			ip = 1;
			for( String ipKey : module.inputPorts.keySet()) {
				Individual inputPortInd = m.createIndividual( EXAMPLE_NS + "p" + i + "_ip" + ip, inputPortClass );
				Property ipIdentifierP = m.createProperty(DCTERMS_NS + "identifier");
				inputPortInd.addProperty(ipIdentifierP, module.entityId + "_" + ipKey, XSDDatatype.XSDstring);
				idToInd.put(module.entityId + "_" + ipKey, inputPortInd);
				Property ipTitleP = m.createProperty(DCTERMS_NS + "title");
				inputPortInd.addProperty(ipTitleP, ipKey, XSDDatatype.XSDstring);
				//Property signatureP = m.createProperty(WFMS_NS + "signature");
				//inputPortInd.addProperty(signatureP, module.inputPorts.get(ipKey), XSDDatatype.XSDstring);
				ip++;
			}
			op = 1;
			for( String opKey : module.outputPorts.keySet()) {
				Individual outputPortInd = m.createIndividual( EXAMPLE_NS + "p" + i + "_op" + op, outputPortClass );
				Property opIdentifierP = m.createProperty(DCTERMS_NS + "identifier");
				outputPortInd.addProperty(opIdentifierP, module.entityId + "_" + opKey, XSDDatatype.XSDstring);
				idToInd.put(module.entityId + "_" + opKey, outputPortInd);
				Property opTitleP = m.createProperty(DCTERMS_NS + "title");
				outputPortInd.addProperty(opTitleP, opKey, XSDDatatype.XSDstring);
				//Property signatureP = m.createProperty(WFMS_NS + "signature");
				//outputPortInd.addProperty(signatureP, module.outputPorts.get(opKey), XSDDatatype.XSDstring);
				op++;
			}
			i++;
		}
		// Iterate over the edges to generate DataLink entities
		int dl = 1;
		for ( Edge edge: this.edges ) {
			if( edge.label.equals("connect") ) {
				OntClass dataLinkClass = m.getOntClass( SOURCE_URL + "#" + "DataLink" );
				Individual dataLinkInd = m.createIndividual( EXAMPLE_NS + "dl" + dl, dataLinkClass );
				Property identifierP = m.createProperty(DCTERMS_NS + "identifier");
				dataLinkInd.addProperty(identifierP, edge.source + "_" + edge.dest + "DL", XSDDatatype.XSDstring);
				idToInd.put(edge.id, dataLinkInd);
				dl++;
			}
		}
		//Iterate over the person objects to generate User entities
		i = 1;
		for( String personKey : this.personHM.keySet()) {
			OntClass userClass = m.getOntClass( SOURCE_URL + "#" + "User" );
			Individual userInd = m.createIndividual( EXAMPLE_NS + "user" + i, userClass );
			Property userIdentifierP = m.createProperty(DCTERMS_NS + "identifier");
			userInd.addProperty(userIdentifierP, this.personHM.get(personKey), XSDDatatype.XSDstring);
			idToInd.put(personKey, userInd);
			i++;
		}
		// Iterate over the data objects to generate Data entities
		i = 1;
		for( Data data: this.dataObjs ) {
			OntClass dataClass = m.getOntClass( SOURCE_URL + "#" + "Data" );
			Individual dataInd = m.createIndividual( EXAMPLE_NS + "data_" + i, dataClass );
			Property identifierP = m.createProperty(DCTERMS_NS + "identifier");
			dataInd.addProperty(identifierP, data.entityId, XSDDatatype.XSDstring);
			idToInd.put(data.entityId, dataInd);
			Property labelP = m.createProperty(DCTERMS_NS + "label");
			if( data.label != null )
				dataInd.addProperty(labelP, data.label, XSDDatatype.XSDstring);
			Property valueP = m.createProperty(PROV_NS + "value");
			if( data.value != null )
				dataInd.addProperty(valueP, data.value, XSDDatatype.XSDstring);
			//Uncomment to ad the type property
			//Property typeP = m.createProperty(WFMS_NS + "type");
			//if( data.vtType != null )
			//dataInd.addProperty(typeP, data.vtType, XSDDatatype.XSDstring);
			i++;
		}
		// Iterate over the modules to generate hasSubprocess, hasInputPort, and hasOutputPort object properties
		for( Module module: this.moduleObjs) {
			ObjectProperty hasSubProcessOP = m.createObjectProperty(SOURCE_URL + "#" + "hasSubProcess");
			if( this.isPartOf.get(module.entityId) != null )
				m.add(idToInd.get(this.isPartOf.get(module.entityId)), hasSubProcessOP, idToInd.get(module.entityId));
			ObjectProperty hasInputPortOP = m.createObjectProperty(SOURCE_URL + "#" + "hasInputPort");
			for(String ipKey: module.inputPorts.keySet())
				m.add(idToInd.get(module.entityId), hasInputPortOP, idToInd.get(module.entityId + "_" + ipKey));
			ObjectProperty hasOutputPortOP = m.createObjectProperty(SOURCE_URL + "#" + "hasOutputPort");
			for(String opKey: module.outputPorts.keySet())
				m.add(idToInd.get(module.entityId), hasOutputPortOP, idToInd.get(module.entityId + "_" + opKey));
		}
		// Iterate over the edge objects to generate DLToInPort and outPortToDL object properties
		for( Edge edge: this.edges) {
			ObjectProperty DLToInPortOP = m.createObjectProperty(SOURCE_URL + "#" + "DLToInPort");
			if( edge.label.equals("connect") ) {
				m.add(idToInd.get(edge.id), DLToInPortOP, idToInd.get(edge.dest + "_" + edge.destPort));
				ObjectProperty outPortToDLOP = m.createObjectProperty(SOURCE_URL + "#" + "outPortToDL");
				m.add(idToInd.get(edge.id), outPortToDLOP, idToInd.get(edge.source + "_" + edge.sourcePort));
			}
		}
		/*
		for( Actor actor: this.actors) {
			sb.append("CREATE n={");
			sb.append("name:\""+ actor.id + "\"," );
			if (actor.vtType!=null)
				sb.append("vtType:\""+ actor.vtType + "\"," );
			if (actor.cache!=null)
				sb.append("cache:\""+ actor.cache + "\"," );
			if (actor.completed!=null)
				sb.append("completed:\""+ actor.completed + "\"," );
			if (actor.runID!=null)
				sb.append("runID:\""+ actor.runID + "\"," );
			sb.append("module:\""+ actor.module + "\"," );
			sb.append("wfID:\""+ actor.wfID + "\"," );
			sb.append("type:\""+ "activity" + "\"" );		
			sb.append("};"+"\n" );
		}
		//Add an extra node representing the workflow to the graph
		sb.append("CREATE n={");
		sb.append("name:\""+ this.wfID + "\"," );
		sb.append("wfID:\""+ this.wfID + "\"," );
		sb.append("type:\""+ "workflow" + "\"" );				
		sb.append("};"+"\n" );
		//Add runID nodes
  		for( String runIDNode: this.runIDs ) {
  			sb.append("CREATE n={");
  			sb.append("name:\""+ runIDNode + "\"," );
  			sb.append("wfID:\""+ this.wfID + "\"," );
  			sb.append("runID:\""+ runIDNode + "\"," );
  			sb.append("type:\""+ "run" + "\"" );				
  			sb.append("};"+"\n" );
  		}
		String reqLabel = null;
		// Create the edges for the 'used' and 'wasGeneratedBy' relations
		for ( Edge edge: this.edges ) {
			if( edge.label.equals("used") )
				reqLabel = "used";
			else if( edge.label.equals("genBy") )
				reqLabel = "wasGeneratedBy";
			else if( edge.label.equals("connect") )
				reqLabel = "isConnectedWith";
			else if( edge.label.equals("associatedWith") )
				reqLabel = "wasAssociatedWith";
			else if( edge.label.equals("belongsTo") )
				reqLabel = "belongsTo";
		    //out.print("START n=node:node_auto_index(name='" + edge.startId + "'), ");
		    //out.print("m=node:node_auto_index(name='" + edge.endId + "') ");
		    //out.println("CREATE n-[r:" + reqLabel + "]->m;");
		}
		*/
		
		try {
			FileOutputStream fos = new FileOutputStream(new File("tempTrace.xml"));
			m.write(fos, "RDF/XML");
			FileOutputStream out = new FileOutputStream(new File(filename));
			Model model = RDFDataMgr.loadModel("tempTrace.xml");
	        RDFDataMgr.write(out, model, RDFFormat.TURTLE_PRETTY);
		}
		catch (IOException e) {
			e.printStackTrace();
	    }
	}
	
	
	private OntModel createOntModel() {
		Model model = ModelFactory.createDefaultModel();
		OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
		//Uncomment to use file instead of the URL
		FileManager.get().getLocationMapper().addAltEntry( SOURCE_URL, SOURCE_FILE );
		Model baseOntology = FileManager.get().loadModel( SOURCE_URL );
		m.addSubModel( baseOntology );
		m.setNsPrefix( "provone", SOURCE_URL + "#" );
		return m;
	}
	
	
	public ArrayList<Actor> getActors() {
		return this.actors;
	}
	
	
	public ArrayList<Data> getData() {
		return this.dataObjs;
	}

	
	public ArrayList<Edge> getEdges() {
		return this.edges;
	}
	
	
}


