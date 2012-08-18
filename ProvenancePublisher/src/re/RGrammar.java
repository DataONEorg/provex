package re;

import file.FileDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Hashtable;

/**
 * 
 */

/**
 * @author sdey
 *
 */
public class RGrammar {

	/**
	 * 
	 */
	public RGrammar() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		//String s = "(a.b)";
		//String s = "(a|(b.c))";
        //String s = "((a|b).c)";
		//String s = "((a.b)|(c.d))";
		String s = "((k+).(((a.b).(c+))|(d+)))";
        
		RGrammar re = new RGrammar();
		re.reGrammar(s);
	}

	public File reGrammar(String s) {
        
        char prevC='\u0000';
        char currC='\u0000';
        
        Hashtable<Integer,REObj> list = new Hashtable<Integer,REObj>();
        Hashtable<Integer,REObj> listS = new Hashtable<Integer,REObj>();
        Hashtable<Integer,REObj> listE = new Hashtable<Integer,REObj>();
        
        REObj obj = null;
        int cObjId = 0;
        int pObjId = 0;
        
        //get the start and end positions of all the relations and 
        //build child -> parent relations
        for (int i=0;i<s.length();i++) {
        	currC = s.charAt(i);       	
        	if (isLeftPara(currC)) {
        		cObjId++;
        		obj = new REObj(cObjId,pObjId,i);
        		if (pObjId>0) 
        			list.get(pObjId).noOfChilds++;
        		list.put(cObjId, obj);
        		pObjId = cObjId;
        	}
        	if (isRightPara(currC)) {
        		obj.ePos = i;
        		pObjId = obj.pId;
        		obj = list.get(pObjId);
        	}
        }   
        
        //Get the Operands and operators
        for (int j=list.size(); j>=1; j--) {        	
        	obj = list.get(j);
        	//No child--> thus of the form (i) a.b (ii) a+ 
        	if (obj.noOfChilds == 0) {
        		String  str      = s.substring(obj.sPos+1, obj.ePos);
        		int     OpPos    = findOpPos(str);
        		char    op       = str.charAt(OpPos);

        		obj.fOpd = str.substring(0, OpPos);
    			obj.op   = op; 
        		if (isBinary(op))
        			obj.sOpd = str.substring(OpPos+1, str.length());	
        	}
        	if (obj.noOfChilds == 1) {
        		char op = '\u0000';
        		//of the form ((a.b).c)
        		if (listS.get(obj.sPos+1) != null) {
        			op = s.charAt(listS.get(obj.sPos+1).ePos+1);
        		//of the form (a.(b.c))
        		} else {
        			op = s.charAt(listE.get(obj.ePos-1).sPos-1);
        		}
        		if (isBinary(op)) {
        			//of the form ((a.b).c) --> the child is on the left of the op
            		if (listS.get(obj.sPos+1) != null) {
            			obj.fOpd = listS.get(obj.sPos+1).getStr();
            			obj.op   = op; 
            			obj.sOpd = s.substring(listS.get(obj.sPos+1).ePos+2, obj.ePos);
            		//of the form (a.(b.c)) --> the child is on the right of the op
            		} else {
            			obj.fOpd = s.substring(obj.sPos+1, listE.get(obj.ePos-1).sPos-1);
            			obj.op   = op; 
            			obj.sOpd = listE.get(obj.ePos-1).getStr();
            		}
        		} else {
        			//Since uniary--> the child is on the left of the op
        			obj.fOpd = listS.get(obj.sPos+1).getStr();
        			obj.op   = op; 
        		}
        	}
        	//has two children
        	if (obj.noOfChilds == 2) {
    			obj.fOpd = listS.get(obj.sPos+1).getStr();
    			obj.op   = s.charAt(listS.get(obj.sPos+1).ePos+1); 
    			obj.sOpd = listE.get(obj.ePos-1).getStr();
        	} 
        	listS.put(obj.sPos, obj);
    		listE.put(obj.ePos, obj);
        }

		StringBuffer sb = new StringBuffer();
		for (int k=list.size(); k>=1; k--) {
			obj = list.get(k);
			sb.append(obj.getFacts()).append("\n");
		}

		s = s.replaceAll("\\(", "");
		s = s.replaceAll("\\)", "");


		sb.append("q(\"" + s + "\").\n");

		FileDriver fd = new FileDriver();
		File tempFile = null;
		try {
			tempFile = File.createTempFile("dlv_facts", ".dlv");
			fd.writeFile(sb, tempFile.getAbsolutePath());
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		return tempFile;
		//Print the facts
	}
	
	public int findOpPos(String str) {		
		int pos = 0;		
		pos = str.indexOf('.');
		if (pos == -1) pos = str.indexOf('|');
		if (pos == -1) pos = str.indexOf('+');
		if (pos == -1) pos = str.indexOf('*');
		if (pos == -1) pos = str.indexOf('?');				
		return pos;
	}
	
	public boolean isBinary(char c) {		
		boolean result = false;
		if ( 
				(c == '.') ||
				(c == '|') 
			) {
			result = true;
		}
		return result;
	}

	public boolean isUnary(char c) {		
		boolean result = false;
		if ( 
				(c == '*') ||
				(c == '+') ||
				(c == '?') 
			) {
			result = true;
		}
		return result;
	}
	
	public boolean isLeftPara(char c) {		
		boolean result = false;
		if ( 
				(c == '(')  
			) {
			result = true;
		}
		return result;
	}

	public boolean isRightPara(char c) {		
		boolean result = false;
		if ( 
				(c == ')')  
			) {
			result = true;
		}
		return result;
	}
		
}