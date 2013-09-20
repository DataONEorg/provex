
package org.dataone.daks.pbase.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.dataone.daks.pbase.neo4j.GraphDAO;

/** Example resource class hosted at the URI path "/exampledaoworkflow"
 */
@Path("/exampledaoworkflow")
public class ExampleDAOWorkflowResource {
    
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
    		retVal = dao.getWorkflow("1b2a1451-8401-41ef-9015-d1f64a7e5cda");
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	return retVal;
    }
}
