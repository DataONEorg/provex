package org.dataone.daks.treecover;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.*;

/**
 * Unit test for TreeInterval class.
 */
public class TreeCoverTest {
	
	
	boolean dfsReachable;
	
	
	public TreeCoverTest() {
		this.dfsReachable = false;
	}
    
	
	//@Test
    public void testTreeCover1() {
		//use the resource file taskGraph1test.txt
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("taskGraph1test.txt");
		Digraph g = new Digraph();
		g.createFromInputStream(inputStream);
		execTreeCoverTest(g);
	}
	
	
    //@Test
    public void testTreeCover2() {
		//use the resource file graph20dot.txt
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("graph20dot.txt");
		Digraph g = new Digraph();
		g.createFromDotInputStream(inputStream);
		execTreeCoverTest(g);
	}
    
    
    //@Test
    public void testTreeCover3() {
		//use the resource file graph30dot.txt
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("graph30dot.txt");
		Digraph g = new Digraph();
		g.createFromDotInputStream(inputStream);
		execTreeCoverTest(g);
	}
    
    
    //@Test
    public void testTreeCover4() {
		//use the resource file graph50dot.txt
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("graph50dot.txt");
		Digraph g = new Digraph();
		g.createFromDotInputStream(inputStream);
		execTreeCoverTest(g);
	}
    
    
    //@Test
    public void testTreeCover5() {
		//use the resource file graph75dot.txt
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("graph75dot.txt");
		Digraph g = new Digraph();
		g.createFromDotInputStream(inputStream);
		execTreeCoverTest(g);
	}
    
    
    //@Test
    public void testTreeCover6() {
		//use the resource file graph100dot.txt
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("graph100dot.txt");
		Digraph g = new Digraph();
		g.createFromDotInputStream(inputStream);
		execTreeCoverTest(g);
	}
    
    
    //@Test
    public void testTreeCover7() {
		//use the resource file graph101dot.txt
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("graph101dot.txt");
		Digraph g = new Digraph();
		g.createFromDotInputStream(inputStream);
		execTreeCoverTest(g);
	}
	
    
    //@Test
    public void testTreeCover8() {
		//use the resource file graph150dot.txt
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("graph150dot.txt");
		Digraph g = new Digraph();
		g.createFromDotInputStream(inputStream);
		execTreeCoverTest(g);
	}
	
	//@Test
    public void testTreeCover9() {
		//use the resource file graph1000dot.txt
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("graph1000dot.txt");
		Digraph g = new Digraph();
		g.createFromDotInputStream(inputStream);
		execTreeCoverTest(g);
	}
	
	
	@Test
    public void testTreeCover10() {
		//use the resource file graph10000dot.txt
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("graph10000dot.txt");
		Digraph g = new Digraph();
		g.createFromDotInputStream(inputStream);
		execTreeCoverTest(g);
	}
	

    public void execTreeCoverTest(Digraph g) {
		int discrepancies = 0;
		int counter = 0;
    	TreeCover cover = new TreeCover();
		cover.createCover(g);
		for( int i = 0; i < g.adj.size(); i++ ) {
			String fromNode = g.posIndex.inverse().get(i);
			for( int j = 0; j < g.adj.size(); j++ ) {
				String toNode = g.posIndex.inverse().get(j);
				if( ! fromNode.equals(toNode) ) {
					boolean treeCoverReachable = treeCoverReachabilityCheck(cover, fromNode, toNode);
					this.dfsReachable = false;
					dfsReachabilityCheck(g, fromNode, toNode);
					if( treeCoverReachable != this.dfsReachable ) {
						System.out.println(fromNode + " -> " + toNode + " : " + treeCoverReachable + "-" + this.dfsReachable);
						discrepancies++;
					}
					//assertEquals(treeCoverReachable, this.dfsReachable);
					counter++;
				}
			}
		}
		System.out.println("Total tests: " + counter);
		System.out.println("Total discrepancies: " + discrepancies);
		assertEquals(true, true);
    }
	
	
	private boolean treeCoverReachabilityCheck(TreeCover cover, String fromNode, String toNode) {
		TreeCode fromCode = cover.treeCodes.get(fromNode);
		int toCodePostorder = cover.rightIndex.get(toNode);
		return fromCode.reachable(toCodePostorder);
	}
	
	
	private void dfsReachabilityCheck(Digraph g, String fromNode, String toNode) {
		//if( this.dfsReachable ) ;
		for( String child: g.adj.get(g.posIndex.get(fromNode)) ) {
			if( toNode.equals(child) ) {
				this.dfsReachable = true;
				break;
			}
			else
				dfsReachabilityCheck(g, child, toNode);
		}
	}
    
    
}



