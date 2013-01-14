package org.dataone.daks.provexdb.DAO.BuilderWithXML;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;


import org.dataone.daks.provexdb.DAO.models.dto.Data;

public class DataBuilder {

	public ArrayList<Data> getData(Element dataRoot){
		ArrayList<Data> dataObjects = new ArrayList<Data>();		
		List<Node> nodes = dataRoot.elements("artifact");
		for(Node node: nodes){
			Element actNode = (Element)node;
			Attribute idAttr = actNode.attribute("id");
			String nodeId = idAttr.getText();
			Data data = new Data();
			data.setNodeId(nodeId);			
			dataObjects.add(data);
		}
		return dataObjects;
	}
}
