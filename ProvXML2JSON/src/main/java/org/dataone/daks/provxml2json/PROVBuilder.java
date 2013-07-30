package org.dataone.daks.provxml2json;

import java.io.*;
import java.util.*;

import org.dom4j.*;

import com.sun.org.apache.xml.internal.utils.NameSpace;

import sun.awt.image.OffScreenImage;


public class PROVBuilder {

	private HashMap<String, Element> dataEntities;
	private HashMap<String, Element> planEntities;
	private HashMap<String, Element> activities;
	private HashMap<String, String> wasAssociatedWith;
	private HashMap<String, String> dataEntityFullName;
	private HashMap<String, String> activityFullName;
	private HashMap<String, String> dataType;
	private HashMap<String, String> dataVersion;
	private HashMap<String, String> dataDesc;
	private HashMap<String, String> dataValue;
	private HashMap<String, String> dataRunID;
	private HashMap<String, String> activityType;
	private HashMap<String, String> activityVersion;
	private HashMap<String, String> activityDesc;
	private HashMap<String, String> activityCache;
	private HashMap<String, String> activityCompleted;
	private HashMap<String, String> activityRunID;
	
	private ArrayList<Actor> actors;
	private ArrayList<Data> dataObjs;
	private ArrayList<Edge> edges;
	
	private Namespace provNS;
	private Namespace vtNS;
	private Namespace dcterms;
	
	
	public PROVBuilder() {
		this.dataEntities = new HashMap<String, Element>();
		this.planEntities = new HashMap<String, Element>();
		this.activities = new HashMap<String, Element>();
		this.wasAssociatedWith = new HashMap<String, String>();
		this.dataEntityFullName = new HashMap<String, String>();
		this.activityFullName = new HashMap<String, String>();
		this.dataType= new HashMap<String, String>();
		this.dataVersion= new HashMap<String, String>();
		this.dataDesc= new HashMap<String, String>();
		this.dataValue= new HashMap<String, String>();
		this.dataRunID= new HashMap<String, String>();
		this.activityType= new HashMap<String, String>();
		this.activityVersion= new HashMap<String, String>();
		this.activityDesc= new HashMap<String, String>();
		this.activityCache = new HashMap<String, String>();
		this.activityCompleted = new HashMap<String, String>();
		this.activityRunID = new HashMap<String, String>();

		
		this.provNS = new Namespace("prov", "http://www.w3.org/ns/prov#");
		this.vtNS = new Namespace("vt", "http://www.vistrails.org/registry.xsd");
		this.dcterms= new Namespace("dcterms", "http://purl.org/dc/terms/");
	}
	
	
	public void processDocument(Element root) {
		this.createEntitiesHT(root);
		this.createActivitiesHT(root);
		this.createWasAssociatedWithHT(root);
		this.createActivityFullNames(root);
		this.createActorsList();
		this.createDataObjsList();
		this.createEdges(root);
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
            QName vtVersionQName=new QName("version", this.vtNS);
            QName isPartof= new QName("isPartOf", this.dcterms);

            Element elemProvType = entityElem.element(provTypeQName);
            Element elemProvLabel = entityElem.element("label");
            Element elemVtType = entityElem.element(vtTypeQName);
            Element elementVtDesc=entityElem.element(vtDescQName);
            Element elementVtValue=entityElem.element(vtValueQName);
            Element elementVtVersion=entityElem.element(vtVersionQName);
            Element elementRunID= entityElem.element(isPartof);
            if (elemVtType!=null)
            	this.dataType.put(entityId, elemVtType.getText());    
            
            if( elemProvType != null ) {
            	if( elemProvType.getText().equals("prov:Plan") ) {
            	     if (elementVtDesc!=null)
                       	this.dataDesc.put(entityId, elementVtDesc.getText());
                     if (elementVtVersion!=null)
                       	this.dataVersion.put(entityId, elementVtVersion.getText());
            		if( elemVtType != null ) {
            			if( elemVtType.getText().equals("vt:module") || 
            					elemVtType.getText().equals("vt:workflow")  )
            				this.planEntities.put(entityId, entityElem);
            		}
            	}
            	else {
            		if( elemProvType.getText().equals("vt:data") ) {
            			if (elementVtValue!=null)
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
         if (elementRunID!=null)
         	this.dataRunID.put(entityId, elementVtVersion.getText());
        }
	}
	// Create a dictionary for the activities
	private void createActivitiesHT(Element root) {
		for ( Iterator i = root.elementIterator("activity"); i.hasNext(); ) {
            Element activityElem = (Element) i.next();
            String activityId = activityElem.attributeValue("id");
            QName vtTypeQName = new QName("type", this.vtNS);
            QName vtDescQName=new QName("desc", this.vtNS);
            QName vtCacheQName=new QName("Cache", this.vtNS);
            QName vtCompletedQName=new QName("completed", this.vtNS);
            QName vtVersionQName=new QName("version", this.vtNS);
            QName isPartOf=new QName("isPartOf", this.dcterms);
            
            Element elemVtType = activityElem.element(vtTypeQName);
            Element elementVtDesc=activityElem.element(vtDescQName);
            Element elementVtCache=activityElem.element(vtCacheQName);
            Element elementVtCompleted=activityElem.element(vtCompletedQName);
            Element elementVtVersion=activityElem.element(vtVersionQName);
            Element elementRunID= activityElem.element(isPartOf);


            this.activityType.put(activityId, elemVtType.getText());  
            if( ! elemVtType.getText().equals("vt:wf_exec") )
            	this.activities.put(activityId, activityElem);
            if (elementVtDesc != null)
            	this.activityDesc.put(activityId, elementVtDesc.getText());
            if (elementVtCache != null)
            	this.activityCache.put(activityId, elementVtCache.getText());
            if (elementVtCompleted != null)
            	this.activityCompleted.put(activityId, elementVtCompleted.getText());
            if (elementVtVersion != null)
            	this.activityVersion.put(activityId, elementVtVersion.getText());
            if (elementRunID!=null)
            	this.activityRunID.put(activityId, elementRunID.getText());
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
		        Element elemVtDesc = planEntity.element("desc");
		        Element elemProvLabel = planEntity.element("label");
		        if( elemVtDesc != null )
					name = elemVtDesc.getText();
				else if( elemProvLabel != null )
					name = elemProvLabel.getText();
				this.activityFullName.put(key, key + "_" + name);
			}
			else {
				this.activityFullName.put(key, key);
			}
		}
	}
	
	
	protected void createDOTFile(String filename) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
			out.println("digraph TheGraph {");
			out.println();
			// Iterate over the data items and actors to create entries of the form
			// id [shape=ellipse]; on the DOT file
			for( Data data: this.dataObjs )
			    out.println(data.id + " [shape=ellipse];");
			for( Actor actor: this.actors )
				out.println(actor.id + " [shape=rectangle];");
			// Create the edges for the 'used' and 'wasGeneratedBy' relations
			for ( Edge edge: this.edges ) {
				if( edge.label.equals("used") )
					out.println(edge.startId + " -> " + edge.endId + ";");
				else if( edge.label.equals("genBy") )
					out.println(edge.startId + " -> " + edge.endId + ";");
			}
			out.println();
			out.println("}");
			out.println();
	        out.close();
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
			for( Data data: this.dataObjs )
                sb.append("\"(" + data.id + "){\\\"name\\\":\\\"" + data.id + "\\\"," + "\\\"version\\\":\\\"" + data.version + "\\\"," + "\\\"type\\\":\\\"" + data.type + "\\\"," +"\\\"desc\\\":\\\"" + data.desc + "\\\"," + "\\\"value\\\":\\\"" + data.value + "\\\"," + "\\\"runID\\\":\\\"" + data.runID + "\\\"}\""+ "\n");			
			for( Actor actor: this.actors )
                sb.append("\"(" + actor.id + "){\\\"name\\\":\\\"" + actor.id + "\\\"," + "\\\"version\\\":\\\"" + actor.version + "\\\"," + "\\\"type\\\":\\\"" + actor.type + "\\\"," +"\\\"desc\\\":\\\"" + actor.desc + "\\\"," + "\\\"Cache\\\":\\\"" + actor.cache + "\\\"," + "\\\"Completed\\\":\\\"" + actor.completed+"\\\"," + "\\\"runID\\\":\\\"" + actor.runID + "\\\"}\""+ "\n");			
			// Create the edges for the 'used' and 'wasGeneratedBy' relations
			for ( Edge edge: this.edges ) {
				if( edge.label.equals("used") )
					sb.append("\"(" + edge.startId + ")-[:used]->(" + edge.endId + ")\"," + "\n");
				else if( edge.label.equals("genBy") )
					sb.append("\"(" + edge.startId + ")-[:wasGeneratedBy]->(" + edge.endId + ")\"," + "\n");
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
			    sb.append("\"version\"" + ":" + "\"" + data.version + "\",");
			    sb.append("\"type\"" + ":" + "\"" + data.type + "\",");
			    sb.append("\"desc\"" + ":" + "\"" + data.desc + "\",");
			    sb.append("\"value\"" + ":" + "\"" + data.value + "\"," );
			    sb.append("\"runID\"" + ":" + "\"" + data.runID + "\"" );
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
			    sb.append("\"version\"" + ":" + "\"" + actor.version + "\"," );
			    sb.append("\"type\"" + ":" + "\"" + actor.type + "\",");
			    sb.append("\"desc\"" + ":" + "\"" + actor.desc + "\"," );
			    sb.append("\"Cache\"" + ":" + "\"" + actor.cache + "\"," );
			    sb.append("\"Cache\"" + ":" + "\"" + actor.completed + "\"," );
			    sb.append("\"Cache\"" + ":" + "\"" + actor.runID + "\"" );
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
					reqLabel = "USED";
				else if( edge.label.equals("genBy") )
					reqLabel = "WASGENBY";
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
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
			// Iterate over the data items and actors
			for( Data data: this.dataObjs )
			    out.println("CREATE n={name:\"" + data.id + "\","+ "version:\"" + data.version + "\","+"type:\"" + data.type + "\","+"desc:\"" + data.desc + "\","+"value:\"" + data.value + "\","+ "runID:\"" + data.runID + "\""+"}" );
			for( Actor actor: this.actors )
				out.println("CREATE n={name:\"" + actor.id + "\","+ "version:\"" + actor.version + "\","+"type:\"" + actor.type + "\","+"desc:\"" + actor.desc + "\","+"Cache:\"" + actor.cache + "\","+ "completed:\"" + actor.completed + "\","+ "runID:\"" + actor.runID + "\""+"}" );
			String reqLabel = null;
			// Create the edges for the 'used' and 'wasGeneratedBy' relations
			for ( Edge edge: this.edges ) {
				if( edge.label.equals("used") )
					reqLabel = "USED";
				else if( edge.label.equals("genBy") )
					reqLabel = "WASGENBY";
			    out.print("START n=node:node_auto_index(name='" + edge.startId + "'), ");
			    out.print("m=node:node_auto_index(name='" + edge.endId + "') ");
			    out.println("CREATE n-[r:" + reqLabel + "]->m");
			}
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
			actor.version=this.activityVersion.get(key);
			actor.type= this.activityType.get(key);
			actor.desc=this.activityDesc.get(key);
			actor.cache=this.activityCache.get(key);
			actor.completed=this.activityCompleted.get(key);
			actor.runID=this.activityRunID.get(key);
			this.actors.add(actor);
		}
	}
	
	
	private void createDataObjsList() {
		this.dataObjs = new ArrayList<Data>();
		for (String key : this.dataEntities.keySet()) {
			String nodeId = this.dataEntityFullName.get(key);
			Data data = new Data();
			data.id = nodeId;		
		    data.version=this.dataVersion.get(key);
		    data.type=this.dataType.get(key);
		    data.desc=this.dataDesc.get(key);
		    data.value= this.dataValue.get(key);
		    data.runID=this.dataRunID.get(key);
			dataObjs.add(data);
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
			edge.label = "genBy";
			edge.startId = startNodeId;
			edge.endId = endNodeId;
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


