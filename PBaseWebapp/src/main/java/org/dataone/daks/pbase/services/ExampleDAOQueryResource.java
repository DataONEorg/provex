
package org.dataone.daks.pbase.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.dataone.daks.pbase.neo4j.GraphDAO;

/** Example resource class hosted at the URI path "/exampledaoquery"
 */
@Path("/exampledaoquery")
public class ExampleDAOQueryResource {
    
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET 
    @Produces("text/plain")
    public String getIt() {
    	GraphDAO dao = GraphDAO.getInstance();
    	dao.init("graphdbs/ComboBreaker");
    	String retVal = null;
    	try {
    		retVal = dao.executeQuery("START n=node(*) WHERE HAS(n.wfID) AND HAS(n.type) RETURN DISTINCT n.wfID, n;");  
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	return retVal;
    }
}
