#Usage: python Ladder.py (outputfile) (n)

import sys

csvfile = open(sys.argv[1], "w")
n = int(sys.argv[2])

csvfile.write("start,a,a0\n")
csvfile.write("start,b,b0\n")

for i in range(n):
	csvfile.write("a" + str(i) + ",a,a"+ str(i+1) + "\n")
	csvfile.write("a" + str(i) + ",b,b"+ str(i+1) + "\n")
	csvfile.write("b" + str(i) + ",b,b"+ str(i+1) + "\n")
	csvfile.write("b" + str(i) + ",a,a"+ str(i+1) + "\n")
	
csvfile.write("a" + str(n) + ",a,end\n")
csvfile.write("b" + str(n) + ",b,end\n")

csvfile.close()
