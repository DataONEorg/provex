package org.dataone.daks.pbase.provxml2neo4j;

import java.io.*;
import java.util.*;
import org.dom4j.*;


public class PROVBuilder {

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
	private HashMap<String, String> activityCache;
	private HashMap<String, String> activityCompleted;
	private HashMap<String, String> moduleVersion;
	private HashMap<String, String> modulePackage;
	private HashMap<String, String> actorModule;

	
	private ArrayList<Actor> actors;
	private ArrayList<Data> dataObjs;
	private ArrayList<Module> moduleObjs;
	private ArrayList<Edge> edges;
	private Set<String> runIDs;
    
	
	private Namespace provNS;
	private Namespace vtNS;
	private Namespace dcterms;
	
	UUID wfID;
	String wfVersion;

	
	public PROVBuilder() {
		this.dataEntities = new HashMap<String, Element>();
		this.planEntities = new HashMap<String, Element>();
		this.activities = new HashMap<String, Element>();
		this.modules = new HashMap<String, Element>();
		this.wasAssociatedWith = new HashMap<String, String>();
		this.dataEntityFullName = new HashMap<String, String>();
		this.activityFullName = new HashMap<String, String>();
		this.dataType= new HashMap<String, String>();
		this.dataDesc= new HashMap<String, String>();
		this.dataValue= new HashMap<String, String>();
		this.dataRunID= new HashMap<String, String>();
		this.activityCompleted = new HashMap<String, String>();
	    this.activityCache = new HashMap<String, String>();
		this.moduleVersion = new HashMap<String, String>();
		this.modulePackage = new HashMap<String, String>();
		this.actorModule = new HashMap<String, String>();
		
		this.runIDs = new HashSet<String>();
		this.wfID = UUID.randomUUID();
		
		this.provNS = new Namespace("prov", "http://www.w3.org/ns/prov#");
		this.vtNS = new Namespace("vt", "http://www.vistrails.org/registry.xsd");
		this.dcterms= new Namespace("dcterms", "http://purl.org/dc/terms/");
	}
	
	
	public void processDocument(Element root) {
		this.createEntitiesHT(root);
		this.createActivitiesHT(root);
		this.createWasAssociatedWithHT(root);
		this.createActivityFullNames(root);
		this.createEdges(root);
		this.createActorsList();
		this.createDataObjsList();
		this.createModuleObjsList();
	}
	
	// Create a dictionary for the entities
	private void createEntitiesHT(Element root) {
		for ( Iterator i = root.elementIterator("entity"); i.hasNext(); ) {
            
			Element entityElem = (Element) i.next();           
            String entityId = entityElem.attributeValue("id");
            QName provTypeQName = new QName("type", this.provNS);
            QName vtTypeQName = new QName("type", this.vtNS);
            QName vtDescQName=new QName("desc", this.vtNS);
            QName vtValueQName=new QName("value", this.provNS);
            QName isPartOf= new QName("isPartOf", this.dcterms);
            QName vtCacheQName=new QName("cache", this.vtNS);
            QName vtVersionQName=new QName("version", this.vtNS);
            QName vtPackageQName=new QName("package", this.vtNS);
            QName vtId=new QName("id", this.vtNS);


            Element elemProvType = entityElem.element(provTypeQName);
            Element elemProvLabel = entityElem.element("label");
            Element elemVtType = entityElem.element(vtTypeQName);
            Element elementVtDesc=entityElem.element(vtDescQName);
            Element elementVtValue=entityElem.element(vtValueQName);
            Element elementRunID= entityElem.element(isPartOf);
            Element elementCache= entityElem.element(vtCacheQName);
            Element elementVersion= entityElem.element(vtVersionQName);
            Element elementPackage= entityElem.element(vtPackageQName);
            Element elementId= entityElem.element(vtId);


            if (elemVtType!=null)
            	this.dataType.put(entityId, elemVtType.getText());    
            
            if( elemProvType != null ) {
            	if( elemProvType.getText().equals("prov:Plan") ) {
            		if (elementId!=null)
            			 this.wfVersion=elementId.getText();
             		if( elemVtType != null ) {
            			if( elemVtType.getText().equals("vt:module") || 
            					elemVtType.getText().equals("vt:workflow")  )
            				this.planEntities.put(entityId, entityElem);
            			if( elemVtType.getText().equals("vt:module") )
            				this.modules.put(entityId, entityElem);
            			if( elemProvLabel != null )
                			this.dataEntityFullName.put(entityId, 
                					entityId + "_" + elemProvLabel.getText());
                		else
                			this.dataEntityFullName.put(entityId, entityId);
            			if ( elementCache != null )
            				this.activityCache.put(entityId, elementCache.getText());
            			if (elementVersion!=null)
                            this.moduleVersion.put(entityId, elementVersion.getText());
            			if (elementPackage!=null)
            				this.modulePackage.put(entityId, elementPackage.getText());
            			if (elementVtDesc!=null)
            				this.dataDesc.put(entityId, elementVtDesc.getText());
            		}
            	}
            	else {
            		if( elemProvType.getText().equals("vt:data") ) {
            			if( elementVtDesc != null )
            				this.dataDesc.put(entityId, elementVtDesc.getText());
            			if( elementVtValue != null )
                        	this.dataValue.put(entityId, elementVtValue.getText());
            			this.dataEntities.put(entityId, entityElem);
            			if( elemProvLabel != null )
            				this.dataEntityFullName.put(entityId, 
            						entityId + "_" + elemProvLabel.getText());
            			else
            				this.dataEntityFullName.put(entityId, entityId);
            		}
            	}	
            }
            if( elementRunID != null )
            	this.dataRunID.put(entityId, elementRunID.attributeValue("ref"));
        }
	}
	
	
	// Create a dictionary for the activities
	private void createActivitiesHT(Element root) {
		for ( Iterator i = root.elementIterator("activity"); i.hasNext(); ) {
            Element activityElem = (Element) i.next();
            String activityId = activityElem.attributeValue("id");
            QName vtTypeQName = new QName("type", this.vtNS);
            QName vtDescQName=new QName("desc", this.vtNS);
            QName vtCacheQName=new QName("cached", this.vtNS);
            QName vtCompletedQName=new QName("completed", this.vtNS);
            QName dctermsIsPartOf=new QName("isPartOf", this.dcterms);
            
            Element elemVtType = activityElem.element(vtTypeQName);
            Element elementVtDesc=activityElem.element(vtDescQName);
            Element elementVtCache=activityElem.element(vtCacheQName);
            Element elementVtCompleted=activityElem.element(vtCompletedQName);
            Element elemDcterms = activityElem.element(dctermsIsPartOf);         

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
            		runIDs.add (elemDcterms.attributeValue("ref"));
            	}
            	else {
            		this.dataRunID.put(activityId,this.dataRunID.get(elemDcterms.attributeValue("ref")));
            		runIDs.add(this.dataRunID.get(elemDcterms.attributeValue("ref")));
            	}
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
	
	
	protected void createDOTFileAllRuns(String filename, String filePath) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
			out.println("digraph TheGraph {");
			out.println();
			out.println("rankdir =BT");
			out.println("node[style=\"filled\",color=\"#CCCC00\", fillcolor=\"#F6F6CC\"]");
			out.println("edge[style=dashed]");
			out.println("color= blue;");
			// Iterate over the data items and actors to create entries of the form
			// id [shape=ellipse]; on the DOT file
			for( Data data: this.dataObjs )
			    out.println(data.id + " [shape=ellipse];");
			out.println("node[style=\"filled\",color=\"#1AA4A7\", fillcolor=\"#DCF1F1\"]");
			for( Module module: this.moduleObjs ) {
				out.println("subgraph cluster_"+ module.id +" {");
				out.println("style=dotted;");			
			    for( Actor actor: this.actors ){
					if (actor.module.equals(module.id))
						out.println(actor.id + " [shape=rectangle];");
				}
				out.println("}");
			}
			// Create the edges for the 'used' and 'wasGeneratedBy' relations
			for ( Edge edge: this.edges ) {
				if( edge.label.equals("used") )
					out.println(edge.startId + " -> " + edge.endId + ";");
				else if( edge.label.equals("genBy") )
					out.println(edge.startId + " -> " + edge.endId + ";");
			}
			out.println();
			out.println( "labelloc=\"t\"");
			out.println("fontsize=25");
			out.println("labeljust=left");	
			out.println("label=\"" +filePath+"_"+ wfVersion+"\";");
			//Workflow Graph
			out.println("subgraph cluster {");
			out.println( "labelloc=\"t\"");
			out.println("label=\"" +filePath+"Workflow"+"\";");
			out.println("style=filled;");
		    out.println("color=\"#f2f2f2\";");
			out.println("node[style=\"filled\",color=\"#1AA4A7\", fillcolor=\"#bae3e4\"]");
			out.println("edge[style=solid]");
			for( Module module: this.moduleObjs )
				out.println(module.id + " [shape=rectangle];");
			for ( Edge edge: this.edges ) {
				if( edge.label.equals("connect") )
					out.println(edge.startId + " -> " + edge.endId + ";");
			}
			out.println("}");
			out.println("}");
			out.println();
	        out.close();
	    }
		catch (IOException e) {
			e.printStackTrace();
	    }
	}
	
	protected void createDOTFile(String filePath) {
		try {
			new File(filePath).mkdirs();
			for( String runIDNode: this.runIDs ) {
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath+"/"+filePath+runIDNode+".dot")));
				out.println("digraph TheGraph {");
				out.println();
				out.println("rankdir = BT");
				out.println("edge[style=dashed]");
			    out.println("node[style=\"filled\", color=\"#1AA4A7\", fillcolor=\"#DCF1F1\"]");
	  			for( Actor actor: this.actors ) {
	  				if (actor.runID.equals(runIDNode))
	  					out.println(actor.id + " [shape=rectangle];");
	  			}
	  			out.println("node[style=\"filled\",color=\"#CCCC00\", fillcolor=\"#F6F6CC\"]");
	  			//Create the edges for the 'used' and 'wasGeneratedBy' relations
	  			for ( Edge edge: this.edges ) {
	  				if (edge.runID!=null && edge.runID.equals(runIDNode)) {
	  					if( edge.label.equals("used") )
	  						out.println(edge.startId + " -> " + edge.endId + ";");
	  					else if( edge.label.equals("genBy") )
	  						out.println(edge.startId + " -> " + edge.endId + ";");
	  				}
	  			}
	  			out.println();
	  			out.println( "labelloc=\"t\"");
	  			out.println("fontsize=25");
	  			out.println("labeljust=left");	
	  			out.println("label=\"" +filePath+"_"+ wfVersion + " -- runID= "+ runIDNode+"\";");
	  			out.println("}");
	  			out.println();
	  			out.close();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
	    }
	}
	
	protected void createJSONFile(String filename) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
			StringBuilder sb = new StringBuilder();
			sb.append("{\"subgraph\": [" + "\n");
			// Iterate over the data items and actors to create entries of the form
			// id [shape=ellipse]; on the DOT file
			for( Data data: this.dataObjs ) {
				sb.append("\"(" + data.id + "){");
				sb.append("\\\"name\\\":\\\"" + data.id + "\\\"," );
				if (data.vtType!=null)
					sb.append( "\\\"vtType\\\":\\\"" + data.vtType + "\\\",");
                if (data.desc !=null)
                	sb.append("\\\"description\\\":\\\"" + data.desc + "\\\"," );
                if (data.value!=null)
                	sb.append( "\\\"value\\\":\\\"" + data.value + "\\\",");
                if (data.runID!=null)
                	sb.append("\\\"runID\\\":\\\"" + data.runID + "\\\",");
			    sb.append("\\\"wfID\\\":\\\"" + data.wfID + "\\\",");
			    sb.append("\\\"type\\\":\\\"" + "data" + "\\\"");
			    sb.append("}\""+ "\n");			
			}
			for( Actor actor: this.actors ) {
                sb.append("\"(" + actor.id + "){");
                sb.append("\\\"name\\\":\\\"" + actor.id + "\\\"," );
                if (actor.vtType!=null)
                	sb.append( "\\\"vtType\\\":\\\"" + actor.vtType + "\\\",");
                if (actor.cache!=null)
                	sb.append( "\\\"cache\\\":\\\"" + actor.cache + "\\\",");
                if (actor.completed!=null)
                	sb.append( "\\\"completed\\\":\\\"" + actor.completed + "\\\",");
                if (actor.runID!=null)
                	sb.append("\\\"runID\\\":\\\"" + actor.runID + "\\\",");
            	sb.append("\\\"wfID\\\":\\\"" + actor.wfID + "\\\",");
            	sb.append("\\\"type\\\":\\\"" + "actor" + "\\\"");
       			sb.append("}\""+ "\n");		
			}
			for( Module module: this.moduleObjs) {
                sb.append("\"(" + module.id + "){");
                sb.append("\\\"name\\\":\\\"" + module.id + "\\\"," );
                if (module.vtType!=null)
                	sb.append( "\\\"vtType\\\":\\\"" + module.vtType + "\\\",");
                if (module.cache!=null)
                	sb.append( "\\\"cache\\\":\\\"" + module.cache + "\\\",");
                if (module.desc!=null)
                	sb.append( "\\\"description\\\":\\\"" + module.desc + "\\\",");
                if (module.vtPackage!=null)
                	sb.append("\\\"package\\\":\\\"" + module.vtPackage + "\\\",");
                if (module.version!=null)
                	sb.append("\\\"version\\\":\\\"" + module.version + "\\\",");
            	sb.append("\\\"wfID\\\":\\\"" + module.wfID + "\\\",");
            	sb.append("\\\"type\\\":\\\"" + "module" + "\\\"");
       			sb.append("}\""+ "\n");		
			}
			//Add an extra node representing the workflow to the graph
			sb.append("\"(" + this.wfID + "){");
            sb.append("\\\"type\\\":\\\"" + "workflow" + "\\\"," );
            sb.append("\\\"wfID\\\":\\\"" + this.wfID + "\\\"," );
            sb.append("}\""+ "\n");		
            //Add runID nodes
  			for( String runIDNode: this.runIDs ) {
  				sb.append("\"(" + runIDNode + "){");
  				sb.append("\\\"type\\\":\\\"" + "run" + "\\\"," );
  				sb.append("\\\"wfID\\\":\\\"" + this.wfID + "\\\"," );
  				sb.append("\\\"wfID\\\":\\\"" + runIDNode+ "\\\"," );
  				sb.append("}\""+ "\n");	
  			}
              
			// Create the edges for the 'used' and 'wasGeneratedBy' relations
			for ( Edge edge: this.edges ) {
				if( edge.label.equals("used") )
					sb.append("\"(" + edge.startId + ")-[:used]->(" + edge.endId + ")\"," + "\n");
				else if( edge.label.equals("genBy") )
					sb.append("\"(" + edge.startId + ")-[:wasGeneratedBy]->(" + edge.endId + ")\"," + "\n");
				else if( edge.label.equals("connect") )
					sb.append("\"(" + edge.startId + ")-[:isConnectedWith]->(" + edge.endId + ")\"," + "\n");
				else if( edge.label.equals("associatedWith") )
					sb.append("\"(" + edge.startId + ")-[:wasAssociatedWith]->(" + edge.endId + ")\"," + "\n");
				else if( edge.label.equals("belongsTo") )
					sb.append("\"(" + edge.startId + ")-[:belongsTo]->(" + edge.endId + ")\"," + "\n");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.deleteCharAt(sb.length()-1);
			sb.append("\n");
			sb.append("]}");
	        out.println(sb.toString());
			out.close();
	    }
		catch (IOException e) {
			e.printStackTrace();
	    }
	}
	
	
	protected void createRESTCypherFile(String filename) {
		/*
		 POST to http://localhost:7474/db/data/batch
		 [ 
		 	{
     			"method" : "POST",
     			"to" : "/cypher",
     			"body" : {
       			"query" : "CREATE n={props}",
       			"params" : {"props": {"name":"node_name"}}
     			},
     			"id" : 0
   			}, ...
   			{
     			"method" : "POST",
     			"to" : "/cypher",
     			"body" : {
       			"query" : "START n=node:node_auto_index(name={name1}), m=node:node_auto_index(name={name2}) CREATE n-[r:USED/WASGENBY]-m",
       			"params" : {"name1":"name1_val", "name2":"name2_val"}
     			},
     			"id" : k
   			}, ...
		] 
		 */
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
			StringBuilder sb = new StringBuilder();
			String nl = System.getProperty("line.separator");
			sb.append("[");
			int reqId = 0;
			// Iterate over the data items and actors
			for( Data data: this.dataObjs ) {
			    sb.append(nl + "\t" + "{" + nl + "\t\t" + "\"method\"" + " : " + "\"POST\"" + ",");
			    sb.append(nl + "\t\t" + "\"to\"" + " : " + "\"/cypher\"" + ",");
			    sb.append(nl + "\t\t" + "\"body\"" + " : " + "{");
			    sb.append(nl + "\t\t" + "\"query\"" + " : " + "\"CREATE n={props}\"" + ",");
			    sb.append(nl + "\t\t" + "\"params\"" + " : " + "{" + "\"props\"" + ": {");
			    sb.append("\"name\"" + ":" + "\"" + data.id + "\",");
			    if (data.vtType!=null)
			    	sb.append("\"vtType\"" + ":" + "\"" + data.vtType + "\",");
			    if (data.desc!=null)
			    	sb.append("\"description\"" + ":" + "\"" + data.desc + "\",");
			    if (data.value!=null)
			    	sb.append("\"value\"" + ":" + "\"" + data.value + "\"," );
			    if (data.runID!=null)
			    	sb.append("\"runID\"" + ":" + "\"" + data.runID + "\"," );
		    	sb.append("\"wfID\"" + ":" + "\"" + data.wfID + "\"" );
		    	sb.append("\"type\"" + ":" + "\"" + "data" + "\"," );

			    sb.append ("}");
			    sb.append(nl + "\t\t" + "},");
			    sb.append(nl + "\t\t" + "\"id\"" + " : " + reqId);
			    sb.append(nl + "\t" + "}");
			    sb.append(",");
			    reqId++;
			}
			for( Actor actor: this.actors ) {
				sb.append(nl + "\t" + "{" + nl + "\t\t" + "\"method\"" + " : " + "\"POST\"" + ",");
			    sb.append(nl + "\t\t" + "\"to\"" + " : " + "\"/cypher\"" + ",");
			    sb.append(nl + "\t\t" + "\"body\"" + " : " + "{");
			    sb.append(nl + "\t\t" + "\"query\"" + " : " + "\"CREATE n={props}\"" + ",");
			    sb.append(nl + "\t\t" + "\"params\"" + " : " + "{" + "\"props\"" + ": {");
			    sb.append("\"name\"" + ":" + "\"" + actor.id + "\",");
			    if (actor.vtType!=null)
			    	sb.append("\"vtType\"" + ":" + "\"" + actor.vtType + "\",");
			    if (actor.cache!=null)
			    	sb.append("\"cache\"" + ":" + "\"" + actor.cache + "\"," );
			    if (actor.completed!=null)
			    	sb.append("\"completed\"" + ":" + "\"" + actor.completed + "\"," );
			    if (actor.runID!=null)
			    	sb.append("\"runID\"" + ":" + "\"" + actor.runID + "\"," );
		    	sb.append("\"wfID\"" + ":" + "\"" + actor.wfID + "\"," );
		    	sb.append("\"type\"" + ":" + "\"" + "activity" + "\"" );
			    sb.append ("}");
			    sb.append(nl + "\t\t" + "},");
			    sb.append(nl + "\t\t" + "\"id\"" + " : " + reqId);
			    sb.append(nl + "\t" + "}");
			    sb.append(",");
			    reqId++;
			}
			for( Module module: this.moduleObjs ) {
				sb.append(nl + "\t" + "{" + nl + "\t\t" + "\"method\"" + " : " + "\"POST\"" + ",");
			    sb.append(nl + "\t\t" + "\"to\"" + " : " + "\"/cypher\"" + ",");
			    sb.append(nl + "\t\t" + "\"body\"" + " : " + "{");
			    sb.append(nl + "\t\t" + "\"query\"" + " : " + "\"CREATE n={props}\"" + ",");
			    sb.append(nl + "\t\t" + "\"params\"" + " : " + "{" + "\"props\"" + ": {");
			    sb.append("\"name\"" + ":" + "\"" + module.id + "\",");
			    if (module.vtType!=null)
			    	sb.append("\"vtType\"" + ":" + "\"" + module.vtType + "\",");
			    if (module.cache!=null)
			    	sb.append("\"cache\"" + ":" + "\"" + module.cache + "\"," );
			    if (module.desc!=null)
			    	sb.append("\"description\"" + ":" + "\"" + module.desc + "\"," );
			    if (module.vtPackage!=null)
			    	sb.append("\"package\"" + ":" + "\"" + module.vtPackage + "\"," );
			    if (module.version!=null)
			    	sb.append("\"version\"" + ":" + "\"" + module.version + "\"," );
		    	sb.append("\"wfID\"" + ":" + "\"" + module.wfID + "\"," );
		    	sb.append("\"type\"" + ":" + "\"" + "module" + "\"" );
			    sb.append ("}");
			    sb.append(nl + "\t\t" + "},");
			    sb.append(nl + "\t\t" + "\"id\"" + " : " + reqId);
			    sb.append(nl + "\t" + "}");
			    sb.append(",");
			    reqId++;
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
				sb.append(nl + "\t" + "{" + nl + "\t\t" + "\"method\"" + " : " + "\"POST\"" + ",");
			    sb.append(nl + "\t\t" + "\"to\"" + " : " + "\"/cypher\"" + ",");
			    sb.append(nl + "\t\t" + "\"body\"" + " : " + "{");
			    
			    sb.append(nl + "\t\t" + "\"query\"" + " : " + "\"START n=node:node_auto_index(name={name1})");
			    sb.append(", m=node:node_auto_index(name={name2}) " );
			    //sb.append("CREATE n-[r:USEDGENBY{label:{l}}]->m\"" + ",");
			    sb.append("CREATE n-[r:" + reqLabel + "]->m\"" + ",");
			    sb.append(nl + "\t\t" + "\"params\"" + " : " + "{" + "\"name1\"" + ":");
			    sb.append("\"" + edge.startId + "\"" + ", ");
			    sb.append("\"name2\"" + ":");
			    //sb.append("\"" + edge.endId + "\"" + ", ");
			    //sb.append("\"l\"" + ":" + "\"" + reqLabel + "\"" + "}");
			    sb.append("\"" + edge.endId + "\"" + "}");
			    sb.append(nl + "\t\t" + "},");
			    sb.append(nl + "\t\t" + "\"id\"" + " : " + reqId);
			    sb.append(nl + "\t" + "}");
			    sb.append(",");
			    reqId++;
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(nl + "]" + nl);
	        out.println(sb.toString());
			out.close();
	    }
		catch (IOException e) {
			e.printStackTrace();
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
			//out.println("commit");
			//out.println("exit");
	        out.println();
			out.close();
	    }
		catch (IOException e) {
			e.printStackTrace();
	    }
	}
	
	private void createActorsList() {
		this.actors = new ArrayList<Actor>();
		for (String key : this.activities.keySet()) {
			String nodeId = this.activityFullName.get(key);
			Actor actor = new Actor();
			actor.id = nodeId;		
			actor.vtType= this.dataType.get(key);
			actor.cache=this.activityCache.get(key);
			actor.completed=this.activityCompleted.get(key);
			actor.module=this.actorModule.get(key);
			actor.runID=this.dataRunID.get(key);
		    actor.wfID= this.wfID;
			this.actors.add(actor);
		}
	}
	
	
	private void createDataObjsList() {
		this.dataObjs = new ArrayList<Data>();
		for (String key : this.dataEntities.keySet()) {
			String nodeId = this.dataEntityFullName.get(key);
			Data data = new Data();
			data.id = nodeId;		
		    data.vtType=this.dataType.get(key);
		    data.desc=this.dataDesc.get(key);
		    data.value= this.dataValue.get(key);
		    data.runID=this.dataRunID.get(key);
		    data.wfID= this.wfID;
			dataObjs.add(data);
		}
	}
	
	private void createModuleObjsList() {
		this.moduleObjs = new ArrayList<Module>();
		for (String key : this.modules.keySet()) {
			String nodeId = this.dataEntityFullName.get(key);
			Module module = new Module();
			module.id = nodeId;		
		    module.vtType=this.dataType.get(key);
		    module.desc=this.dataDesc.get(key);
            module.vtPackage=this.modulePackage.get(key);
            module.version=this.moduleVersion.get(key);
            module.cache=this.activityCache.get(key);
		    module.wfID= this.wfID;
			moduleObjs.add(module);
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
			edge.runID=dataRunID.get(actRef);
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
			edge.runID=dataRunID.get(actRef);
			edge.label = "genBy";
			edge.startId = startNodeId;
			edge.endId = endNodeId;
			this.edges.add(edge);
		}
		for ( Iterator i = root.elementIterator("connection"); i.hasNext(); ) {
			Element elemconnect = (Element) i.next();
            QName vtSourceName = new QName("source", this.vtNS);
			Element elemSource = elemconnect.element(vtSourceName);
			String source = elemSource.getText();
            QName vtDestName = new QName("dest", this.vtNS);
			Element elemDest = elemconnect.element(vtDestName);
			String dest = elemDest.getText();
			String startNodeId = this.dataEntityFullName.get(source);
			String endNodeId = this.dataEntityFullName.get(dest);
			Edge edge = new Edge();
			edge.label = "connect";
			edge.startId = startNodeId;
			edge.endId = endNodeId;
			this.edges.add(edge);
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
			edge.runID=dataRunID.get(destNodeId);
			this.edges.add(edge);
		}
		//create edges between workflow and its runs
		for( String runIDNode: this.runIDs ) {
			Edge edge = new Edge();
			edge.startId=this.wfID.toString();
			edge.endId=runIDNode;
			edge.label = "belongsTo";
			this.edges.add(edge);
			
		}

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


