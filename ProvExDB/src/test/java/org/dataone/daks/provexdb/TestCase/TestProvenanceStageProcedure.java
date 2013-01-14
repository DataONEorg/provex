package org.dataone.daks.provexdb.TestCase;

import java.util.ArrayList;

import org.dataone.daks.provexdb.DAO.models.dto.Edge;
import org.dataone.daks.provexdb.DAO.procedure.ProvenanceStageProcedure;

import junit.framework.TestCase;

public class TestProvenanceStageProcedure extends TestCase {

	public static void main(String args[]) {
		junit.textui.TestRunner.run(TestProvenanceStageProcedure.class);
	}
	
	public static void testGetTrace(){
		//Print out the edges and check with the number of database tuples..
		
		ProvenanceStageProcedure psp = new ProvenanceStageProcedure();
		ArrayList<Edge> edges = psp.getTrace("1", "1", "1");
		for( Edge e: edges){
			assertEquals(e.getTraceId(),"1");
		}
		assertEquals(21, edges.size());
	}

}
