package org.dataone.daks.pbase.treecover;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class TreeCover {

	Hashtable<String, Integer> leftIndex;
	Hashtable<String, Integer> rightIndex;
	String root;
	Hashtable<String, TreeCode> treeCodes;
	
	
	public TreeCover() {
		this.leftIndex = new Hashtable<String, Integer>();
		this.rightIndex = new Hashtable<String, Integer>();
		this.root = null;
		this.treeCodes = new Hashtable<String, TreeCode>();
	}
	
	
	public void createCover(Digraph g) {
		Digraph gRev = g.reverse();
		this.createVirtualRoot(g, gRev);
        Digraph gPrime = g.copy();
        Digraph gPrimeRev = gPrime.reverse();
        coverAlgorithm(gPrime, gPrimeRev);
        postorder(gPrime, gPrimeRev, this.root);
        processTreeIntervals(g);
	}
	
	
	public boolean reachable(String fromNode, String toNode) {
		TreeCode fromCode = this.treeCodes.get(fromNode);
		int toCodePostorder = this.rightIndex.get(toNode);
		return fromCode.reachable(toCodePostorder);
	}
	
	
	public TreeCode getCode(String node) {
		return this.treeCodes.get(node);
	}
	
	
	public int getPostorder(String node) {
		return this.rightIndex.get(node);
	}
	
	
	private void createVirtualRoot(Digraph g, Digraph gRev) {
		String vroot = "root";
		//Find the nodes in that have no incoming edges by using
		//the reverse graph of g, which is gRev
		List<String> rootNodes = new ArrayList<String>();
		for( int i = 0; i < gRev.adj.size(); i++ ) {
			if( gRev.adj.get(i).isEmpty() ) {
				String vtx = gRev.posIndex.inverse().get(i);
				rootNodes.add(vtx);
			}
		}
		if( rootNodes.size() > 1) {
			for( String rootNode: rootNodes ) {
				g.addEdge(vroot, rootNode);
				gRev.addEdge(rootNode, vroot);
			}
			this.root = vroot;
		}
		else {
			this.root = rootNodes.get(0);
		}
	}
	
	
	private boolean coverAlgorithm(Digraph g, Digraph gRev) {
		List<String> topSort = g.topSort();
		if(topSort == null) {
			System.out.println("The graph has a cycle.");
			return false;
		}
		Hashtable<String, Set<String>> pred = new Hashtable<String, Set<String>>();
		for( String s: topSort) {
			Set<String> set = new LinkedHashSet<String>();
			pred.put(s, set);
		}
		for( String v: topSort ) {
			int vposgrev = gRev.posIndex.get(v);
			while( gRev.adj.get(vposgrev).size() > 1 ) {
				Iterator<String> it = gRev.adj.get(vposgrev).iterator();
				String u = it.next();
				String uprime = it.next();
				if( pred.get(u).size() > pred.get(uprime).size() ) {
					//delete the edge (uprime, v)
					int uprimeposg = g.posIndex.get(uprime);
					g.adj.get(uprimeposg).remove(v);
					gRev.adj.get(vposgrev).remove(uprime);
				}
				else {
					//delete the edge (u, v)
					int uposg = g.posIndex.get(u);
					g.adj.get(uposg).remove(v);
					gRev.adj.get(vposgrev).remove(u);
				}
			}
			//pred(v) <- {u} u pred(u) for every incoming edge (u, v);
			Iterator<String> it = gRev.adj.get(vposgrev).iterator();
			while(it.hasNext()) {
				String u = it.next();
				pred.get(v).add(u);
				Iterator<String> itinner = pred.get(u).iterator();
				while(itinner.hasNext()) {
					String upred = itinner.next();
					pred.get(v).add(upred);
				}
			}
		}
        return true;
	}

	
	private void postorder(Digraph g, Digraph gRev, String startNode) {
		if( startNode == null )
			return;
		String currNode = startNode;
		Hashtable<String, Boolean> marked = new Hashtable<String, Boolean>();
		int visited = 1;
		while( currNode != null ) {
			//Find the leftmost leaf on a path which has not been marked yet
			int currNodePos = g.posIndex.get(currNode);
			if( ( g.adj.get(currNodePos).size() > 0 ) && ( marked.get(currNode) == null ) ) {
				marked.put(currNode, true);
				currNode = g.adj.get(currNodePos).get(0);
			}
			else {
				//Visit the node
				this.rightIndex.put(currNode, visited);
				//If the current node is a leaf node then assign to the left index
				//the same value as to the right index
				if( g.adj.get(currNodePos).size() == 0 )
					this.leftIndex.put(currNode, visited);
				visited++;
				//If the current node is the start node, the traversal is complete
				if( currNode == startNode )
					break;
				//Find the next sibling of the current node, if there is one
				int currNodeRevPos = gRev.posIndex.get(currNode);
				String currNodeParent = gRev.adj.get(currNodeRevPos).get(0);
				int currNodeParentPos = g.posIndex.get(currNodeParent);
				int currNodeAdjPos = g.adj.get(currNodeParentPos).indexOf(currNode);
				if( g.adj.get(currNodeParentPos).size() > (currNodeAdjPos + 1) ) {
					currNode = g.adj.get(currNodeParentPos).get(currNodeAdjPos + 1);
				}
				//Otherwise, walk up the tree
				else {
					currNode = currNodeParent;
					int pos = g.posIndex.get(currNode);
					int minVal = Integer.MAX_VALUE;
					for( int i = 0; i < g.adj.get(pos).size(); i++ ) {
						String child = g.adj.get(pos).get(i);
						int childLeftVal = this.leftIndex.get(child);
						if( childLeftVal < minVal )
							minVal = childLeftVal;
					}
					this.leftIndex.put(currNode, minVal);
				}
			}
		}
	}
	
	
	private void processTreeIntervals(Digraph g) {
		//Create the tree code with the initial values created for the spanning tree
		for( int i = 0; i < g.adj.size(); i++ ) {
			String node = g.posIndex.inverse().get(i);
			TreeCode code = new TreeCode(this.leftIndex.get(node), this.rightIndex.get(node) );
			this.treeCodes.put(node, code);
		}
		//Merge the intervals associated with outgoing edges p -> q
		//Process the nodes p in reverse topological order
		List<String> revTopSort = g.reverseTopSort();
		for( int i = 0; i < revTopSort.size(); i++ ) {
			String pNode = revTopSort.get(i);
			TreeCode pCode = this.treeCodes.get(pNode);
			int pNodePos = g.posIndex.get(pNode);
			for( int j = 0; j < g.adj.get(pNodePos).size(); j++ ) {
				String qNode = g.adj.get(pNodePos).get(j);
				TreeCode qCode = this.treeCodes.get(qNode);
				pCode.addCode(qCode);
			}
		}
	}
	
	
	public void printTreeCodes(Digraph g) {
		for( int i = 0; i < g.adj.size(); i++ ) {
			String node = g.posIndex.inverse().get(i);
			TreeCode code = this.treeCodes.get(node);
			int postorder = this.rightIndex.get(node);
			System.out.println(node + ":" + code.toString() + " postorder: " + postorder );
		}
		
	}
	
	
}





