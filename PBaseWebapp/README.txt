
1) Compile the application with maven (assuming it is installed) with the command:

mvn clean package

2) Configure tomcat

Make sure JAVA_HOME and CATALINA_HOME are setup correctly

Add these lines to conf/tomcat-users.xml

<role rolename="manager-gui"/>  
<role rolename="manager-script"/>  
<role rolename="manager-jmx"/>  
<role rolename="manager-status"/>  
<user username="tomcat" password="tomcat" roles="manager-gui,manager-script,manager-jmx,manager-status"/>  

3) While running tomcat deploy the file target/ProPubRest.war (generated in step 1) with the tomcat manager


4) Open the file jquery.html in a browser to call the Java restful service

This tutorial explains how to use Maven to create the base for this example

Oracle tutorial: 

http://docs.oracle.com/cd/E19226-01/820-7627/giqdq/index.html



Url of deployed application:

http://localhost:8080/PBaseWebapp/

Url of restful web service:

http://localhost:8080/PBaseWebapp/webresources/graphexample



To add a local jar dependency:

C:\Users\Victor\workspaceprovex\propub\ProvEx\ProvExRestApp>mvn install:install-file  -DlocalReposit
oryPath=lib   -DcreateChecksum=true -Dpackaging=jar -Dfile=RPQEngine-1.0-SNAPSHOT.jar  -DgroupId=org
.dataone.daks -DartifactId=RPQEngine -Dversion=1.0-SNAPSHOT







