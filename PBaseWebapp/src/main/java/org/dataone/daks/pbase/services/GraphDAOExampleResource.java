
package org.dataone.daks.pbase.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.dataone.daks.pbase.neo4j.GraphDAO;

/** Example resource class hosted at the URI path "/graphdaoexample"
 */
@Path("/graphdaoexample")
public class GraphDAOExampleResource {
    
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET 
    @Produces("text/plain")
    public String getIt() {
    	GraphDAO dao = new GraphDAO("graphdbs/ComboBreaker");
    	String retVal = dao.outputGraphNodes();
    	dao.shutdownDB();
    	return retVal;
    }
}
