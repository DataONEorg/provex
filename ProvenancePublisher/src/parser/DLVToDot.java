package parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class DLVToDot {

	public DLVToDot() {
		// TODO Auto-generated constructor stub
	}
	
	//Build node and edge list
	ArrayList<String> edges = new ArrayList<String>();
	//ArrayList<String> ppEdgeList = new ArrayList<String>();
	Hashtable <String, String> ppEdges = new Hashtable<String, String>();
	Hashtable <String, String> actorIDs = new Hashtable<String, String>();
	Hashtable <String, String> dataIDs = new Hashtable<String, String>();
	Hashtable <String, String> partOfEdge = new Hashtable<String, String>();

	public void readInFile(String pgFileName, String ppFileName) {
		
		readInFile(pgFileName);
		
		try {
			FileReader     input   = new FileReader(ppFileName);
			BufferedReader bufRead = new BufferedReader(input);			
			//Read through file one line at time. Print line # and line
			String line;   
			line = bufRead.readLine();
			while (line != null){
				String first = line.split(",")[0].split("\\(")[1];
				String second = line.split(",")[1].split("\\)")[0];
				ppEdges.put("\"" + second + "\"->\"" + first + "\"","");
				//ppEdgeList.add("\"" + second + "\"->\"" + first + "\"");
				line = bufRead.readLine();
			}	
			bufRead.close();
		} catch (Exception e){
			/* If no file was passed on the command line, this expception is
			   generated. A message indicating how to the class should be
			   called is displayed */
			System.out.println("Usage: java ReadFile filename\n");          
		} 
		
	}

	public void readInFile(String fileName) {
		try {
			FileReader     input   = new FileReader(fileName);
			BufferedReader bufRead = new BufferedReader(input);
			String line;   
			line = bufRead.readLine();
			while (line != null){
				//processLine(line);
				processLineNew(line);
				line = bufRead.readLine();
			}
			bufRead.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void readArrayList(ArrayList<String> model) {
		Iterator<String> itrDep = model.iterator();
		while (itrDep.hasNext()) {
			//processLine(itr.next());
			processLineNew(itrDep.next());
		}
		Iterator<String> itrDataActor = model.iterator();
		while (itrDataActor.hasNext()) {
			//processLine(itr.next());
			processLineDataActor(itrDataActor.next());
		}
	}
	
	private void processLine(String line) {
		/*
		 * expected file format:	
		 * used(actor,data)
		 * genBy(data,actor)
		 */				
		String first = line.split(",")[0].split("\\(")[1];
		String second = line.split(",")[1].split("\\)")[0];
		
		if (line.contains("used")){ //Used
			actorIDs.put(first, "");
			dataIDs.put(second, "");
			edges.add("\"" + second + "\"->\"" + first + "\"");
		} else if (line.contains("gen_by")){ //GenBy
			actorIDs.put(second, "");
			//there could be two types of gen_by--> gen_by(D,I) or
			//gen_by(I,I). this if is to make sure that actors
			//are identified as actors.
			if (!(actorIDs.containsKey(first))) {
				dataIDs.put(first, "");	
			}
			edges.add("\"" + second + "\"->\"" + first + "\"");
		} else if (line.contains("idep")){ //Invocation
			actorIDs.put(second, "");
			actorIDs.put(first, "");
			edges.add("\"" + first + "\"->\"" + second + "\"");
		} else if (line.contains("ddep")){ //Data
			dataIDs.put(second, "");
			dataIDs.put(first, "");
			edges.add("\"" + first + "\"->\"" + second + "\"");
		}		
	}
	

	private void processLineNew(String line) {
		/*
		 * expected file format:	
		 * l_dep(node,node)
		 */				
		String first = line.split(",")[0].split("\\(")[1];
		String second = line.split(",")[1].split("\\)")[0];
		
		if (line.contains("l_dep")){
			edges.add("\"" + second + "\"->\"" + first + "\"");
			partOfEdge.put(first, first);
			partOfEdge.put(second, second);
		} else if (line.contains("pv")){ 
			ppEdges.put("\"" + second + "\"->\"" + first + "\"","");
		} 	
	}
	private void processLineDataActor(String line) {
		/*
		 * expected file format:	
		 * data(Data,URL)
		 * actor(Invocation, Actor)
		 */				
		String first = line.split(",")[0].split("\\(")[1];
		
        if (line.contains("data")){
        	if (partOfEdge.containsKey(first)) {
        		dataIDs.put(first, "");	
        	}
		} else if (line.contains("actor")){ 
        	if (partOfEdge.containsKey(first)) {
        		actorIDs.put(first, "");
        	}
		} 		
	}
	
	public void readInData(String [][] data){
	/*
	 * data[][] = {data(node,node,edgeType)}	
	 * node     = {data,actor}
	 * edgeType = {u=used,g=genBy} 		
	 * 
	 */
		for(int x = 0; x < data.length; x++) {
			if (data[x][2].equals("u")){ //Used
				actorIDs.put(data[x][0], "");
				dataIDs.put(data[x][1], "");
				edges.add("\"" + data[x][0] + "\"->\"" + data[x][1] + "\"");
			} else if (data[x][2].equals("g")){ //GenBy
				actorIDs.put(data[x][1], "");
				if (!(actorIDs.containsKey(data[x][0]))) {
					dataIDs.put(data[x][0], "");	
				}
				edges.add("\"" + data[x][0] + "\"->\"" + data[x][1] + "\"");
			} else if (data[x][2].equals("i")){ //Invocation
				actorIDs.put(data[x][1], "");
				actorIDs.put(data[x][0], "");
				edges.add("\"" + data[x][0] + "\"->\"" + data[x][1] + "\"");
			} else { //Data
				dataIDs.put(data[x][1], "");
				dataIDs.put(data[x][0], "");
				edges.add("\"" + data[x][0] + "\"->\"" + data[x][1] + "\"");
			}
		}
	}
	
	public void prepareDOTFile(String dotFileName) {

		BufferedWriter outfile = null;
		//Create the file on disk, if unable to create file, error out.
		
		try{
			outfile = new BufferedWriter(new FileWriter(dotFileName));
		} catch(IOException e){
			System.out.println("Warning: unable to create output file.");
			System.exit(1);
		}
		
		String dotfile = "digraph G {\n";

		dotfile = dotfile + "rankdir=LR\n";
		dotfile = dotfile + "{ node [shape=circle color=none width=0.4 fixedsize=true fontname=arial fontsize=10 style=filled fillcolor=blanchedalmond]\n";
		
		for (Enumeration<String> e = dataIDs.keys(); e.hasMoreElements();){
			String DataID = e.nextElement();
			dotfile = dotfile + "\"" + DataID + "\"\n";
		}
		
		dotfile = dotfile + "}\n";
		dotfile = dotfile + "{ node [shape=box color=none width=0.3 height=0.3 fontname=arial fontsize=11 style=filled fillcolor=cadetblue1]\n";
		
		for (Enumeration<String> e = actorIDs.keys(); e.hasMoreElements();){
			String ActorID = e.nextElement();
			dotfile = dotfile + "\"" + ActorID + "\"\n";
		}
		
		dotfile = dotfile + "}\n";
		dotfile = dotfile + "edge [style=dashed dir=back]\n";
		
		for (int x = 0; x < edges.size(); x++){
			if ((!ppEdges.containsKey(edges.get(x)))) {
				dotfile = dotfile + edges.get(x) + "[color=darkslategray] \n";	
			} else {
				dotfile = dotfile + edges.get(x) + "[color=red] \n";
			}
		}
				
		dotfile = dotfile + "}\n";
		
		//Create the file on disk, if unable to create file, error out.
		try{
			outfile.append(dotfile);
			outfile.close();
		} catch(IOException e){
			System.out.println("Warning: unable to create output file.");
			System.exit(1);
		}		
	}
}
