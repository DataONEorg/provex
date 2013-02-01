package org.dataone.daks.provexdb.DAO.BuilderWithXML;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

import org.dataone.daks.provexdb.DAO.models.dto.Edge;

public class EdgeBuilder {
	public ArrayList<Edge> getEdges(Element edgeRoot) {
		ArrayList<Edge> edgeObjects = new ArrayList<Edge>();

		// Get nodes whose nodeType is 'used'. That is data node is start node
		// and actor node is end node.
		//
		List<Node> usedNodes = edgeRoot.elements("used");
		for (Node node : usedNodes) {
			Element used = (Element) node;
			Element data = used.element("cause");
			Element actor = used.element("effect");
			
			
			Edge edge = new Edge();
			edge.setEdgeType("used");
			
			//data --> startNode; actor --> endNode
			String startNodeId = data.attribute("id").getText();
			String endNodeId = actor.attribute("id").getText();
			
			edge.setStartNodeId(startNodeId);
			edge.setEndNodeId(endNodeId);
			
			edgeObjects.add(edge);
		}
		
		//For node whose nodeType is 'genBy', the start node is actor and the end node is data.
		List<Node> genByNodes = edgeRoot.elements("wasGeneratedBy");
		for (Node node : genByNodes) {
			Element genBy = (Element) node;
			Element data = genBy.element("effect");
			Element actor = genBy.element("cause");
			
			
			Edge edge = new Edge();
			edge.setEdgeType("genBy");
			
			//actor --> startNode; data --> endNode
			String startNodeId = actor.attribute("id").getText();
			String endNodeId = data.attribute("id").getText();
			
			edge.setStartNodeId(startNodeId);
			edge.setEndNodeId(endNodeId);
			
			edgeObjects.add(edge);
		}
		
		return edgeObjects;
	}
}
