package org.dataone.daks.provexdb.DAO.procedure;

import java.util.ArrayList;

import org.dataone.daks.provexdb.DAO.dbconfig.DatabaseConnector;
import org.dataone.daks.provexdb.DAO.models.dao.mysql.ProvenanceStageDaoImpl;
import org.dataone.daks.provexdb.DAO.models.dto.Edge;
import org.dataone.daks.provexdb.DAO.models.dto.ProvenanceStage;
public class ProvenanceStageProcedure {
	
	private ProvenanceStageDaoImpl psDao  = new ProvenanceStageDaoImpl(DatabaseConnector.getConnection());
	//This method should return all the edges of the trace, req, and stage from the provenance_stage table
	public ArrayList<Edge> getTrace(String traceId, String reqId, String stageId){
		ProvenanceStage[] ps = psDao.findByTraceRequestStage(traceId, reqId, stageId);
		ArrayList<Edge> edges = new ArrayList<Edge>();
		for(int i = 0;i<ps.length;i++){
			//Copy edge data from edge.
			Edge e = new Edge();
			e.setTraceId(ps[i].getTraceId());
			e.setStartNodeId(ps[i].getStartNodeId());
			e.setEndNodeId(ps[i].getEndNodeId());
			e.setEdgeLabel(ps[i].getEdgeLabel());
			
			//Add edge to the return ArrayList.
			edges.add(e);
		}
		return edges;
	}
}


