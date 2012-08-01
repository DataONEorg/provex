package db;

import java.util.Arrays;

public class DLVDriver implements DBDriver {

	public void exeDLV(String[] dlvPath) {

		try {
			Runtime rt = Runtime.getRuntime();
			/*String[] args = {
					"cmd.exe",
					"/c",
					constants.PROPUB_EXE + constants.ENV_SEPARATOR
							+ "exe_dlv.bat" };
			Process p = rt.exec(args);*/
			System.out.println("DLV command: " + Arrays.toString(dlvPath));
			Process p = rt.exec(dlvPath);
			p.waitFor();

		} catch (Exception ioe) {
			System.err.println("Error in calling external command");
			ioe.printStackTrace();
		}

	}
}
