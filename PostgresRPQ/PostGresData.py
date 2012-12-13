#Usage: python PostGresData.py (input file) (tablename) (config file)

import psycopg2
import sys

csvfile = open(sys.argv[1], "r")

configFile = open(sys.argv[3])
		
dbname = configFile.readline()
username = configFile.readline()
password = configFile.readline()

configFile.close()

connectionString = "host=localhost dbname=" + dbname + " user=" + username + " password=" + password

conn = psycopg2.connect(connectionString)

cur = conn.cursor()

dbnameTuple = sys.argv[2]

cur.execute('DROP TABLE IF EXISTS %s' %(dbnameTuple,))

cur.execute("""CREATE TABLE %s (startNode character varying(100), 
			label character varying(45), endNode character varying(100));""" %(dbnameTuple,))

for line in csvfile:
	data = line.split(",")
	cur.execute('INSERT INTO %s (startNode, label, endNode) VALUES (%s, %s, %s)'
			 %(dbnameTuple, "'" + data[0].strip() + "'", "'" + data[1].strip() + "'", "'" + data[2].strip() + "'" ))  

csvfile.close()

cur.close()

conn.commit()

conn.close()
