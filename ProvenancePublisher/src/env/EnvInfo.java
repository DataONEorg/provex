package env;

import java.util.Hashtable;

import propub.Constants;

import file.FileDriver;

public class EnvInfo {
	
	Hashtable<String, String> lines = new Hashtable<String, String>();
	
	public void setSetupInfo(Constants constants) {
		
		FileDriver fd = new FileDriver();
		lines = fd.readFile("/Users/sean/ProPubProj/propub/exe/config.txt", "=");
		//lines = fd.readFile("C:\\saumen\\propub\\exe\\config_win.txt", "=");
		
		constants.PROPUB_ROOT = lines.get("PROPUB_ROOT");
		constants.ENV_SEPARATOR = lines.get("ENV_SEPARATOR");
		constants.DOT_PATH = lines.get("DOT_PATH");
		constants.SL_DLV_PATH = lines.get("SL_DLV_PATH");
		constants.NI_DLV_PATH = lines.get("NI_DLV_PATH");
		
		constants.setPaths();
	}
	
}
