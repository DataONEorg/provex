package db;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
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
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), baos);
            errorGobbler.start();
			p.waitFor();

            String errorString = baos.toString("UTF-8");

            if (!"".equals(errorString)) {
                JOptionPane.showMessageDialog(null, errorString, "Error while running DLV", JOptionPane.ERROR_MESSAGE);
            }

		} catch (Exception ioe) {
			System.err.println("Error in calling external command");
			ioe.printStackTrace();
		}

	}

    class StreamGobbler extends Thread {
        InputStream is;
        OutputStream os;

        StreamGobbler(InputStream is, OutputStream os) {
            this.is = is;
            this.os = os;
        }

        public void run() {
            try {
                PrintWriter pw = null;

                if (os != null) {
                    pw = new PrintWriter(os);
                }

                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;

                while ((line = br.readLine()) != null) {
                    if (pw != null) {
                        pw.println(line);
                    }
                }

                if (pw != null) {
                    pw.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
