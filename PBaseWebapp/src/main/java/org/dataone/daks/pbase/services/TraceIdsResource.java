
package org.dataone.daks.pbase.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.dataone.daks.pbase.neo4j.GraphDAO;

/** Example resource class hosted at the URI path "/traceidsresource"
 */
@Path("/traceidsresource")
public class TraceIdsResource {
    
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET 
    @Produces("text/plain")
    public String getIt(@QueryParam("dbname") String dbname, @QueryParam("wfid") String wfid) {
    	GraphDAO dao = GraphDAO.getInstance();
    	dao.init("graphdbs/" + dbname);
    	String retVal = null;
    	try {
    		retVal = dao.getRunIDs(wfid);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	return retVal;
    }
}
