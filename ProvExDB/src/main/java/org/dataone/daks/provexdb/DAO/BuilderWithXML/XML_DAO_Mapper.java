package org.dataone.daks.provexdb.DAO.BuilderWithXML;

import org.dom4j.Document;
import org.dom4j.Element;

public class XML_DAO_Mapper {
	private static String filePath = "d://evaWorkflow1OPM.xml";
	private static Document doc = XMLLoader.getDocument(filePath);
	private static Element opmGraphRoot = doc.getRootElement();
	
	
	public static Element getActorRootNode(){
		return opmGraphRoot.element("processes");
	}

	public static Element getDataRootNode(){
		return opmGraphRoot.element("artifacts");
	}
	
	public static Element getEdgesRootNode(){
		return opmGraphRoot.element("causalDependencies");
	}
	
	
}
