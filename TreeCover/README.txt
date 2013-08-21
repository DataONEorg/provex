

Run command: 

C:\Users\Victor\workspace\TreeCover>mvn exec:java -Dexec.mainClass=org.dataone.daks.treecover.Digraph -Dexec.args="graph.txt false"   

C:\Users\Victor\workspace\TreeCover>mvn exec:java -Dexec.mainClass=org.dataone.daks.treecover.Digraph -Dexec.args="graph100dot.txt true"      

C:\Users\Victor\workspace\TreeCover>mvn exec:java -Dexec.mainClass=org.dataone.daks.treecover.ClientTreeCover -Dexec.args="graph1000dot.txt test"       




mvn clean package -Dmaven.test.skip=true