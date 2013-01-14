package org.dataone.daks.provexdb.DAO.BuilderWithXML;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import org.dataone.daks.provexdb.DAO.models.dto.Actor;

public class ActorBuilder {

	public ArrayList<Actor> getActors(Element processes){
		ArrayList<Actor> objects = new ArrayList<Actor>();		
		List<Node> nodes = processes.elements("process");
		for(Node node: nodes){
			Element actNode = (Element)node;
			Attribute idAttr = actNode.attribute("id");
			String nodeId = idAttr.getText();
			Actor actor = new Actor();
			actor.setNodeId(nodeId);			
			objects.add(actor);
		}
		return objects;
	}

}
