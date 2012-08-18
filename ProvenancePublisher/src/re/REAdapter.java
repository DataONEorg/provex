package re;

import java.io.BufferedReader;

import file.FileDriver;

public class REAdapter {

	public REAdapter() {
		// TODO Auto-generated constructor stub
	}
	
	public void OPMToSDM() {
		//Read OPM based file
		//Transform into SDM
		//Save the file
		
		
				
	}

	public StringBuffer readFile(String fileName) {
		StringBuffer sb = new StringBuffer();
		FileDriver fd = new FileDriver();
		try {
			BufferedReader br = fd.getFileHandle(fileName);
			String line = br.readLine();
			while (line != null) {
				if (line.contains("used")) {
					line.replace("used", "g");
					line.replace(",", ",used,");
				} else if (line.contains("genBy")) {
					line.replace("genBy", "g");
					line.replace(",", ",genBy,");
				}
				sb.append(line + "\n");
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}
		return sb;
	}
	
	public void SDMToOPM() {
		//Read SDM based file
		//Transform into OPM
		//Save the file
	}
	
}
