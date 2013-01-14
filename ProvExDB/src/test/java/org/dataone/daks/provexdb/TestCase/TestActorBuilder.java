package org.dataone.daks.provexdb.TestCase;

import java.util.ArrayList;

import junit.framework.TestCase;
import org.dataone.daks.provexdb.DAO.BuilderWithXML.ActorBuilder;
import org.dataone.daks.provexdb.DAO.BuilderWithXML.XML_DAO_Mapper;
import org.dataone.daks.provexdb.DAO.models.dto.*;

public class TestActorBuilder extends TestCase{
	
	public void testGetActors(){
		//Expected Data
		ArrayList<Actor> expect = new ArrayList<Actor>();
		for(int i=0;i<20;i++){
			Actor a = new Actor();
			a.setNodeId("p"+i);
			expect.add(a);
		}
		
		//Test Data
		ActorBuilder a = new ActorBuilder();
		ArrayList<Actor> test = a.getActors(XML_DAO_Mapper.getActorRootNode());
		
		//Test
		assertEquals(test,expect);
	}
	
	public static void main(String args[]){
		junit.textui.TestRunner.run(TestActorProcedure.class);
	}


}
