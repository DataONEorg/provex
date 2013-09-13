package org.dataone.daks.pbase.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.dataone.daks.pbase.provxml2neo4j.BatchCypher;
import org.dataone.daks.pbase.provxml2neo4j.ProvXML2Neo4j;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/provtraceupload")
public class ProvTraceUploadService {

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadProvTrace(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("graphdbname") String graphdbname) {

		//System.out.println("GraphDB Name: " + graphdbname);
		
		String uploadedFileName = graphdbname + ".xml";
		writeToFile(uploadedInputStream, uploadedFileName);
		ProvXML2Neo4j converter = new ProvXML2Neo4j(uploadedFileName);
		converter.createTextCypherFile();
		
		String nodeKeysIndexable = "name,graph_id";
		BatchCypher batch = new BatchCypher("graphdbs/" + graphdbname, nodeKeysIndexable);
		batch.doBatch(uploadedFileName + ".cql");
		batch.shutdownDB();

		String output = "Database created with the name : " + graphdbname;

		return Response.status(200).entity(output).build();
	}


	private void writeToFile(InputStream uploadedInputStream, String uploadedFileName) {
		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileName));
			int read = 0;
			byte[] bytes = new byte[1024];
			out = new FileOutputStream(new File(uploadedFileName));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}


