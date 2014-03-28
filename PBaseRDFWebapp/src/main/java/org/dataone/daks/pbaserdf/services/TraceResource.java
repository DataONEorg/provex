
package org.dataone.daks.pbaserdf.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.dataone.daks.pbaserdf.dao.LDBDAO;

import java.io.*;

/** Example resource class hosted at the URI path "/traceresource"
 */
@Path("/traceresource")
public class TraceResource {
    
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET 
    @Produces("text/plain")
    public String getIt(@QueryParam("dbname") String dbname, @QueryParam("wfid") String wfid,
    		@QueryParam("traceid") String traceid) {
    	LDBDAO dao = LDBDAO.getInstance();
    	dao.init(dbname);
    	String retVal = null;
    	try {
    		retVal = dao.getTrace(wfid, traceid);
    		//retVal = this.getFileContents("jsonTrace.txt");
    		//System.out.println("Read jsonTrace.txt");
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


