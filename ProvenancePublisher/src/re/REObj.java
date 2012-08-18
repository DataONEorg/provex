package re;

/**
 * @author sdey
 *
 */
public class REObj {

	int  id;
	int  pId;
	int  sPos;
	int  ePos;
	int  noOfChilds = 0;
	
	String fOpd = null;
	String sOpd = null;
	char op;

	public REObj(int id, int pId, int sPos) {
		this.id = id;
		this.pId = pId;
		this.sPos = sPos;
	}

	
	public String getStr() {
		String rtn = null;
		if (sOpd != null) {
			rtn = "\""+ fOpd.replace("\"", "") + op + sOpd.replace("\"", "") + "\"";
		} else {
			rtn = "\""+ fOpd.replace("\"", "") + op + "\"";
		}
		return rtn;
	}

	public String getFacts() {
		String fact = null;
				
		if (op == '.') 
			fact = "conc(" +  fOpd + ", " + sOpd + ", " + getStr() + ").";
		if (op == '|') 
			fact = "or(" +  fOpd + ", " + sOpd + ", " + getStr() + ").";
		if (op == '+') 
			fact = "plus(" +  fOpd + ", " + getStr() + ").";
		if (op == '*') 
			fact = "star(" +  fOpd + ", " + getStr() + ").";
		
		return fact;
	}
	
}
