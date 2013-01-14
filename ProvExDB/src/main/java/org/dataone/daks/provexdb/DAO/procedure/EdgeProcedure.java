package org.dataone.daks.provexdb.DAO.procedure;

import java.util.ArrayList;

import com.spoledge.audao.db.dao.DaoException;

import org.dataone.daks.provexdb.DAO.dbconfig.DatabaseConnector;
import org.dataone.daks.provexdb.DAO.models.dao.mysql.*;
import org.dataone.daks.provexdb.DAO.models.dto.*;

public class EdgeProcedure {
	private EdgeDaoImpl edgeDao = new EdgeDaoImpl(
			DatabaseConnector.getConnection());
	private ActorDaoImpl actorDao = new ActorDaoImpl(
			DatabaseConnector.getConnection());
	private DataDaoImpl dataDao = new DataDaoImpl(
			DatabaseConnector.getConnection());

	// This method should save data into edge, data, actor tables
	public void saveTrace(ArrayList<Edge> edge, String traceId) {
		for (Edge e : edge) {
			try {
				edgeDao.insert(e);	
				// Check whether we need add new node or not.
				if (e.getEdgeType() == "genBy") {
					// Function: addNewNode(DataId, ActorId)
					// For "genBy" type edge, the end node is data and the start
					// node is actor.
					addNewNode(e.getEndNodeId(), e.getStartNodeId());
				} else if (e.getEdgeType() == "used") {
					// For "used" type edge, the end node is actor and the start
					// node is data.
					addNewNode(e.getStartNodeId(), e.getEndNodeId());
				}
			} catch (DaoException e1) {
				// TODO Auto-generated catch block
				System.out.println("Insert into Edge table fail.");
				e1.printStackTrace();
			}
		}
	}

	// This is just a overloaded method. This method should save data into edge,
	// data, actor tables
	public void saveTrace(ArrayList<Edge> edge, ArrayList<Actor> actor,
			ArrayList<Data> data, String traceId) {

		for (Edge e : edge) {
			try {
				edgeDao.insert(e);
			} catch (DaoException e1) {
				// TODO Auto-generated catch block
				System.out.println("Insert into Edge table fail.");
				e1.printStackTrace();
			}
		}
		for (Actor a : actor) {

			try {
				actorDao.insert(a);
			} catch (DaoException e1) {
				// TODO Auto-generated catch block
				System.out.println("Insert into Actor table fail.");
				e1.printStackTrace();
			}
		}
		for (Data d : data) {

			try {
				dataDao.insert(d);
			} catch (DaoException e1) {
				// TODO Auto-generated catch block
				System.out.println("Insert into Data table fail.");
				e1.printStackTrace();
			}
		}
		DatabaseConnector.removeConnection();
	}

	// This method should return all the edges of the trace from the edge table
	public ArrayList<Edge> getTrace(String traceId) {
		Edge[] edges = edgeDao.findByTrace(traceId);
		ArrayList<Edge> es = new ArrayList<Edge>();
		for (int i = 0; i < edges.length; i++) {
			es.add(edges[i]);
		}
		return es;

	}

	private void addNewNode(String dataNodeId, String actorNodeId) {

		// Check the data node.
		if (dataDao.findByNodeId(dataNodeId) == null) {
			Data d = new Data();
			d.setNodeId(dataNodeId);
			d.setNodeType("d");
			try {
				dataDao.insert(d);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Check the actor node.
		if (actorDao.findByNodeId(actorNodeId) == null) {
			Actor a = new Actor();
			a.setNodeId(actorNodeId);
			a.setNodeType("i");
			try {
				actorDao.insert(a);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
