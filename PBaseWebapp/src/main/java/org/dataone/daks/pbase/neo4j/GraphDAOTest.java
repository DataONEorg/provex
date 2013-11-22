package org.dataone.daks.pbase.neo4j;

public class GraphDAOTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphDAO dao = GraphDAO.getInstance();
    	dao.init("graphdbs/ComboBreaker");
    	String retVal = null;
    	try {
    		//retVal = dao.executeQuery("START n=node(*) WHERE HAS(n.wfID) AND HAS(n.type) RETURN DISTINCT n.wfID, n.type;");
    		retVal = dao.executeQuery("START n=node(*) WHERE HAS(n.wfID) AND HAS(n.type) RETURN DISTINCT n.wfID, n;");
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	System.out.println(retVal);
	}

}
