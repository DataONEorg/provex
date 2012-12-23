package org.dataone.daks.regexpparser;


class TestRegExpParser {
	
	
	public static void main(String[] args) {
		TestRegExpParser tester = new TestRegExpParser();
		tester.testExpression("(a|b)+");
	}
	
	
	public void testExpression(String query) {
		ParserExecutor executor = new ParserExecutor();
		executor.processExpression(query);
		System.out.println();
	}
	
	
}
