
package org.dataone.daks.pbaserdf.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.dataone.daks.pbaserdf.dao.LDBDAO;

/** Example resource class hosted at the URI path "/wfresource"
 */
@Path("/wfresource")
public class WfResource {
    
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET 
    @Produces("text/plain")
    public String getIt(@QueryParam("dbname") String dbname, @QueryParam("wfid") String wfid) {
    	LDBDAO dao = LDBDAO.getInstance();
    	dao.init(dbname);
    	String retVal = null;
    	try {
    		retVal = dao.getWorkflowReachEncoding(wfid);
    		//retVal = this.getFileContents("jsonWF.txt");
    		//System.out.println("Read jsonWF.txt");
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	return retVal;
    }
    
    
	private String getFileContents(String filename) {
		BufferedReader reader = null;
		StringBuffer buff = new StringBuffer();
		String line = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
			while( (line = reader.readLine()) != null ) {
				buff.append(line);
			}
			reader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return buff.toString();
	}
    
    
}



