package propub;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.tree.*;

import types.URContext;
import types.PayloadTreeNode;

/** JTree with missing or custom icons at the tree nodes.
 *  1999 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class BuildTree {
 
	public JTree getRoot() {
	    
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Query:0.0");
		JTree tree = new JTree(root);
		return tree;
	}
	
	private void setChild(PayloadTreeNode child, Hashtable<Integer, ArrayList> ht, Hashtable<Integer, URContext> contexts, Integer childKey) {
		PayloadTreeNode grandChild;
		ArrayList<Integer> ht_list = null;
		Integer i = null;
		
		ht_list = ht.get(childKey);
		Iterator<Integer> iterator = ht_list.iterator(); 
		while(iterator.hasNext()) {
			i = iterator.next();
			grandChild = new PayloadTreeNode("Query:" + i);
			URContext context = contexts.get(i);
			grandChild.setPayload(context);
			grandChild.setId(i);
			child.add(grandChild);	
			if (ht.containsKey(i)) {
				
				System.out.println(i.toString());
				
				setChild(grandChild, ht, contexts, i);
				
			}
		} 	
	}
	
	public JTree getTree(Hashtable<Integer, ArrayList> ht, Hashtable<Integer, URContext> contexts) {
    
		PayloadTreeNode root = new PayloadTreeNode("Query:0.0");
		Integer rootKey = new Integer(0);
		URContext context = contexts.get(rootKey);
		System.out.println("<<<<<<<< context used for root: " + context);
		root.setPayload(context);
		root.setId(rootKey);
		PayloadTreeNode child;
		
		System.out.println(ht.toString());
		
		ArrayList<Integer> ht_list = ht.get(rootKey);
		Integer i = null;
		Iterator<Integer> iterator = ht_list.iterator(); 
		while(iterator.hasNext()) {
			i = iterator.next();
			
			System.out.println("in getTree: "+i.toString());
			
			child = new PayloadTreeNode("Query:" + i);
			context = contexts.get(i);
			child.setPayload(context);
			child.setId(i);
			root.add(child);
			if (ht.containsKey(i)) {
				setChild(child, ht, contexts, i);
			} 
		} 

		JTree tree = new JTree(root);
		tree.expandRow(3); // Expand children to illustrate leaf icons
		
		return tree;
	}
}

