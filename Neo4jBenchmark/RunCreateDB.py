import os
import time
import sys

path = "C:/Users/Victor/workspaceprovex/propub/Neo4jBenchmark"
file = sys.argv[1]

start = time.clock()

q_filename = path + "/" + file
os.system('mvn exec:java -Dexec.mainClass="org.dataone.daks.pbase.CreateDatabase" -Dexec.args="graphdb ' + q_filename + '"')  

elapsed = (time.clock() - start)

print "\n\nTotal time: " + str(elapsed) + "\n"
