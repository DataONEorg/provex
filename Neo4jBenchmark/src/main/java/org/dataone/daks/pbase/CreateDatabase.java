package org.dataone.daks.pbase;

public class CreateDatabase {

	public static void main(String[] args) {
		BatchCypher batch = new BatchCypher(args[0]);
		batch.doBatch(args[1]);
		batch.shutdownDB();
	}

}
