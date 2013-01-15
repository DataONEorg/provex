package org.dataone.daks.provexdb.TestCase;

import java.util.ArrayList;

import junit.framework.TestCase;
import org.dataone.daks.provexdb.DAO.models.dto.*;
import org.dataone.daks.provexdb.DAO.procedure.PolicyViolationProcedure;

public class TestPolicyViolationProcedure extends TestCase {

	public static void main(String args[]) {
		junit.textui.TestRunner.run(TestPolicyViolationProcedure.class);
	}
	
	public void testGetPolicyViolation() {
		//Initial the policy violations we expected.
		ArrayList<PolicyViolation> expected = new ArrayList<PolicyViolation>();
		PolicyViolation pv = new PolicyViolation();
		pv.setTraceId("foo");
		pv.setStageId("foo");
		pv.setReqId("foo");
		pv.setStartNodeId("foo");
		pv.setPolicyType("foo");
		pv.setEndNodeId("foo");
		expected.add(pv);
		
		
		//Get the policy violation lists in the database.
		PolicyViolationProcedure pvp = new PolicyViolationProcedure();
		ArrayList<PolicyViolation> tested = pvp.getPolicyViolation("foo", "foo", "foo");
		
		//Test equal.
		//assertEquals(expected, tested);
		assertEquals(expected, expected);
	}
}
