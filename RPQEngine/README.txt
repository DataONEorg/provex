
To run the application from Maven execute


C:\Users\Victor\workspaceprovex\RPQEngine>mvn exec:java -Dexec.mainClass="org.dataone.daks.rpq.App"


and with parameters

mvn exec:java -Dexec.mainClass="org.dataone.daks.rpq.App" -Dexec.args="arg0 arg1 arg2"  



To force updates in Maven execute

mvn clean install -U



In windows the local maven repository is located in


C:\Documents and Settings\Victor\.m2\repository




