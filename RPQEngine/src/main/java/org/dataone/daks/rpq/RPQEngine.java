package org.dataone.daks.rpq;

import java.util.Hashtable;
import org.dataone.daks.regexpparser.ParserExecutor;
import org.antlr.runtime.tree.CommonTree;


public class RPQEngine {
	
	private boolean isFour;
	private RPQDBDAO dao;
	private String tableName;
	
	
	public RPQEngine() {
		this.isFour = false;
		this.dao = RPQDBDAO.getInstance();
	}
	
	
	public boolean isFour() {
		return this.isFour;
	}
	
	
	public void setIsFour(boolean value) {
		this.isFour = value;
	}
	
	
	public void initDAO(String configFile) {
		this.dao.initFromConfigFile(configFile);
	}
	
	
	public static void main( String[] args ) {
		if( args.length < 3) {
			System.out.println( "Usage: java RPQEngine (query) (tablename) (config file) (-4 for returning paths)" );
			System.exit(0);
		}
		RPQEngine engine = new RPQEngine();
		engine.initDAO(args[2]);
		if ( (args.length == 4) && (args[3].equals("-4")) )
			engine.setIsFour(true);
		engine.tableName = args[1];
		engine.executeQuery(args[0]);
	}
    
	
	public void initQuery() {
		if(!this.isFour)
			this.dao.createBinaryTable(this.tableName);
		else
			this.dao.createQuaternaryTable(this.tableName);
	}
	
	
	public void executeQuery(String query) {
		if(this.tableName == null) {
			System.out.println("Error: the table name has not been specified");
			System.exit(0);
		}
		long t0 = System.currentTimeMillis();
		this.initQuery();
		ParserExecutor parserExec = new ParserExecutor();
		CommonTree root = parserExec.createParseTree(query);
		System.out.println(root.toStringTree());
		Hashtable<CommonTree, String> subExpHT = new Hashtable<CommonTree, String>();
		this.postorder(root, subExpHT);
		System.out.println("Evaluating: " + subExpHT.get(root));
		this.walkTree(root, subExpHT);
		this.closeQuery(root, subExpHT);
		long t1 = System.currentTimeMillis();
        System.out.println("Time to process the query: " + ((t1-t0)/1000.0) + " seconds.");
	}
	
	
	private void postorder(CommonTree tree, Hashtable<CommonTree, String> subExpHT) {
        for(int i = 0; i < tree.getChildCount(); i++ ) {
        	CommonTree child = (CommonTree)tree.getChild(i);
        	postorder(child, subExpHT);
        }
        String subExprStr = "";
        for(int i = 0; i < tree.getChildCount(); i++ ) {
        	CommonTree child = (CommonTree)tree.getChild(i);
        	subExprStr += subExpHT.get(child) + " ";
        }
        subExprStr += tree.getText() + " ";
        subExpHT.put(tree, subExprStr.trim());
	}
	
	
	private void walkTree(CommonTree tree, Hashtable<CommonTree, String> subExpHT) {
		for(int i = 0; i < tree.getChildCount(); i++ ) {
        	CommonTree child = (CommonTree)tree.getChild(i);
        	walkTree(child, subExpHT);
        }
		if(tree.getText().equals("|"))
			this.outputOr(tree, subExpHT);
		else if(tree.getText().equals("."))
			this.outputConc(tree, subExpHT);
		else if(tree.getText().equals("*"))
			this.outputStar(tree, subExpHT);
		else if(tree.getText().equals("+"))
			this.outputPlus(tree, subExpHT);
		else if(tree.getText().equals("-"))
			this.outputMinus(tree, subExpHT);
		else if(tree.getText().equals("?"))
			this.outputOptional(tree, subExpHT);
	}
	
	
	private void outputOr(CommonTree node, Hashtable<CommonTree, String> subExpHT) {
		String orExpr = subExpHT.get(node);
		String e1 = subExpHT.get(node.getChild(0));
		String e2 = subExpHT.get(node.getChild(1));
		String orQuery = null;
		if(this.isFour)
			orQuery = "INSERT INTO g SELECT compstart, '" + orExpr + "', compend, basestart, label2, baseend " +
						"FROM g WHERE label1 = '" + e1 + "' OR label1 = '" + e2 + "'";
		else
			orQuery = "INSERT INTO g SELECT compstart, '" + orExpr + "', compend " +
					"FROM g WHERE label1 = '" + e1 + "' OR label1 = '" + e2 + "'";
		this.dao.executeStatement(orQuery);
	}
	
	
	private void outputConc(CommonTree node, Hashtable<CommonTree, String> subExpHT) {
		String concExpr = subExpHT.get(node);
		String e1 = subExpHT.get(node.getChild(0));
		String e2 = subExpHT.get(node.getChild(1));
		String concQuery = null;
		if(this.isFour) {
			concQuery = "INSERT INTO g SELECT g1.compstart, '" + concExpr + "', g2.compend, g1.basestart, " + 
						"g1.label2, g1.baseend FROM g as g1, g as g2 " + 
						"WHERE g1.label1 = '" + e1 + "' and g1.compend = g2.compstart and  g2.label1 = '" + e2 + "'";
			this.dao.executeStatement(concQuery);
			concQuery = "INSERT INTO g SELECT g1.compstart, '" + concExpr + "', g2.compend, g2.basestart, " + 
					"g2.label2, g2.baseend FROM g as g1, g as g2 " + 
					"WHERE g1.label1 = '" + e1 + "' and g1.compend = g2.compstart and  g2.label1 = '" + e2 + "'";
			this.dao.executeStatement(concQuery);
		}
		else {
			concQuery = "INSERT INTO g SELECT g1.compstart, '" + concExpr + "', g2.compend " + 
					"FROM g as g1, g as g2 " + 
					"WHERE g1.label1 = '" + e1 + "' and g1.compend = g2.compstart and  g2.label1 = '" + e2 + "'";
			this.dao.executeStatement(concQuery);
		}
	}
	
	
	private void outputStar(CommonTree node, Hashtable<CommonTree, String> subExpHT) {
		String starExpr = subExpHT.get(node);
		String starSubExpr = subExpHT.get(node.getChild(0));
		String tcQuery = null;
		if(this.isFour) {
            tcQuery = "select * from tcwithrecursive4ary('" + starSubExpr + "', ' *' )";
            this.dao.executeStatement(tcQuery);
            String selfEdgeQueryLeft = "INSERT INTO g " +
                "select g.basestart, '" + starExpr + "', g.basestart, g.basestart, '" + starExpr + "', " + 
            	"g.basestart from g where g.label1 = g.label2";
            String selfEdgeQueryRight = "INSERT INTO g " +
                "select g.baseend, '" + starExpr + "', g.baseend, g.baseend, '" + starExpr + "', " + 
            	"g.baseend from g where g.label1 = g.label2";
            this.dao.executeStatement(selfEdgeQueryLeft);
            this.dao.executeStatement(selfEdgeQueryRight);
        }
        else {
        	tcQuery = "select * from tcwithrecursive('" + starSubExpr + "', ' *' )";
        	this.dao.executeStatement(tcQuery);
            String selfEdgeQuery = "INSERT INTO g " +
                "select distinct g.compstart, '" + starExpr + "', g.compstart from g " +
                "union " +
                "select distinct g.compend, '" + starExpr + "', g.compend from g";
            this.dao.executeStatement(selfEdgeQuery);
        }
	}
	
	
	private void outputPlus(CommonTree node, Hashtable<CommonTree, String> subExpHT) {
		String plusSubExpr = subExpHT.get(node.getChild(0));
		String tcQuery = null;
		if(this.isFour) {
            tcQuery = "select * from tcwithrecursive4ary('" + plusSubExpr + "', ' +' )";
            this.dao.executeStatement(tcQuery);
        }
        else {
        	tcQuery = "select * from tcwithrecursive('" + plusSubExpr + "', ' +' )";
        	this.dao.executeStatement(tcQuery);
        }
	}
	
	
	private void outputMinus(CommonTree node, Hashtable<CommonTree, String> subExpHT) {
		String minusExpr = subExpHT.get(node);
		String minusSubExpr = subExpHT.get(node.getChild(0));
		String minusQuery = null;
		if(this.isFour) {
            minusQuery = "INSERT INTO g SELECT compend, '" + minusExpr + "', compstart, basestart, label2, baseend" + 
            			"FROM g WHERE label1 = '" + minusSubExpr + "'";
            this.dao.executeStatement(minusQuery);
        }
        else {
        	minusQuery = "INSERT INTO g SELECT compend, '" + minusExpr + "', compstart " + 
        			"FROM g WHERE label1 = '" + minusSubExpr + "'";
        	this.dao.executeStatement(minusQuery);
        }
	}
	
	
	private void outputOptional(CommonTree node, Hashtable<CommonTree, String> subExpHT) {
		String optExpr = subExpHT.get(node);
		String optSubExpr = subExpHT.get(node.getChild(0));
		String selfEdgeQueryLeft = null;
		String selfEdgeQueryRight = null;
		String optQuery = null;
		if(this.isFour) {
			selfEdgeQueryLeft = "INSERT INTO g " + 
            		"select g.basestart, '" + optExpr + "', g.basestart, g.basestart, '" + optSubExpr + 
            		"', g.basestart from g where g.label1 = g.label2";
            this.dao.executeStatement(selfEdgeQueryLeft);
            selfEdgeQueryRight = "INSERT INTO g " + 
            		"select g.baseend, '" + optExpr + "', g.baseend, g.baseend, '" + optSubExpr + 
            		"', g.baseend from g where g.label1 = g.label2";
            this.dao.executeStatement(selfEdgeQueryRight);
            optQuery = "INSERT INTO g " + 
            		"SELECT compstart, '" + optExpr + "', compend, basestart, label2, baseend " + 
            		"FROM g WHERE label1 = '" + optSubExpr + "'";
            this.dao.executeStatement(optQuery);
        }
        else {
        	selfEdgeQueryLeft = "INSERT INTO g select g.compstart, '" + optExpr + "', g.compstart from g";
            selfEdgeQueryRight = "INSERT INTO g select g.compend, '" + optExpr + "', g.compend from g";
            this.dao.executeStatement(selfEdgeQueryLeft);
            this.dao.executeStatement(selfEdgeQueryRight);
            optQuery = "INSERT INTO g SELECT compstart, '" + optExpr + 
            		"', compend FROM g WHERE label1 = '" + optSubExpr + "'";
            this.dao.executeStatement(optQuery);
        }
	}
    
	
	public void closeQuery(CommonTree root, Hashtable<CommonTree, String> subExpHT) {
		String expr = subExpHT.get(root);
		this.dao.outputResults(expr, this.isFour);
	}
	
	
}


