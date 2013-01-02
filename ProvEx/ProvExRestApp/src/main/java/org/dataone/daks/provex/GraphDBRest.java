
package org.dataone.daks.provex;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.dataone.daks.rpq.RPQDBDAO;


/** Example resource class hosted at the URI path "/graphdbrest"
 */
@Path("/graphdbrest")
public class GraphDBRest {
    
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET 
    @Produces("text/plain")
    public String getGraphJSON() {
    	RPQDBDAO dao = RPQDBDAO.getInstance();
    	dao.initFromConfigFile("config");
    	String graphJSON = dao.getGraphJSON("graph");
        return graphJSON;
    }
    
    
}


