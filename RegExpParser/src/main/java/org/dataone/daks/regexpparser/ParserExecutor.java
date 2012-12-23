package org.dataone.daks.regexpparser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;


public class ParserExecutor {

	public void processExpression(String expr) {
    	CommonTree root = this.createParseTree(expr);
    	System.out.println(root.toStringTree());
	}
	
	
	public CommonTree createParseTree(String str) {
		InputStream istream = null;
		try {
			istream = new ByteArrayInputStream(str.getBytes("UTF-8"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return this.createParseTree(istream);
	}
	
	
	public CommonTree createParseTree(InputStream is) {
    	//Create a CharStream that reads from the input stream provided as a parameter
    	ANTLRInputStream input = null;
		try {
			input = new ANTLRInputStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	//Create a lexer that feeds-off of the input CharStream
    	RegExpLexer lexer = new RegExpLexer(input);
    	//Create a buffer of tokens pulled from the lexer
    	CommonTokenStream tokens = new CommonTokenStream(lexer);
    	//Create a parser that feeds off the tokens buffer
    	RegExpParser parser = new RegExpParser(tokens);
    	//Begin parsing at rule exp
    	CommonTree root = null;
		try {
			RegExpParser.exp_return retVal = parser.exp();
			root = (CommonTree)retVal.getTree();
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		return root;
	}
	
	
}


