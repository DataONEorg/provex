package dot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import parser.DLVToDot;
import propub.Constants;

public class DOTDriver {
	private   String DOT_OUT_TYPE = "gif"; // was "jpg"
	private   String DOT_OUT_EXTN = ".jpg";
	
	Constants constants;
	
	public DOTDriver(Constants pConstants) {
		// TODO Auto-generated constructor stub
		constants = pConstants;
	}

    public String prepareImgage(ArrayList<String> model) {
		DLVToDot rdt = new DLVToDot();
		rdt.readArrayList(model);
		String inputPGDOTFileName = 
			constants.PROPUB_DOT_OUT
			+ constants.ENV_SEPARATOR 
			+ "pg_" 
			+ 0 + "." + 0 + 
			".txt";
		String inputPGIMGFileName = 
			constants.PROPUB_DOT_OUT
			+ constants.ENV_SEPARATOR 
			+ "pg_" 
			+ 0 + "." + 0 
			+ ".gif";
		rdt.prepareDOTFile(inputPGDOTFileName);
		write(getGraph(new File(inputPGDOTFileName)), inputPGIMGFileName);    
		return inputPGIMGFileName;
    }
        
    public void write(byte[] img, String tFile) {
    	File to = new File(tFile);
    	try {
    		FileOutputStream fos = new FileOutputStream(to);
    		fos.write(img);
    		fos.close();
    	} catch (Exception e) { 
    		System.out.println(e.getMessage());
    	}
    }
    
    public byte[] getGraph(File dotFile)
    {
       File img;
       byte[] img_stream = null;
       try {
          img = File.createTempFile("graph_", DOT_OUT_EXTN, new File(constants.DOT_TMP_DIR));
          Runtime rt = Runtime.getRuntime();
          String[] args = {constants.DOT_PATH, "-T" + DOT_OUT_TYPE, dotFile.getAbsolutePath(), "-o", img.getAbsolutePath()};
		  System.out.println("Running graph command: " + Arrays.toString(args));
          Process p = rt.exec(args);
          
          p.waitFor();
          
          FileInputStream in = new FileInputStream(img.getAbsolutePath());
          img_stream = new byte[in.available()];
          in.read(img_stream);
          
          if( in != null ) 
        	  in.close();

          if (img.delete() == false) 
             System.err.println("Warning: " + img.getAbsolutePath() + " could not be deleted!");
       } catch (Exception ioe) {
          System.err.println("Error:    in I/O processing of tempfile in dir " + constants.DOT_TMP_DIR+"\n");
          System.err.println("       or in calling external command");
          ioe.printStackTrace();
       }
       
       //write(img_stream, DOT_DIR + "/" + "fpc" + DOT_OUT_EXTN);
       
       return img_stream;
   }
    
}
