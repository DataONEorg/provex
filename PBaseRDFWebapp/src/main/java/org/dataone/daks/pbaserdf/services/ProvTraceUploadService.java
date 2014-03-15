package org.dataone.daks.pbaserdf.services;

import java.io.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.hp.hpl.jena.rdf.model.*;

import org.apache.jena.riot.RDFDataMgr;
import org.dataone.daks.pbaserdf.dao.LDBDAO;
import org.dataone.daks.provxml2rdf.*;

@Path("/provtraceupload")
public class ProvTraceUploadService {

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadProvTrace(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("dbname") String dbname,
			@FormDataParam("wfid") String wfid) {
		
		String uploadedFileName = dbname + ".xml";
		writeToFile(uploadedInputStream, uploadedFileName);
		ProvXML2RDF converter = new ProvXML2RDF();
		String tempXMLRDFFile = converter.doMapping(uploadedFileName, wfid);
		
		Model m = RDFDataMgr.loadModel(tempXMLRDFFile);
		
		LDBDAO dao = LDBDAO.getInstance();
		dao.init(dbname);
		dao.addModel(m);
		dao.shutdown();

		String output = "Database created/updated with the name : " + dbname +
				" and workflow id: " + wfid;

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


