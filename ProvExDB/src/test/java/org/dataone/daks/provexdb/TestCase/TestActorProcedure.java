package org.dataone.daks.provexdb.TestCase;

import org.dataone.daks.provexdb.DAO.models.dao.mysql.ActorDaoImpl;
import org.dataone.daks.provexdb.DAO.models.dto.Actor;
import org.dataone.daks.provexdb.DAO.procedure.ActorProcedure;

import junit.framework.TestCase;

public class TestActorProcedure extends TestCase {

	public void testGetActorInfo() {
		
		//The Actor we get.
		ActorProcedure ap = new ActorProcedure();
		Actor tested = ap.getActorInfo("aw1");
		
		//The Actor we expected.
		Actor expected = new Actor();
		expected.setNodeId("aw1");
		expected.setNodeType("i");
		expected.setNodeDesc("Align Warp 1");
		expected.setValType("v");
		expected.setVal("No value for now");
		expected.setActorId("aw");
		
		
		//Test.
		assertEquals(expected, tested);
	}
	
	public static void main(String args[]){
		junit.textui.TestRunner.run(TestActorProcedure.class);
	}

}
