package db;

public class DLVDriver implements DBDriver {

	public void exeDLV(String dlvPath) {
		
		try {
			Runtime rt = Runtime.getRuntime();
			/*String[] args = {
					"cmd.exe",
					"/c",
					constants.PROPUB_EXE + constants.ENV_SEPARATOR
							+ "exe_dlv.bat" };
			Process p = rt.exec(args);*/
			Process p = rt.exec(dlvPath);
			System.out.println("DLV command: " + dlvPath);
			p.waitFor();

		} catch (Exception ioe) {
			System.err.println("Error in calling external command");
			ioe.printStackTrace();
		}
	
	}
}
