package propub;

public class Constants {          
	
	public String PROPUB_ROOT   ;
	public String ENV_SEPARATOR ;
	public String DOT_PATH      ;  
	public String SL_DLV_PATH   ;  
	public String NI_DLV_PATH   ;        
	public String RPQ_DLV_PATH  ;
	public String RPQ4_DLV_PATH  ;
	
	public String DOT_TMP_DIR   ; 
	public String PROPUB_EXE    ;
	public String PROPUB_OUT    ;
	public String PROPUB_DOT_OUT;
	
	public void setPaths() {
		DOT_TMP_DIR    = PROPUB_ROOT + ENV_SEPARATOR + "propub"+ ENV_SEPARATOR + "tmp"; 
		PROPUB_EXE     = PROPUB_ROOT + ENV_SEPARATOR + "propub"+ ENV_SEPARATOR + "exe";
		PROPUB_OUT     = PROPUB_ROOT + ENV_SEPARATOR + "propub"+ ENV_SEPARATOR + "out";
		PROPUB_DOT_OUT = PROPUB_ROOT + ENV_SEPARATOR + "propub"+ ENV_SEPARATOR + "dot";
	}
	
}  
	


/*	
public static final String SERVER_ROOT_DIR = "";          
public static final String UPLOAD_DIRECTORY = SERVER_ROOT_DIR + "webapps/GoldenApp/datastore/";          
public static final String GRAPH_DIRECTORY = SERVER_ROOT_DIR + "webapps/GoldenApp/dot/graph.txt";          
public static final String GRAPH_OUTPUT_DIRECTORY = SERVER_ROOT_DIR + "webapps/GoldenApp/dot/out.jpg";          
public static final String DOT_PATH = "/usr/local/bin/dot";          
public static final String DOT_DIR = SERVER_ROOT_DIR + "webapps/GoldenApp/dot/tmp";          
public static final String HTTP_OUTPUT_DIRECTORY = "http://127.0.0.1:8080/GoldenApp/dot/out.jpg";
*/  
