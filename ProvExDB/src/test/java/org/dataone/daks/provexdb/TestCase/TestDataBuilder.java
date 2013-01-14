package org.dataone.daks.provexdb.TestCase;

import java.util.ArrayList;

import org.dataone.daks.provexdb.DAO.BuilderWithXML.DataBuilder;
import org.dataone.daks.provexdb.DAO.BuilderWithXML.XML_DAO_Mapper;
import org.dataone.daks.provexdb.DAO.models.dto.Data;

import junit.framework.TestCase;

public class TestDataBuilder extends TestCase{
	public void testGetData(){
		//Expected Data
		ArrayList<Data> expect = new ArrayList<Data>();
		for(int i=0;i<79;i++){
			Data data = new Data();
			data.setNodeId("a"+i);
			expect.add(data);
		}
		
		//Test Data
		DataBuilder datas = new DataBuilder();
		ArrayList<Data> test = datas.getData(XML_DAO_Mapper.getDataRootNode());
		
		//Test
		assertEquals(test,expect);
	}
	public static void main(String args[]){
		junit.textui.TestRunner.run(TestDataProcedure.class);
	}

}
