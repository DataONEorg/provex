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
    
	
	@Test
    public void testTreeCover1() {
		//use the resource file taskGraph1test.txt
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("taskGraph1test.txt");
		Digraph g = new Digraph();
		g.createFromInputStream(inputStream);
		execTreeCoverTest(g);
	}
	
	
	@Test
    public void testTreeCover2() {
		//use the resource file graph100dot.txt
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("graph100dot.txt");
		Digraph g = new Digraph();
		g.createFromDotInputStream(inputStream);
		execTreeCoverTest(g);
	}
	

    public void execTreeCoverTest(Digraph g) {
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
					//System.out.println(fromNode + " -> " + toNode + " : " + treeCoverReachable + "-" + this.dfsReachable);
					assertEquals(treeCoverReachable, this.dfsReachable);
				}
			}
		}
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



