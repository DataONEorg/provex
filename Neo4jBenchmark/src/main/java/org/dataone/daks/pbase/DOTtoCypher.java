package org.dataone.daks.pbase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class DOTtoCypher {

	//DOTtoCypher <in_dotFile> <out_cypherFile>
	public static void main(String[] args) {
		DOTtoCypher converter = new DOTtoCypher();
		converter.processDOTFile(args[0], args[1]);
	}
	
	
	/*
       digraph G {
	   0;
	   ...
	   0->1 ;
	   ...
	   }
    */
	public void processDOTFile(String dotFile, String cypherFile) {
		String line = null;
		BufferedReader br;
		PrintWriter pw;
		try {
			br = Util.openReadFile(dotFile);
			pw = new PrintWriter(Util.openWriteFile(cypherFile));
			while( (line = br.readLine()) != null ) {
				line = line.trim();
				if( line.length() == 0 )
					continue;
				else if( line.startsWith("digraph") || line.startsWith("}") )
					continue;
				else if( (! line.contains("->")) ) {
					String nodeId = line.substring(0, line.length()-1 );
					//CREATE n={name:"the name"}
					pw.println("CREATE n={name:\"" + nodeId + "\"}");
					pw.flush();
					//System.out.println(nodeId);
				}
				else {
					String nodeId1 = line.substring(0, line.indexOf("->"));
					String nodeId2 = line.substring(line.indexOf("->")+2, line.indexOf(";")-1);
					//START n=node:node_auto_index(name='name1'), m=node:node_auto_index(name='name2') CREATE n-[r:USED]->m
					pw.print("START n=node:node_auto_index(name='" + nodeId1 + "'), ");
					pw.print("m=node:node_auto_index(name='" + nodeId2 + "') ");
					pw.println("CREATE n-[r:USED]->m");
					pw.flush();
					//System.out.println(nodeId1 + " " + nodeId2);
				}
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}


