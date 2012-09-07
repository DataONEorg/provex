package env;

import java.util.Hashtable;

import propub.Constants;

import file.FileDriver;
import java.io.File;

public class EnvInfo {
	
	Hashtable<String, String> lines = new Hashtable<String, String>();
	
	public void setSetupInfo(Constants constants) {
		System.out.println("Setting up!");
		FileDriver fd = new FileDriver();
		File userDirectory = new File(System.getProperty("user.home"));
		File configurationFile = new File(userDirectory, "propubrc");
		if (!configurationFile.exists()) {
			System.err.println("Configuration file not found at: " + configurationFile.getAbsolutePath());
			System.exit(1);
		}
		lines = fd.readFile(configurationFile.getAbsolutePath(), "=");
		//lines = fd.readFile("C:\\saumen\\propub\\exe\\config_win.txt", "=");
		
		constants.PROPUB_ROOT = lines.get("PROPUB_ROOT");
		constants.ENV_SEPARATOR = lines.get("ENV_SEPARATOR");
		constants.DOT_PATH = lines.get("DOT_PATH");
		constants.SL_DLV_PATH = lines.get("SL_DLV_PATH");
		constants.NI_DLV_PATH = lines.get("NI_DLV_PATH");
		constants.RPQ_DLV_PATH = lines.get("RPQ_DLV_PATH");
		
		constants.setPaths();
	}
	
}
