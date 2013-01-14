package org.dataone.daks.provexdb.DAO.BuilderWithXML;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class XMLLoader {
	
	
	 public static Document getDocument( final String xmlFileName )
	   {
	      Document document = null;
	      SAXReader reader = new SAXReader();
	      try
	      {
	         document = reader.read( xmlFileName );
	      }
	      catch (DocumentException e)
	      {
	         e.printStackTrace();
	      }
	      return document;
	   }

}
