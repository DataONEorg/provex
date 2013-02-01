
package org.dataone.daks.provex;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.dataone.daks.provexdb.JDBCDAO.ProvExDBJDBCDAO;


/** Example resource class hosted at the URI path "/tracedbrest"
 */
@Path("/tracedbrest")
public class TraceDBRest {
    
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET 
    @Produces("text/plain")
    public String getTraceJSON() {
    	ProvExDBJDBCDAO dao = ProvExDBJDBCDAO.getInstance();
    	dao.initFromConfigFile("provexconfig");
    	String traceJSON = dao.getTraceJSON();
        return traceJSON;
    }
    
    
}


