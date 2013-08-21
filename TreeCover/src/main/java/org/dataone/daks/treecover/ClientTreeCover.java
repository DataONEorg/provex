package org.dataone.daks.treecover;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClientTreeCover {

	
	boolean dfsReachable;
	
	
	public ClientTreeCover() {
		this.dfsReachable = false;
	}
	
	
	/**
	 * java ClientTreeCover <dotFile> <test|benchmark>
	 */
	public static void main(String[] args) {
		ClientTreeCover client = new ClientTreeCover();
		Digraph g = client.createDigraph(args[0]);
		TreeCover cover = new TreeCover();
		cover.createCover(g);
		if( args[1].equals("test") )
			client.execTreeCoverTest(g, cover);
		else if( args[1].equals("benchmark") )
			client.benchmarkTreeCover(g, cover);
	}
	
	
	private Digraph createDigraph(String dotFile) {
		Digraph retVal = null;
		try {
			InputStream inputStream = new FileInputStream(dotFile);
			Digraph g = new Digraph();
			g.createFromDotInputStream(inputStream);
			retVal = g;
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return retVal;
	}

	
	public void execTreeCoverTest(Digraph g, TreeCover cover) {
		int discrepancies = 0;
		int counter = 0;
		for( int i = 0; i < g.adj.size(); i++ ) {
			String fromNode = g.posIndex.inverse().get(i);
			for( int j = 0; j < g.adj.size(); j++ ) {
				String toNode = g.posIndex.inverse().get(j);
				if( ! fromNode.equals(toNode) ) {
					boolean treeCoverReachable = cover.reachable(fromNode, toNode);
					this.dfsReachable = false;
					dfsReachabilityCheck(g, fromNode, toNode);
					if( treeCoverReachable != this.dfsReachable ) {
						System.out.println(fromNode + " -> " + toNode + " : " + treeCoverReachable + "-" + this.dfsReachable);
						discrepancies++;
					}
					counter++;
				}
			}
		}
		System.out.println("Total tests: " + counter);
		System.out.println("Total discrepancies: " + discrepancies);
    }
	
	
	public void benchmarkTreeCover(Digraph g, TreeCover cover) {
		int counter = 0;
		int nReachable = 0;
		long startTime = System.currentTimeMillis();
		for( int i = 0; i < g.adj.size(); i++ ) {
			String fromNode = g.posIndex.inverse().get(i);
			for( int j = 0; j < g.adj.size(); j++ ) {
				String toNode = g.posIndex.inverse().get(j);
				if( ! fromNode.equals(toNode) ) {
					boolean treeCoverReachable = cover.reachable(fromNode, toNode);
					if( treeCoverReachable )
						nReachable++;
					counter++;
				}
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Tree cover execution time: " + (endTime-startTime)/1000.0 + " sec." );
		System.out.println("Total tests: " + counter);
		System.out.println("Total reachable pairs: " + nReachable);
		System.out.println();
		counter = 0;
		nReachable = 0;
		startTime = System.currentTimeMillis();
		for( int i = 0; i < g.adj.size(); i++ ) {
			String fromNode = g.posIndex.inverse().get(i);
			for( int j = 0; j < g.adj.size(); j++ ) {
				String toNode = g.posIndex.inverse().get(j);
				if( ! fromNode.equals(toNode) ) {
					this.dfsReachable = false;
					this.dfsReachabilityCheck(g, fromNode, toNode);
					if( this.dfsReachable )
						nReachable++;
					counter++;
				}
			}
		}
		endTime = System.currentTimeMillis();
		System.out.println("Depth first search execution time: " + (endTime-startTime)/1000.0 + " sec." );
		System.out.println("Total tests: " + counter);
		System.out.println("Total reachable pairs: " + nReachable);
		System.out.println();
    }
	
	
	private void dfsReachabilityCheck(Digraph g, String fromNode, String toNode) {
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



