
package org.dataone.daks.pbaserdf.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.dataone.daks.pbaserdf.dao.LDBDAO;

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
    	LDBDAO dao = LDBDAO.getInstance();
    	dao.init("provone");
    	String retVal = null;
    	try {
    		retVal = dao.getDataLinks("e0");
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	return retVal;
    }
}
