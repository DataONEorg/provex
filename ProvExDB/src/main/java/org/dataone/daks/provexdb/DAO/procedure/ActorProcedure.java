package org.dataone.daks.provexdb.DAO.procedure;

import com.spoledge.audao.db.dao.DaoException;

import org.dataone.daks.provexdb.DAO.dbconfig.DatabaseConnector;
import org.dataone.daks.provexdb.DAO.models.dao.mysql.ActorDaoImpl;
import org.dataone.daks.provexdb.DAO.models.dto.Actor;

public class ActorProcedure {

	private ActorDaoImpl actorDao = new ActorDaoImpl(DatabaseConnector.getConnection());
	//This method should return information from actor table
	public Actor getActorInfo(String nodeId){
		return actorDao.findByNodeId(nodeId);
	}
	
	public void inserActor(Actor actor){
		try {
			actorDao.insert(actor);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
