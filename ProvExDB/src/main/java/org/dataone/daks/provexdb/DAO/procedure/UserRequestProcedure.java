package org.dataone.daks.provexdb.DAO.procedure;

import java.util.ArrayList;
import java.util.Random;

import com.spoledge.audao.db.dao.DaoException;

import org.dataone.daks.provexdb.DAO.dbconfig.DatabaseConnector;
import org.dataone.daks.provexdb.DAO.models.dao.mysql.UserRequestDetailDaoImpl;
import org.dataone.daks.provexdb.DAO.models.dao.mysql.UserRequestMasterDaoImpl;
import org.dataone.daks.provexdb.DAO.models.dto.UserRequestDetail;
import org.dataone.daks.provexdb.DAO.models.dto.UserRequestMaster;

public class UserRequestProcedure {
	private UserRequestDetailDaoImpl urdetail  = new UserRequestDetailDaoImpl(DatabaseConnector.getConnection());
	private UserRequestMasterDaoImpl urmaster= new UserRequestMasterDaoImpl(DatabaseConnector.getConnection());
	
	
	//This method should save the data into user_request_master and user_request_detail tables
	//and return the reqId
	public String saveUserRequest(ArrayList<UserRequestDetail> userRequest, String traceId, String algoType){
		//Generate a userRequest.
		String reqId;
		while(true){
			reqId = ""+new Random().nextInt();
			if(urdetail.findByReqId(reqId)==null){
				break;
			}
		}
		UserRequestMaster urm = new UserRequestMaster();
		urm.setTraceId(traceId);
		urm.setReqId(reqId);
		
		for(UserRequestDetail ur : userRequest){
			try {
				urdetail.insert(ur);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				System.out.println("Insert the user request detail.");
				e.printStackTrace();
			}
		}
		
		try {
			urmaster.insert(urm);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			System.out.println("Insert the user request detail.");
			e.printStackTrace();
		}
		
		return reqId;
	}
}
