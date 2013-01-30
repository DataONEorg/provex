
To compile the application with maven (assuming it is installed) skipping the tests use the command:

mvn clean package -Dmaven.test.skip=true


To execute the XML file loader application use the command

mvn exec:java -Dexec.mainClass="org.dataone.daks.provexdb.DAO.BuilderWithXML.XMLDAOMapperDynamic" -Dexec.args="evaWorkflow1OPM.xml"    










