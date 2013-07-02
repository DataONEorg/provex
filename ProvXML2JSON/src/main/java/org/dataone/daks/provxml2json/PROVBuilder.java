package org.dataone.daks.provxml2json;

import java.io.*;
import java.util.*;
import org.dom4j.*;


public class PROVBuilder {

	private HashMap<String, Element> dataEntities;
	private HashMap<String, Element> planEntities;
	private HashMap<String, Element> activities;
	private HashMap<String, String> wasAssociatedWith;
	private HashMap<String, String> dataEntityFullName;
	private HashMap<String, String> activityFullName;
	
	private ArrayList<Actor> actors;
	private ArrayList<Data> dataObjs;
	private ArrayList<Edge> edges;
	
	private Namespace provNS;
	private Namespace vtNS;
	
	
	public PROVBuilder() {
		this.dataEntities = new HashMap<String, Element>();
		this.planEntities = new HashMap<String, Element>();
		this.activities = new HashMap<String, Element>();
		this.wasAssociatedWith = new HashMap<String, String>();
		this.dataEntityFullName = new HashMap<String, String>();
		this.activityFullName = new HashMap<String, String>();
		this.provNS = new Namespace("prov", "http://www.w3.org/ns/prov#");
		this.vtNS = new Namespace("vt", "http://www.vistrails.org/registry.xsd");
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
            Element elemProvType = entityElem.element(provTypeQName);
            Element elemProvLabel = entityElem.element("label");
            QName vtTypeQName = new QName("type", this.vtNS);
            Element elemVtType = entityElem.element(vtTypeQName);
            if( elemProvType != null ) {
            	if( elemProvType.getText().equals("prov:Plan") ) {
            		if( elemVtType != null ) {
            			if( elemVtType.getText().equals("vt:module") || 
            					elemVtType.getText().equals("vt:workflow")  )
            				this.planEntities.put(entityId, entityElem);
            		}
            	}
            	else {
            		if( elemProvType.getText().equals("vt:data") ) {
            			this.dataEntities.put(entityId, entityElem);
            			if( elemProvLabel != null )
            				this.dataEntityFullName.put(entityId, 
            						entityId + "_" + elemProvLabel.getText());
            			else
            				this.dataEntityFullName.put(entityId, entityId);
            		}
            	}
            }
        }
	}
	
	
	// Create a dictionary for the activities
	private void createActivitiesHT(Element root) {
		for ( Iterator i = root.elementIterator("activity"); i.hasNext(); ) {
            Element activityElem = (Element) i.next();
            String activityId = activityElem.attributeValue("id");
            QName vtTypeQName = new QName("type", this.vtNS);
            Element elemVtType = activityElem.element(vtTypeQName);
            if( ! elemVtType.getText().equals("vt:wf_exec") )
            	this.activities.put(activityId, activityElem);
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
			    sb.append("\"(" + data.id + ")\"," + "\n");
			for( Actor actor: this.actors )
				sb.append("\"(" + actor.id + ")\"," + "\n");
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
	
	
	private void createActorsList() {
		this.actors = new ArrayList<Actor>();
		for (String key : this.activities.keySet()) {
			String nodeId = this.activityFullName.get(key);
			Actor actor = new Actor();
			actor.id = nodeId;			
			this.actors.add(actor);
		}
	}
	
	
	private void createDataObjsList() {
		this.dataObjs = new ArrayList<Data>();
		for (String key : this.dataEntities.keySet()) {
			String nodeId = this.dataEntityFullName.get(key);
			Data data = new Data();
			data.id = nodeId;			
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


