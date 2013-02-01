package org.dataone.daks.provex;

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

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import org.dataone.daks.provexdb.DAO.BuilderWithXML.*;

@Path("/traceupload")
public class TraceUpload {

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		
		String uploadedFileName = "trace.txt";
		writeToFile(uploadedInputStream, uploadedFileName);
		XMLDAOMapperDynamic mapper = new XMLDAOMapperDynamic(uploadedFileName);
		mapper.doMapping();
		String output = "Trace successfully created.";
		
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


