package org.dataone.daks.provexdb.DAO.procedure;

import java.util.ArrayList;

import org.dataone.daks.provexdb.DAO.dbconfig.DatabaseConnector;
import org.dataone.daks.provexdb.DAO.models.dao.mysql.PolicyViolationDaoImpl;
import org.dataone.daks.provexdb.DAO.models.dto.PolicyViolation;

public class PolicyViolationProcedure {
	// This method should return all the policy violation information of the
	// trace, req, and stage from the
	// policy_violation table
	private PolicyViolationDaoImpl policyDao = new PolicyViolationDaoImpl(
			DatabaseConnector.getConnection());

	public ArrayList<PolicyViolation> getPolicyViolation(String traceId,
			String reqId, String stageId) {
		PolicyViolation[] pp = policyDao.findByTraceRequestStage(traceId,
				reqId, stageId);
		ArrayList<PolicyViolation> pps = new ArrayList<PolicyViolation>();
		for (int i = 0; i < pp.length; i++) {
			pps.add(pp[i]);
		}
		return pps;
	}

}
