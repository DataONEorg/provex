package org.dataone.daks.provexdb.DAO.PROVXMLBuilder;

import java.util.ArrayList;

import org.dataone.daks.provexdb.DAO.models.dto.*;
import org.dataone.daks.provexdb.DAO.models.dao.*;
import org.dataone.daks.provexdb.DAO.dbconfig.DatabaseConnector;
import org.dom4j.Document;
import org.dom4j.Element;

import com.spoledge.audao.db.dao.DaoException;


public class XMLDAOMapperDynamic {
	
	private String filePath;
	
	private PROVBuilder provBuilder;
	
	public XMLDAOMapperDynamic(String filePath) {
		this.filePath = filePath;
	}
	
	public static void main(String args[]) {
		XMLDAOMapperDynamic mapper = new XMLDAOMapperDynamic(args[0]);
		mapper.doMapping();
	}
	
	public void doMapping() {
		Document doc = XMLLoader.getDocument(this.filePath);
		Element provRoot = doc.getRootElement();
		this.provBuilder = new PROVBuilder();
		this.provBuilder.processDocument(provRoot);
		this.mapActors();
		this.mapData();
		this.mapEdges();
	}
	
	
	public void mapActors() {
		ArrayList<Actor> actors = this.provBuilder.getActors();
		ActorDao actorDao = DaoFactory.createActorDao(DatabaseConnector.getConnection());
		for(Actor actor: actors) {
			actor.setNodeType("i");
			actor.setNodeDesc("No desc for now");
			actor.setValType("v");
			actor.setVal("No value for now");
			actor.setActorId("0");
			try {
				actorDao.insert(actor);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void mapData() {
		ArrayList<Data> dataObjs = this.provBuilder.getData();
		DataDao dataDao = DaoFactory.createDataDao(DatabaseConnector.getConnection());
		for(Data data: dataObjs) {
			data.setNodeType("d");
			data.setNodeDesc("No desc for now");
			data.setValType("v");
			data.setVal("No value for now");
			data.setContId("0");
			try {
				dataDao.insert(data);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void mapEdges() {
		ArrayList<Edge> edges = this.provBuilder.getEdges();
		EdgeDao edgeDao = DaoFactory.createEdgeDao(DatabaseConnector.getConnection());
		for(Edge edge: edges) {
			edge.setTraceId("1");
			edge.setEdgeLabel(edge.getEdgeType().charAt(0) + "");
			try {
				edgeDao.insert(edge);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}


