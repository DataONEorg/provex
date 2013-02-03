
package org.dataone.daks.provex;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.dataone.daks.provexdb.JDBCDAO.ProvExDBJDBCDAO;

/** Example resource class hosted at the URI path "/processhiderequestrest"
 */
@Path("/processhiderequestrest")
public class ProcessHideRequestRest {
    
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET 
    @Produces("text/plain")
    public String processHideRequest(@QueryParam("request") String request) {
    	ProvExDBJDBCDAO dao = ProvExDBJDBCDAO.getInstance();
    	dao.initFromConfigFile("provexconfig");
    	String resultJSON = dao.processURFull(request);
        return resultJSON;
    }
    
    
}


