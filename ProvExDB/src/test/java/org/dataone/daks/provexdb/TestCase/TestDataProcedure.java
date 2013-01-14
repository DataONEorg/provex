package org.dataone.daks.provexdb.TestCase;

import org.dataone.daks.provexdb.DAO.dbconfig.DatabaseConnector;
import org.dataone.daks.provexdb.DAO.models.dto.Data;
import org.dataone.daks.provexdb.DAO.procedure.DataProcedure;

import junit.framework.TestCase;

public class TestDataProcedure extends TestCase{
	
	public void testGetDataInfo(){
		//The data we expected.
		Data expected = new Data();
		expected.setNodeId("ai1");
		expected.setNodeType("d");
		expected.setNodeDesc("Anatomy Image 1");
		expected.setValType("v");
		expected.setVal("No value for now");
		
		//The data we get.
		DataProcedure dp = new DataProcedure();
		Data tested = dp.getDataInfo("ai1");
		
		//Test.
		assertEquals(expected, tested);
	}
	
	public static void main(String args[]){
		junit.textui.TestRunner.run(TestDataProcedure.class);
	}


}
