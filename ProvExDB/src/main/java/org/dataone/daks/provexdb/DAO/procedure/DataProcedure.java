package org.dataone.daks.provexdb.DAO.procedure;

import com.spoledge.audao.db.dao.DaoException;

import org.dataone.daks.provexdb.DAO.dbconfig.DatabaseConnector;
import org.dataone.daks.provexdb.DAO.models.dao.mysql.DataDaoImpl;
import org.dataone.daks.provexdb.DAO.models.dto.Data;

public class DataProcedure {

	private DataDaoImpl dataDao = new DataDaoImpl(DatabaseConnector.getConnection());
	//This method should return information from data table
	public Data getDataInfo(String nodeId){
		Data d = dataDao.findByNodeId(nodeId);
		return d;
	}
	
	public void insertData(Data data){
		try {
			dataDao.insert(data);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
