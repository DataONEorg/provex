package org.dataone.daks.provexdb.TestCase;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.dataone.daks.provexdb.DAO.models.dto.Edge;
import org.dataone.daks.provexdb.DAO.procedure.EdgeProcedure;

public class TestEdgeProcedure extends TestCase{

	public static void main(String args[]) {
		junit.textui.TestRunner.run(TestEdgeProcedure.class);
	}

	public void testSaveTrace() {
		//Initial edges to be inserted.
		ArrayList<Edge> edges = new ArrayList<Edge>();
		for (int i = 0; i < 10; i++) {
			Edge e1 = new Edge();
			e1.setTraceId("foo");
			e1.setStartNodeId("foo"+i);
			e1.setEndNodeId("foo"+i);
			e1.setEdgeLabel("foo"+i);
			e1.setEdgeType("foo"+i);
			edges.add(e1);
		}
		
		//test.
		EdgeProcedure ep = new EdgeProcedure();
		ep.saveTrace(edges, "foo");
	}
	
	public void testGetTrace(){
		//Initial edges expected.
		ArrayList<Edge> expected = new ArrayList<Edge>();
		for (int i = 0; i < 10; i++) {
			Edge e1 = new Edge();
			e1.setTraceId("foo");
			e1.setStartNodeId("foo"+i);
			e1.setEndNodeId("foo"+i);
			e1.setEdgeLabel("foo"+i);
			e1.setEdgeType("foo"+i);
			expected.add(e1);
		}
		
		//The edged we got.
		EdgeProcedure ep = new EdgeProcedure();
		ArrayList<Edge> tested = ep.getTrace("foo");
		
		//Test equal.
		assertEquals(expected, expected);	
	}

}
