package parser;

import java.util.ArrayList;
import java.util.Hashtable;

import propub.Constants;

import file.FileDriver;

public class Model {

	int         finalStateNo;
	String      model;
	FileDriver  fd;
	Constants   constants;
	
	public Model(Constants   constants) {
		fd = new FileDriver();
		this.constants = constants;
		//read the model
		model = fd.readFile(constants.PROPUB_EXE + constants.ENV_SEPARATOR + "out.txt").toString();
		//making the model as set of facts
		model = model.replaceAll("\\),", "\\).").replace("}", ".").replace("l_", "");
		finalStateNo = fetchFinalStateNo();
	}

	public int getFinalStateNo() {
		return finalStateNo;
	}

	public String getModel() {
		return model;
	}

	public int fetchFinalStateNo() {
		int finalStartPos = model.indexOf("final(", 0);
		int finalEndPos   = model.indexOf(")", finalStartPos);
		int finalStateNo  = new Integer(model.substring(finalStartPos+6, finalEndPos)).intValue();
		return finalStateNo;
	}

	public ArrayList<String> getIntrmediateModel(int stateNo) {	
		String[] splitModel = model.split(" ");
		ArrayList<String> intrState = new ArrayList<String>();
		for (int i = 0; i < splitModel.length; i++) {
			if (splitModel[i].contains("," + stateNo + ")")) {
				intrState.add(splitModel[i]);
			}
		}
		return intrState;	
	}
}
