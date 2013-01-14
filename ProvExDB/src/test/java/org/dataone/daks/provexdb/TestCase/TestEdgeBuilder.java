package org.dataone.daks.provexdb.TestCase;

import java.util.ArrayList;

import org.dataone.daks.provexdb.DAO.BuilderWithXML.EdgeBuilder;
import org.dataone.daks.provexdb.DAO.BuilderWithXML.XML_DAO_Mapper;
import org.dataone.daks.provexdb.DAO.models.dto.Edge;

import junit.framework.TestCase;

public class TestEdgeBuilder extends TestCase{
	public void testGetEdges(){
		EdgeBuilder eb = new EdgeBuilder();
		ArrayList<Edge> a = eb.getEdges(XML_DAO_Mapper.getEdgesRootNode());
		
		
	} 
	
	public static void main(String args[]){
		junit.textui.TestRunner.run(TestDataProcedure.class);
	}
	
}
