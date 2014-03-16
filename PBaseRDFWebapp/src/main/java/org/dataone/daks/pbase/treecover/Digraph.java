
package org.dataone.daks.pbase.treecover;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Stack;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;


public class Digraph {

    List<List<String>> adj;
    BiMap<String, Integer> posIndex;
    
    
    //Variables used to detect cycles
    private boolean[] marked;        // marked[v] = has vertex v been marked?
    private int[] edgeTo;            // edgeTo[v] = previous vertex on path to v
    private boolean[] onStack;       // onStack[v] = is vertex on the stack?
    private Stack<String> cycle;    // directed cycle (or null if no such cycle)
    
    private Stack<String> reversePost;    // used to store the topological sort (reverse postorder)
    
    
   /**
     * Create an empty digraph.
     */
    public Digraph() {
    	this.adj = new ArrayList<List<String>>();
    	this.posIndex = HashBiMap.create();
    }

   /**
     * Create a digraph from input file of the form
     * 
     * nodeId_i1	nodeId_j1
     * 
     * . . .
     * 
     * nodeId_in	nodeId_jn
     */  
    public void createFromFile(String infile) {
		BufferedReader buff = null;
		try {
			buff = new BufferedReader(new FileReader(infile));
			processData(buff);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
    
    
    /**
     * Create the graph from a dot file
     */
    public void createFromDotFile(String dotFile) {
		BufferedReader buff = null;
		try {
			buff = new BufferedReader(new FileReader(dotFile));
			processDotData(buff);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
    
    
    /**
     * Same as crateFromFile, only that the graph is read from an InputStream
     */
    public void createFromInputStream(InputStream istream) {
		BufferedReader buff = null;
		try {
			buff = new BufferedReader(new InputStreamReader(istream));
			processData(buff);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
    
    
    /**
     * Create the graph from an InputStream representing a dot file
     */
    public void createFromDotInputStream(InputStream istream) {
		BufferedReader buff = null;
		try {
			buff = new BufferedReader(new InputStreamReader(istream));
			processDotData(buff);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
    
    
    /**
     * Process the data read from a BufferedReader associated with an InputStream or FileReader
     * to create the graph
     */
    private void processData(BufferedReader buff) throws IOException {
		String line = null;
		while((line = buff.readLine()) != null) {
			if ( line.trim().length() == 0 )
				continue;
			StringTokenizer tok = new StringTokenizer(line);
			String id1 = tok.nextToken();
			String id2 = tok.nextToken();
			this.addEdge(id1, id2);
		}
	}
    
    
    /**
     * Create the graph from a dot file
    */
	/*
    digraph G {
	   0;
	   ...
	   0->1 ;
	   ...
	   }
	*/
	public void processDotData(BufferedReader buff) throws IOException {
		String line = null;
		while( (line = buff.readLine()) != null ) {
			line = line.trim();
			if( line.length() == 0 )
				continue;
			else if( line.startsWith("digraph") || line.startsWith("}") )
				continue;
			//Lines defining the nodes, ignore them
			else if( (! line.contains("->")) ) {
				;
			}
			//Lines defining the edges
			else {
				String id1 = line.substring(0, line.indexOf("->"));
				String id2 = line.substring(line.indexOf("->")+2, line.indexOf(";")-1);
				this.addEdge(id1, id2);
			}
		}
	}

        
   /**
     * Return the number of vertices in the digraph.
     */
    public int nVertices() {
        return this.adj.size();
    }

   /**
     * Return the number of edges in the digraph.
     */
    public int nEdges() {
        int n = 0;
        for( int i = 0; i < this.adj.size(); i++ )
        	n += this.adj.get(i).size();
    	return n;
    }

   /**
     * Add the directed edge v->w to the digraph.
     */
    public void addEdge(String v, String w) {
    	//Check if the nodes v and w are already in the adjacency table
    	if( ! this.posIndex.containsKey(v) ) {
    		List<String> vAdjList = new ArrayList<String>();
			this.adj.add(vAdjList);
			this.posIndex.put(v, this.adj.size()-1);
		}
    	if( ! this.posIndex.containsKey(w) ) {
    		List<String> wAdjList = new ArrayList<String>();
			this.adj.add(wAdjList);
			this.posIndex.put(w, this.adj.size()-1);
		}
		this.adj.get(this.posIndex.get(v)).add(w);
    }
    
    
    /**
     * Remove a directed edge v->w to the digraph, returns true if
     * it was removed and false if it was not found.
     */
    public boolean removeEdge(String v, String w) {
    	boolean retVal = false;
    	if( this.posIndex.containsKey(v) ) {
    		int vPos = this.posIndex.get(v);
    		retVal = this.adj.get(vPos).remove(w);
		}
		return retVal;
    }
    
    
    /**
     * Determine if a directed edge v->w is in the digraph.
     */
    public boolean hasEdge(String v, String w) {
    	boolean retVal = false;
    	if( this.posIndex.containsKey(v) ) {
    		int vPos = this.posIndex.get(v);
    		retVal = this.adj.get(vPos).contains(w);
		}
		return retVal;
    }

    
   /**
     * Return the list of vertices pointed to from vertex v,
     * null if v is not in the adjacency table.
     */
    public List<String> getAdjList(String v) {
        List<String> adjList = null;
        if( this.posIndex.containsKey(v) )
        	adjList = this.adj.get(this.posIndex.get(v));
        return adjList;
    }
    
    
    /**
     * Return the list of vertices at a given position,
     * @throws java.lang.IndexOutOfBoundsException if the position is not valid
     */
    public List<String> getAdjListAt(int i) {
        List<String> adjList = null;
        if ( i < 0 || i >= this.nVertices() ) 
        	throw new IndexOutOfBoundsException( i + " is not a valid position for a vertex.");
        adjList = this.adj.get(i);
        return adjList;
    }


    /**
     * Return a reverse graph indicating incoming edges , i.e. edges u -> v become v -> u
     */
    public Digraph reverse() {
    	Digraph rDigraph = new Digraph();
    	for ( int i = 0; i < this.adj.size(); i++ ) {
            String vtx1 = this.posIndex.inverse().get(i);
            Iterator<String> it = this.getAdjListAt(i).iterator();
            while ( it.hasNext() ) {
            	String vtx2 = it.next();
            	rDigraph.addEdge(vtx2, vtx1);
            }
        }
    	return rDigraph;
    }
    
    
    /**
     * Return a copy of this directed graph
     */
    public Digraph copy() {
    	Digraph copyDigraph = new Digraph();
    	for ( int i = 0; i < this.adj.size(); i++ ) {
            String vtx1 = this.posIndex.inverse().get(i);
            Iterator<String> it = this.getAdjListAt(i).iterator();
            while ( it.hasNext() ) {
            	String vtx2 = it.next();
            	copyDigraph.addEdge(vtx1, vtx2);
            }
        }
    	return copyDigraph;
    }
    
    
    /**
     * Obtain a reverse topological sort of the nodes using DFS. The method that looks
     * for a cycle also computes the topological sort (in reverse postorder).
     * If a cycle is found no topological sort is possible.
     */
    public List<String> reverseTopSort() {
    	List<String> retVal = null;
    	if( ! this.hasCycle() )
    		retVal = this.reversePost;
    	return retVal;
    }
    
    
    public List<String> topSort() {
    	List<String> retVal = null;
    	if( ! this.hasCycle() ) {
    		retVal = new ArrayList<String>();
    		for(int i = this.reversePost.size()-1; i >= 0; i--)
    			retVal.add(this.reversePost.get(i));
    	}
    	return retVal;
    }
    
    
    /**
     * Determine whether the directed graph has a cycle and obtain
     * a stack with the nodes of such cycle
     */
    public Stack<String> findCycle() {
    	this.cycle = null;
    	this.marked  = new boolean[this.adj.size()];
        this.onStack = new boolean[this.adj.size()];
        this.edgeTo  = new int[this.adj.size()];
        this.reversePost = new Stack<String>();
        for (int i = 0; i < this.adj.size(); i++)
            if ( ! this.marked[i] )
            	this.dfs(i);
        return this.cycle;
    }
    
    
    /**
     * Determine whether the directed graph has a cycle
     */
    public boolean hasCycle() {
    	this.findCycle();
        return this.cycle != null;
    }
    
    
    /**
     * Provides a string representation of a List of vertices
     */
    public String verticesListToString(List<String> verticesList) {
    	StringBuilder s = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        if( verticesList != null ) {
        	for ( int i = 0; i < verticesList.size(); i++ ) {
        		String nodeStr = verticesList.get(i);
        		s.append(nodeStr + NEWLINE);
        	}
        }
        return s.toString();
    }
    
    
    /**
     * Provides a string representation of a List of vertices in reverse order
     */
    public String verticesListToStringReverse(List<String> verticesList) {
    	StringBuilder s = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        if( verticesList != null ) {
        	for ( int i = verticesList.size()-1; i >= 0 ; i-- ) {
        		String nodeStr = verticesList.get(i);
        		s.append(nodeStr + NEWLINE);
        	}
        }
        return s.toString();
    }
    
    
    // computes either the topological order or find a directed cycle
    private void dfs(int v) {
        this.onStack[v] = true;
        this.marked[v] = true;
        Iterator<String> it = this.getAdjListAt(v).iterator();
        while ( it.hasNext() ) {
        	String wStr = it.next();
        	int w = this.posIndex.get(wStr);
            // short circuit if directed cycle found
            if ( this.cycle != null )
            	return;
            //found new vertex, so recur
            else if ( ! this.marked[w] ) {
                this.edgeTo[w] = v;
                this.dfs(w);
            }
            // trace back directed cycle
            else if ( this.onStack[w] ) {
                this.cycle = new Stack<String>();
                for (int x = v; x != w; x = this.edgeTo[x]) {
                    this.cycle.push( this.posIndex.inverse().get(x) );
                }
                this.cycle.push( this.posIndex.inverse().get(w) );
                this.cycle.push( this.posIndex.inverse().get(v) );
            }
        }
        this.onStack[v] = false;
        this.reversePost.push(this.posIndex.inverse().get(v));
    }
   
    
    /**
     * Return a string representation of the digraph.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        s.append(this.nVertices() + " vertices, " + this.nEdges() + " edges " + NEWLINE);
        for ( int i = 0; i < this.adj.size(); i++ ) {
            s.append( String.format("%s: ", this.posIndex.inverse().get(i) ));
            Iterator<String> it = this.getAdjListAt(i).iterator();
            while ( it.hasNext() ) {
            	String w = it.next();
            	s.append(String.format("%s ", w));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

   /**
     * Test client.
     */
    public static void main(String[] args) {
        Digraph g = new Digraph();
        if( args[1].equals("false") )
        	g.createFromFile(args[0]);
        else if( args[1].equals("true") )
        	g.createFromDotFile(args[0]);
        System.out.println("Graph: ");
        System.out.println(g.toString());
        System.out.println();
        System.out.println("Reverse graph: ");
        System.out.println(g.reverse().toString());
        System.out.println();
        System.out.println("Has cycle: " + g.hasCycle());
        System.out.println();
        System.out.println("Cycle: ");
        System.out.println(g.verticesListToStringReverse(g.findCycle()));
        System.out.println();
        System.out.println("Topological sort: ");
        System.out.println(g.verticesListToString(g.topSort()));
        System.out.println();
        System.out.print("Edge removed: ");
        System.out.println(g.removeEdge("d3", "d2"));
        System.out.println();
        System.out.println("Graph updated: ");
        System.out.println(g.toString());
        System.out.println();
        System.out.println();
        System.out.println("Topological sort: ");
        System.out.println(g.verticesListToString(g.topSort()));
    }

}



