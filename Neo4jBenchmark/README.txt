To execute the CreateDatabase application use the command

mvn exec:java -Dexec.mainClass="org.dataone.daks.pbase.CreateDatabase" -Dexec.args="graphdb batch.txt false"

mvn exec:java -Dexec.mainClass="org.dataone.daks.pbase.CreateReachabilityBenchmarkFile" -Dexec.args="graphdb graph10000testcases.txt 100"

mvn exec:java -Dexec.mainClass="org.dataone.daks.pbase.RunReachabilityBenchmark" -Dexec.args="graphdb graph1000testcases.txt false"  

mvn exec:java -Dexec.mainClass="org.dataone.daks.pbase.RunReachabilityBenchmarkMultiple" -Dexec.args="graphdbmultiple graph1000testcases.txt"   

