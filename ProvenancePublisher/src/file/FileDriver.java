package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Hashtable;

public class FileDriver {

	public FileDriver() {	
	}
	
	ArrayList<String>          alLines   = new ArrayList<String>();
	Hashtable<String, String>  htLines = new Hashtable<String, String>();
	
	/*
	 * Reads the file line by line and add them into the ArrayList
	 */
	public void copyFile(String infileName, String outfileName) {
		StringBuffer sb = readFile(infileName);
		writeFile(sb, outfileName);
	}

	/*
	 * Write a file with StringBuffer data
	 */
	public void writeFile(StringBuffer data, String outfileName) {
		try {
			FileWriter fw = new FileWriter(outfileName);
			BufferedWriter pgfile = new BufferedWriter(fw);
			pgfile.append(data.toString());
			pgfile.close();
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}
	}

	/*
	 * Reads the file line by line and add them into the ArrayList
	 */
	public StringBuffer readFile(String fileName) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = getFileHandle(fileName);
			String line = br.readLine();
			while (line != null) {
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
	
	/*
	 * Reads the file line by line and develops key-value pairs based on the 
	 * separator provided and put the pairs into the Hashtable
	 */
	public Hashtable<String, String> readFile(String fileName, String separator) {
		try {
			BufferedReader br = getFileHandle(fileName);
			String line = br.readLine();
			while (line != null) {		
				htLines.put(line.split(separator)[0], line.split(separator)[1]);
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}
		return htLines;
	}
	
	private BufferedReader getFileHandle(String fileName) throws Exception{
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		return br;
	}
	
}
