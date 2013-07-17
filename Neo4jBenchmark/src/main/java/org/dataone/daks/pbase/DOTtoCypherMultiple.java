package org.dataone.daks.pbase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class DOTtoCypherMultiple {

	
	public static void main(String[] args) {
		DOTtoCypherMultiple converter = new DOTtoCypherMultiple();
		converter.processDOTFileMultiple(args[0], args[1], args[2], Integer.parseInt(args[3]) );
	}
	
	
	/*
       digraph G {
	   0;
	   ...
	   0->1 ;
	   ...
	   }
    */
	public void processDOTFileMultiple(String dotFile, String cypherFilePrefix, String directory, int n) {
		String line = null;
		BufferedReader br;
		PrintWriter pw;
		String path = "C:/Users/Victor/workspaceprovex/propub/Neo4jBenchmark/";
		try {
			for(int i = 1; i <= n; i++) {
				br = Util.openReadFile(dotFile);
				pw = new PrintWriter(Util.createWriteFile(path + directory, cypherFilePrefix + i + ".txt"));
				while( (line = br.readLine()) != null ) {
					line = line.trim();
					if( line.length() == 0 )
						continue;
					else if( line.startsWith("digraph") || line.startsWith("}") )
						continue;
					else if( (! line.contains("->")) ) {
						String nodeId = line.substring(0, line.length()-1 );
						//CREATE n={name:"the name", trace_id:"the trace id"}
						pw.println("CREATE n={name:\"" + nodeId + "\", " + "graph_id:\"" + i + "\"}" );
						pw.flush();
						//System.out.println(nodeId);
					}
					else {
						String nodeId1 = line.substring(0, line.indexOf("->"));
						String nodeId2 = line.substring(line.indexOf("->")+2, line.indexOf(";")-1);
						//START n=node:node_auto_index(name='name1'), 
						//m=node:node_auto_index(name='name2')
						//WHERE n.graph_id='graphid' AND m.graph_id='graphid'
						//CREATE n-[r:USED]->m
						pw.print("START n=node:node_auto_index(name='" + nodeId1 + "'), ");
						pw.print("m=node:node_auto_index(name='" + nodeId2 + "') ");
						pw.print("WHERE n.graph_id='" + i + "' AND m.graph_id='" + i + "' " );
						pw.println("CREATE n-[r:USED]->m");
						pw.flush();
						//System.out.println(nodeId1 + " " + nodeId2);
					}
				}
				pw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}


