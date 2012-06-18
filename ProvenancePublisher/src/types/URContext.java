package types;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class URContext {
	public URContext(String userRequests, String modelData) {
		int lineCount = 0;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new StringReader(userRequests));
			String line;
			while ((line = br.readLine()) != null) {
				lineCount++;
			}
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		finally {
			if (br != null) {
				try {
					br.close();
				}
				catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		this.userRequests = userRequests;
		this.modelData = modelData;
		this.lineCount = lineCount;
	}
	public String getUserRequestsText() {
		return userRequests;
	}

	public String getModelData() {
		return modelData;
	}

	public String toString() {
		int threshold = 5;
		if (userRequests.length() > threshold) {
			return "[" + userRequests.substring(0, threshold) + "] (" + lineCount + " lines)";
		}
		else {
			return "(" + userRequests + ")";
		}
	}

	private String userRequests = null;
	private String modelData = null;
	private int lineCount = -1;
}
