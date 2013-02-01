
package org.dataone.daks.provex;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.dataone.daks.provexdb.JDBCDAO.ProvExDBJDBCDAO;


/** Example resource class hosted at the URI path "/provstagedbrest"
 */
@Path("/provstagedbrest")
public class ProvStageDBRest {
    
    
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET 
    @Produces("text/plain")
    @Path("{stageId}")
    public String getProvenanceStageJSON(@PathParam("stageId") String stageId) {
    	ProvExDBJDBCDAO dao = ProvExDBJDBCDAO.getInstance();
    	dao.initFromConfigFile("provexconfig");
    	String provStageJSON = dao.getProvenanceStageJSON(stageId);
        return provStageJSON;
    }
    
    
}


