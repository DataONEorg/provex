package org.dataone.daks.treecover;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.*;

/**
 * Unit test for TreeInterval class.
 */
public class TreeCoverTest {
    
	
	@Test
    public void testTreeCover() {
    	//use the resource file taskGraph1test.txt
		InputStream inputStream = 
			    Thread.currentThread().getContextClassLoader().getResourceAsStream("taskGraph1test.txt");
		TreeCover cover = new TreeCover();
		Digraph g = new Digraph();
		g.createFromInputStream(inputStream);
		cover.createCover(g);
		assertTrue(true);
    }
    
    
}



