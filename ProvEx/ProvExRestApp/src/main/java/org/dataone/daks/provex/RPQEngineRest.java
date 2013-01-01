
package org.dataone.daks.provex;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.dataone.daks.rpq.RPQEngine;
import java.io.*;

/** Example resource class hosted at the URI path "/rpqenginerest"
 */
@Path("/rpqenginerest")
public class RPQEngineRest {
    
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET 
    @Produces("text/plain")
    public String runQuery(@QueryParam("q") String query, @QueryParam("lineage") boolean quaternary) {
    	RPQEngine engine = new RPQEngine();
    	//Usage: java RPQEngine (query) (tablename) (config file) [-4 for returning paths] [-JSON]
    	if(!quaternary)
    		engine.main(new String[]{query, "graph", "config", "-JSON"});
    	else
    		engine.main(new String[]{query, "graph", "config", "-4", "-JSON"});
        return readFile("results.txt");
    }
    
    
    private String readFile(String filename) {
    	BufferedReader input = null;
    	StringBuffer buff = new StringBuffer();
    	String line = null;
    	try {
    		input =  new BufferedReader(new FileReader(new File(filename)));
    		while( (line = input.readLine()) != null)
    			buff.append(line);
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    	return buff.toString();
    }
    
    
}


