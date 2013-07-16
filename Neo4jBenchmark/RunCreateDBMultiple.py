import os
import time

path = "C:/Users/Victor/workspaceprovex/propub/Neo4jBenchmark/taskGraphInstances"

start = time.clock()

for file in os.listdir(path) :
    #q_filename = os.path.join(path, file)
    q_filename = path + "/" + file
    if os.path.isfile(q_filename) :
        #print file
        print q_filename
        os.system('mvn exec:java -Dexec.mainClass="org.dataone.daks.pbase.CreateDatabase" -Dexec.args="graphdbmultiple ' + q_filename + '"')  
        #data = open(file, "rb")
        #line = data.readline()
        #print line
        #data.close()

elapsed = (time.clock() - start)

print "\n\nTotal time: " + str(elapsed) + "\n"
