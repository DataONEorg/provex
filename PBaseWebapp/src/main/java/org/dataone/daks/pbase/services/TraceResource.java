
package org.dataone.daks.pbase.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.dataone.daks.pbase.neo4j.GraphDAO;

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
    	GraphDAO dao = GraphDAO.getInstance();
    	dao.init("graphdbs/" + dbname);
    	String retVal = null;
    	try {
    		retVal = dao.getTrace(wfid, traceid);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	return retVal;
    }
}
