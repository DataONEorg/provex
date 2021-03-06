package org.dataone.daks.treecover;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit test for TreeInterval class.
 */
public class TreeCodeTest {
    
    
	@Test
    public void testTreeCode1() {
    	//[3:5] added to [2:7] should print [2:7]
    	TreeCode code1 = new TreeCode(2,7);
    	TreeCode code2 = new TreeCode(3,5);
    	code1.addCode(code2);
    	assertTrue(code1.toString().equals("[2:7]"));
    }
	
	
	@Test
    public void testTreeCode2() {
    	//[2:7] added to [7:9] should print [2:7],[7:9]
    	TreeCode code1 = new TreeCode(2,7);
    	TreeCode code2 = new TreeCode(7,9);
    	code1.addCode(code2);
    	assertTrue(code1.toString().equals("[2:7],[7:9]"));
    }
	
	
	@Test
    public void testTreeCode3() {
    	//[2:7] added to [8:9] should print [2:7],[8:9]
    	TreeCode code1 = new TreeCode(2,7);
    	TreeCode code2 = new TreeCode(8,9);
    	code1.addCode(code2);
    	assertTrue(code1.toString().equals("[2:7],[8:9]"));
    }
	
	
	@Test
    public void testTreeCode4() {
    	//[2:3] added to [3:5] added to [5:7] should print [5:7],[3:5],[2:3]
    	TreeCode code1 = new TreeCode(5,7);
    	TreeCode code2 = new TreeCode(3,5);
    	TreeCode code3 = new TreeCode(2,3);
    	code1.addCode(code2);
    	code1.addCode(code3);
    	assertTrue(code1.toString().equals("[5:7],[3:5],[2:3]"));
    }
	
	
	@Test
    public void testTreeCode5() {
    	//[2:6] added to [3:5] added to [6:7] should print [6:7],[2:6]
    	TreeCode code1 = new TreeCode(6,7);
    	TreeCode code2 = new TreeCode(3,5);
    	code1.addCode(code2);
    	assertTrue(code1.toString().equals("[6:7],[3:5]"));
    	TreeCode code3 = new TreeCode(2,6);
    	code1.addCode(code3);
    	assertTrue(code1.toString().equals("[6:7],[2:6]"));
    }
	
	
	@Test
    public void testTreeCode6() {
    	//[3:5] added to [2:6] added to [6:7] should print [6:7],[2:6]
    	TreeCode code1 = new TreeCode(6,7);
    	TreeCode code2 = new TreeCode(2,6);
    	code1.addCode(code2);
    	assertTrue(code1.toString().equals("[6:7],[2:6]"));
    	TreeCode code3 = new TreeCode(3,5);
    	code1.addCode(code3);
    	assertTrue(code1.toString().equals("[6:7],[2:6]"));
    }
	
	
	@Test
    public void testTreeCode7() {
    	//[1:6] added to [3:4] added to [2:3] added to [1:2] should print [1:6]
    	TreeCode code1 = new TreeCode(1,2);
    	TreeCode code2 = new TreeCode(2,3);
    	code1.addCode(code2);
    	assertTrue(code1.toString().equals("[1:2],[2:3]"));
    	TreeCode code3 = new TreeCode(3,4);
    	code1.addCode(code3);
    	assertTrue(code1.toString().equals("[1:2],[2:3],[3:4]"));
    	TreeCode code4 = new TreeCode(1,6);
    	code1.addCode(code4);
    	assertTrue(code1.toString().equals("[1:6]"));
    }
	
	
	@Test
    public void testTreeCode8() {
    	//[2:6] added to [3:4] added to [2:3] added to [1:2] should print [1:2],[2:6]
    	TreeCode code1 = new TreeCode(1,2);
    	TreeCode code2 = new TreeCode(2,3);
    	code1.addCode(code2);
    	assertTrue(code1.toString().equals("[1:2],[2:3]"));
    	TreeCode code3 = new TreeCode(3,4);
    	code1.addCode(code3);
    	assertTrue(code1.toString().equals("[1:2],[2:3],[3:4]"));
    	TreeCode code4 = new TreeCode(2,6);
    	code1.addCode(code4);
    	assertTrue(code1.toString().equals("[1:2],[2:6]"));
    }
	
	
	@Test
    public void testTreeCode9() {
    	//[2:4] added to [3:4] added to [2:3] added to [1:2] should print [1:2],[2:4]
    	TreeCode code1 = new TreeCode(1,2);
    	TreeCode code2 = new TreeCode(2,3);
    	code1.addCode(code2);
    	assertTrue(code1.toString().equals("[1:2],[2:3]"));
    	TreeCode code3 = new TreeCode(3,4);
    	code1.addCode(code3);
    	assertTrue(code1.toString().equals("[1:2],[2:3],[3:4]"));
    	TreeCode code4 = new TreeCode(2,4);
    	code1.addCode(code4);
    	assertTrue(code1.toString().equals("[1:2],[2:4]"));
    }
	
	
	@Test
    public void testTreeCode10() {
    	//[36:36] added to [77:90] added to [[97:97] added to [92:96]] added to [5:5] should print
		//[5:5],[97:97],[92:96],[77:90],[36:36]
    	TreeCode code1 = new TreeCode(5,5);
    	TreeCode code2 = new TreeCode(92,96);
    	TreeCode code3 = new TreeCode(97,97);
    	code2.addCode(code3);
    	assertTrue(code2.toString().equals("[92:96],[97:97]"));
    	code1.addCode(code2);
    	assertTrue(code1.toString().equals("[5:5],[92:96],[97:97]"));
    	TreeCode code4 = new TreeCode(77,90);
    	code1.addCode(code4);
    	assertTrue(code1.toString().equals("[5:5],[92:96],[97:97],[77:90]"));
    	TreeCode code5 = new TreeCode(36,36);
    	code1.addCode(code5);
    	assertTrue(code1.toString().equals("[5:5],[92:96],[97:97],[77:90],[36:36]"));
    }
	
	
	@Test
    public void testTreeCode11() {
    	//The TreeCode created from the String "[5:5],[97:97],[92:96],[77:90],[36:36]"
		//should print the same String
    	TreeCode code = new TreeCode("[5:5],[97:97],[92:96],[77:90],[36:36]");
    	assertTrue(code.toString().equals("[5:5],[97:97],[92:96],[77:90],[36:36]"));
    }
    
    
}



