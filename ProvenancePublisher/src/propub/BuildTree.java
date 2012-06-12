package propub;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.tree.*;

/** JTree with missing or custom icons at the tree nodes.
 *  1999 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class BuildTree {
 
	public JTree getRoot() {
	    
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Query:0.0");
		JTree tree = new JTree(root);
		return tree;
	}
	
	private void setChild(DefaultMutableTreeNode child, Hashtable<Integer, ArrayList> ht, Integer childKey) {
		DefaultMutableTreeNode grandChild;
		ArrayList<Integer> ht_list = null;
		Integer i = null;
		
		ht_list = ht.get(childKey);
		Iterator<Integer> iterator = ht_list.iterator(); 
		while(iterator.hasNext()) {
			i = iterator.next();
			grandChild = new DefaultMutableTreeNode("Query:" + i);
			child.add(grandChild);	
			if (ht.containsKey(i)) {
				
				System.out.println(i.toString());
				
				setChild(grandChild, ht, i);
				
			}
		} 	
	}
	
	public JTree getTree(Hashtable<Integer, ArrayList> ht) {
    
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Query:0.0");
		DefaultMutableTreeNode child;
		
		System.out.println(ht.toString());
		
		ArrayList<Integer> ht_list = ht.get(new Integer(0));
		Integer i = null;
		Iterator<Integer> iterator = ht_list.iterator(); 
		while(iterator.hasNext()) {
			i = iterator.next();
			
			System.out.println("in getTree: "+i.toString());
			
			child = new DefaultMutableTreeNode("Query:" + i);
			root.add(child);
			if (ht.containsKey(i)) {
				setChild(child, ht, i);
			} 
		} 

		JTree tree = new JTree(root);
		tree.expandRow(3); // Expand children to illustrate leaf icons
		
		return tree;
	}
}

