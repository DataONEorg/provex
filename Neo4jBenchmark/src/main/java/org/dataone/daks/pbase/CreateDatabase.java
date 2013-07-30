package org.dataone.daks.pbase;

public class CreateDatabase {

	//CreateDatabase <database folder> <cypher file> <multiple (true|false)> 
	public static void main(String[] args) {
		String nodeKeysIndexable = null;
		if( args[2].equals("true") )
			nodeKeysIndexable = "name,graph_id";
		else
			nodeKeysIndexable = "name";
		BatchCypher batch = new BatchCypher(args[0], nodeKeysIndexable);
		batch.doBatch(args[1]);
		batch.shutdownDB();
	}

}
