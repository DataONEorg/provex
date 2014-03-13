package org.dataone.daks.pbaserdf.services;

import java.io.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import org.dataone.daks.provxml2rdf.*;

@Path("/provtraceupload")
public class ProvTraceUploadService {

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadProvTrace(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("dbname") String dbname) {
		
		String uploadedFileName = dbname + ".xml";
		writeToFile(uploadedInputStream, uploadedFileName);
		String[] params = {""};
		params[0] = uploadedFileName;
		ProvXML2RDF.main(params);

		String output = "Database created with the name : " + dbname;

		return Response.status(200).entity(output).build();
	}


	private void writeToFile(InputStream uploadedInputStream, String uploadedFileName) {
		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileName));
			int read = 0;
			byte[] bytes = new byte[1024];
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


