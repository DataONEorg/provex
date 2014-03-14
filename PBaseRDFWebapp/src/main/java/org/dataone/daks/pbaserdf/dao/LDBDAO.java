package org.dataone.daks.pbaserdf.dao;

import com.hp.hpl.jena.query.Dataset ;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.tdb.TDBFactory;


public class LDBDAO {
	
	
	private Dataset ds;
	
	private String directory;
	
	private Model model;
	
	private static final LDBDAO instance = new LDBDAO();
	
	
	public LDBDAO() {

	}
	
	
	public static LDBDAO getInstance() {
    	return instance;
    }
	
	
	public synchronized void init(String directory) {
		if( this.ds == null || ( this.directory != null && ! this.directory.equals(this.directory) ) ) {
			if( this.ds != null )
				this.ds.close();
			this.directory = directory;
			Dataset ds = TDBFactory.createDataset(this.directory);
			this.ds = ds;
			this.directory = directory;
			this.model = this.ds.getDefaultModel();
		}
	}
	
	
	public synchronized void shutdown() {
		this.model.close();
		this.ds.close();
		this.ds = null;
		this.model = null;
	}
	
	
	public void addModel(Model m) {
		this.model.add(m);
	}
	
	
}




