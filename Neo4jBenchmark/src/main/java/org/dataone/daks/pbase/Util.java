package org.dataone.daks.pbase;

import java.io.*;


public class Util {
    
	
	public static BufferedReader openReadFile(String filename) {
		FileReader filestream = null;
		try {
			filestream = new FileReader(filename);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return new BufferedReader(filestream);
	}
	
	
	public static BufferedWriter openWriteFile(String filename) {
		FileWriter filestream = null;
		try {
			filestream = new FileWriter(filename);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return new BufferedWriter(filestream);
	}
	
	
	public static BufferedWriter createWriteFile(String path, String fileName) {
		FileWriter filestream = null;
		try {
			File file = new File(path);
			file.mkdirs();
			filestream = new FileWriter(path + "/" + fileName);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return new BufferedWriter(filestream);
	}
	
	
}


