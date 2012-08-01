package parser;

import java.util.ArrayList;
import java.util.Hashtable;

import propub.Constants;

import file.FileDriver;

public class Model {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Model model1 = (Model) o;

        if (model != null ? !model.equals(model1.model) : model1.model != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return model != null ? model.hashCode() : 0;
    }

    int         finalStateNo;
	String      model;
	FileDriver  fd;
	Constants   constants;
	
	public Model(String modelData) {
		this.model = modelData;
		finalStateNo = fetchFinalStateNo();
	}

	public Model(Constants   constants) {
		fd = new FileDriver();
		this.constants = constants;
		//read the model
		model = fd.readFile(constants.PROPUB_EXE + constants.ENV_SEPARATOR + "out.txt").toString();
		System.out.println("Building model from file " + (constants.PROPUB_EXE + constants.ENV_SEPARATOR + "out.txt"));
		//making the model as set of facts
		//model = model.replaceAll("\\),", "\\).").replace("}", ".").replace("l_", "");
		model = model.replaceAll("\\),", "\\).").replace("}", ".");
		finalStateNo = fetchFinalStateNo();
	}

	public static Model fromString(String modelData) {
		Model model = new Model(modelData);
		return model;
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
			if (splitModel[i].contains("data")) {
				intrState.add(splitModel[i]);
			}
			if (splitModel[i].contains("actor")) {
				intrState.add(splitModel[i]);
			}
		}
		return intrState;	
	}
}
