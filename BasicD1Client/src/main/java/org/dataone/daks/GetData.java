package org.dataone.daks;

import java.io.*;

import org.dataone.client.CNode;
import org.dataone.client.D1Client;
import org.dataone.client.MNode;
import org.dataone.service.exceptions.*;
import org.dataone.service.types.v1.*;


public class GetData {

	
	public GetData() {
		
	}
	
	
    public static void main(String[] args) {
    	GetData gd = new GetData();
        gd.getData();
    }
	
    
	private void getData()  {

		String mNodeUrl = "https://mn-demo-9.test.dataone.org/knb/d1/mn";
		String cNodeUrl = "https://cn-stage-2.test.dataone.org/cn";
		String id = "test:1362091257238";
		try {
			Identifier pid = new Identifier();
			pid.setValue(id);
			//CNode cNode = D1Client.getCN();
			/*
			CNode cNode = new CNode(cNodeUrl);
			ObjectLocationList objectLocationList = cNode.resolve(pid);
			ObjectLocation objectLocation = objectLocationList.getObjectLocation(0);
			MNode mnNode = D1Client.getMN(objectLocation.getNodeIdentifier());
			*/
			MNode mnNode = D1Client.getMN(mNodeUrl);
			InputStream inputStream = mnNode.get(pid);
			System.out.println(this.outputData(inputStream));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private String outputData(InputStream istream) {
		InputStreamReader is = new InputStreamReader(istream);
		BufferedReader br = new BufferedReader(is);
		StringBuilder sb = new StringBuilder();
		String read = null;
		try {
			while( (read = br.readLine()) != null) {
				sb.append(read + "\n");
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
}




