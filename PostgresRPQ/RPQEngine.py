import sys
import time
import psycopg2
from antlr3  import *
from antlr3.tree import *
from RegExpLexer import RegExpLexer
from RegExpParser import RegExpParser

class TreeWalker:
    
    def __init__(self, root, isFour):
        self.isFour = isFour
        self.root = root
    
    def postorder(self, tree):
        for child in tree.getChildren():
            self.postorder(child)
        subexpstr = ""
        for child in tree.getChildren():
            subexpstr += child.subexp.value + " "
        subexpstr += tree.text + " "
        tree.subexp = lambda: None
        setattr(tree.subexp, 'value', subexpstr.strip())
        print tree.text
        
    def doWalk(self, tree):
        self.initializeQuery()
        self.walk(tree)
        self.closeQuery()
        
    def walk(self, tree):
        for child in tree.getChildren():
            self.walk(child)
        if tree.getText() == "|":
            self.outputOr(tree)
        elif tree.getText() == ".":
            self.outputConc(tree)
        elif tree.getText() == "*":
            self.outputStar(tree)
        elif tree.getText() == "+":
            self.outputPlus(tree)
        elif tree.getText() == "-":
            self.outputMinus(tree)
        elif tree.getText() == "?":
            self.outputOptional(tree)
        else:
            pass


    def outputOr(self, node):
        if (self.isFour):
            orQuery = '''INSERT INTO g SELECT compstart, %s, compend, basestart, label2, baseend 
                         FROM g WHERE label1 = %s OR label1 = %s '''
        else:
            orQuery = '''INSERT INTO g SELECT compstart, %s, compend 
                         FROM g WHERE label1 = %s OR label1 = %s '''
        valueTuple = (node.subexp.value, node.getChild(0).subexp.value, node.getChild(1).subexp.value)
        self.cur.execute(orQuery, valueTuple)

    def outputConc(self, node):
        if (self.isFour):
            concQuery = '''INSERT INTO g
                SELECT g1.compstart, %s, g2.compend, g1.basestart, g1.label2, g1.baseend 
                FROM g as g1, g as g2 
                WHERE g1.label1 = %s and g1.compend = g2.compstart and g2.label1 = %s'''
            valueTuple = (node.subexp.value, node.getChild(0).subexp.value, node.getChild(1).subexp.value)
            self.cur.execute(concQuery, valueTuple)
            concQuery = '''INSERT INTO g
                SELECT g1.compstart, %s, g2.compend, g2.basestart, g2.label2, g2.baseend 
                FROM g as g1, g as g2 
                WHERE g1.label1 = %s and g1.compend = g2.compstart and g2.label1 = %s'''
            self.cur.execute(concQuery, valueTuple)
        else:
            concQuery = '''INSERT INTO g
                SELECT g1.compstart, %s, g2.compend 
                FROM g as g1, g as g2 
                WHERE g1.label1 = %s and g1.compend = g2.compstart and g2.label1 = %s'''
            valueTuple = (node.subexp.value, node.getChild(0).subexp.value, node.getChild(1).subexp.value)
            self.cur.execute(concQuery, valueTuple)

    def outputStar(self, node):
        if (self.isFour):
            tcQuery = '''select * from tcwithrecursive4ary(%s, %s)'''
            self.cur.execute(tcQuery, (node.getChild(0).subexp.value, ' *'))
            selfedgeQueryleft = '''INSERT INTO g
                select g.basestart, %s, g.basestart, g.basestart, %s, g.basestart from g where g.label1 = g.label2'''
            selfedgeQueryright = '''INSERT INTO g
                select g.baseend, %s, g.baseend, g.baseend, %s, g.baseend from g where g.label1 = g.label2'''
            self.cur.execute(selfedgeQueryleft, (node.subexp.value, node.subexp.value))
            self.cur.execute(selfedgeQueryright, (node.subexp.value, node.subexp.value))
        else:
            tcQuery = '''select * from tcwithrecursive(%s, %s)'''
            self.cur.execute(tcQuery, (node.getChild(0).subexp.value, ' *'))
            selfedgeQuery = '''INSERT INTO g
                select distinct g.compstart, %s, g.compstart from g
                union
                select distinct g.compend, %s, g.compend from g'''
            self.cur.execute(selfedgeQuery, (node.subexp.value, node.subexp.value))         

    def outputPlus(self, node):
        if (self.isFour):
            tcQuery = '''select * from tcwithrecursive4aryintersect(%s, %s)'''
            self.cur.execute(tcQuery, (node.getChild(0).subexp.value, ' +'))
        else:
            tcQuery = '''select * from tcwithrecursive(%s, %s)'''
            self.cur.execute(tcQuery, (node.getChild(0).subexp.value, ' +'))
        
    def outputMinus(self, node):
        valueTuple = (node.subexp.value, node.getChild(0).subexp.value)
        if (self.isFour):
            self.cur.execute(''' INSERT INTO g SELECT compend, %s, compstart, basestart, label2, baseend
                                 FROM g WHERE label1 = %s; ''', valueTuple)  
        else:
            self.cur.execute("INSERT INTO g SELECT compend, %s, compstart FROM g WHERE label1 = %s;", valueTuple)
    
    def outputOptional(self, node):
        if (self.isFour):
            selfedgeQueryleft = '''INSERT INTO g
                select g.basestart, %s, g.basestart, g.basestart, %s, g.basestart from g where g.label1 = g.label2'''
            selfedgeQueryright = '''INSERT INTO g
                select g.baseend, %s, g.baseend, g.baseend, %s, g.baseend from g where g.label1 = g.label2'''
            self.cur.execute(selfedgeQueryleft, (node.subexp.value, node.getChild(0).subexp.value))
            self.cur.execute(selfedgeQueryright, (node.subexp.value, node.getChild(0).subexp.value))
            optQuery = '''INSERT INTO g SELECT compstart, %s, compend, basestart, label2, baseend FROM g WHERE label1 = %s'''
            valueTuple = (node.subexp.value, node.getChild(0).subexp.value)
            self.cur.execute(optQuery, valueTuple)
        else:
            selfedgeQueryleft = '''INSERT INTO g select g.compstart, %s, g.compstart from g'''
            selfedgeQueryright = '''INSERT INTO g select g.compend, %s, g.compend from g'''
            self.cur.execute(selfedgeQueryleft, (node.subexp.value,))
            self.cur.execute(selfedgeQueryright, (node.subexp.value,))
            optQuery = '''INSERT INTO g SELECT compstart, %s, compend FROM g WHERE label1 = %s'''
            valueTuple = (node.subexp.value, node.getChild(0).subexp.value)
            self.cur.execute(optQuery, valueTuple)


    def initializeQuery(self):
        self.t0 = time.time()
        print("Query starting...")
        configFile = open(sys.argv[3])
        dbname = configFile.readline()
        username = configFile.readline()
        password = configFile.readline()
        configFile.close()
        connectionString = "host=localhost dbname=" + dbname + " user=" + username + " password=" + password
        self.conn = psycopg2.connect(connectionString)
        self.cur = self.conn.cursor()

        if (self.isFour):
            self.cur.execute( '''CREATE TABLE g (
                compstart character varying(100),
                label1 character varying(45),
                compend character varying(100),
                basestart character varying(100),
                label2 character varying(45),
                baseend character varying(100) );
                    ''' )
                    # CREATE INDEX gidx ON g (label1);
            
            insertionQuery = "INSERT INTO g select *, * from " + sys.argv[2]

        else:
            self.cur.execute('''CREATE TABLE g (
                compstart character varying(100),
                label1 character varying(45),
                compend character varying(100) );
                    ''' )
                    # CREATE INDEX gidx ON g (label1);
            
            insertionQuery = "INSERT INTO g select * from " + sys.argv[2]
            
        self.cur.execute(insertionQuery)


    def closeQuery(self):
        print("Closing Query.")
        resultsFile = open("results.txt", "w")
        node = self.root
        workingTuple = (node.subexp.value,)
            
        if (self.isFour):        
            self.cur.execute("SELECT DISTINCT * FROM g WHERE label1 = %s", workingTuple)
            #outputQuery = '''select * from resultoutput4ary(%s)'''
            #self.cur.execute(outputQuery, workingTuple)
        else:
            self.cur.execute("SELECT DISTINCT compstart, compend FROM g WHERE label1 = %s", workingTuple)
            
        counter = 0
        for results in self.cur:
            resultsFile.write(str(results) + "\n")
            counter = counter + 1
        
        resultsFile.close()
        self.cur.execute('''DROP TABLE g''')
        self.conn.close()
        self.cur.close()
        self.t1 = time.time()
        print "Time to process the query: " + str(self.t1-self.t0)
    
        print "Number of results: " + str(counter)


class RPQEngine:
    
    def __init__(self, isFour):
        self.isFour = isFour

    def run(self, query):
        char_stream = ANTLRStringStream(query)
        lexer = RegExpLexer(char_stream)
        tokens = CommonTokenStream(lexer)
        parser = RegExpParser(tokens)
        r = parser.exp()
        # this is the root of the AST
        root = r.tree
        print root.toStringTree()
        walker = TreeWalker(root, self.isFour)
        walker.postorder(root)
        walker.doWalk(root)
        print root.subexp.value


if __name__ == '__main__':
    if (len(sys.argv) < 4):
        print("Usage: python RPQEngine.py (query) (tablename) (config file) (-4 for returning paths)")
    else:    
        if (len(sys.argv) == 5 and sys.argv[4] == "-4"):
            calc = RPQEngine(True)
        else:     
            calc = RPQEngine(False)
        calc.run(sys.argv[1])
        
        


