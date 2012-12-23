package org.dataone.daks.rpq;

import java.io.*;
import org.dataone.daks.regexpparser.ParserExecutor;
import org.antlr.runtime.tree.CommonTree;


public class RPQEngine {
	
	private boolean isFour;
	private RPQDBDAO dao;
	
	
	public RPQEngine(boolean isFour) {
		this.isFour = isFour;
	}
	
	
	public static void main( String[] args ) {
		if( args.length < 3)
			System.out.println( "Usage: java RPQEngine (query) (tablename) (config file) (-4 for returning paths)" );
		else {
			RPQEngine calc = null;
			if ( (args.length == 4) && (args[3].equals("-4")) )
	            calc = new RPQEngine(true);
	        else   
	            calc = new RPQEngine(false);
	        calc.executeQuery(args[0]);
		}
	}
    
	
	public void executeQuery(String query) {
		ParserExecutor parserExec = new ParserExecutor();
		CommonTree root = parserExec.createParseTree(query);
		System.out.println(root.toStringTree());
	}
    
	
}


