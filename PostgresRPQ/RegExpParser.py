# $ANTLR 3.1.3 Mar 18, 2009 10:09:25 RegExp.g 2012-07-07 22:54:46

import sys
from antlr3 import *
from antlr3.compat import set, frozenset

from antlr3.tree import *



# for convenience in actions
HIDDEN = BaseRecognizer.HIDDEN

# token types
WS=10
T__16=16
T__15=15
T__18=18
T__17=17
IntegerNumber=7
T__12=12
T__11=11
LETTER=5
T__14=14
T__13=13
CHAR=8
DIGIT=6
ID=4
EOF=-1
STRING=9

# token names
tokenNames = [
    "<invalid>", "<EOR>", "<DOWN>", "<UP>", 
    "ID", "LETTER", "DIGIT", "IntegerNumber", "CHAR", "STRING", "WS", "'|'", 
    "'.'", "'*'", "'+'", "'?'", "'-'", "'('", "')'"
]




class RegExpParser(Parser):
    grammarFileName = "RegExp.g"
    antlr_version = version_str_to_tuple("3.1.3 Mar 18, 2009 10:09:25")
    antlr_version_str = "3.1.3 Mar 18, 2009 10:09:25"
    tokenNames = tokenNames

    def __init__(self, input, state=None, *args, **kwargs):
        if state is None:
            state = RecognizerSharedState()

        super(RegExpParser, self).__init__(input, state, *args, **kwargs)






        self._adaptor = None
        self.adaptor = CommonTreeAdaptor()
                


        
    def getTreeAdaptor(self):
        return self._adaptor

    def setTreeAdaptor(self, adaptor):
        self._adaptor = adaptor

    adaptor = property(getTreeAdaptor, setTreeAdaptor)


    class exp_return(ParserRuleReturnScope):
        def __init__(self):
            super(RegExpParser.exp_return, self).__init__()

            self.tree = None




    # $ANTLR start "exp"
    # RegExp.g:15:1: exp : term ( '|' term )* ;
    def exp(self, ):

        retval = self.exp_return()
        retval.start = self.input.LT(1)

        root_0 = None

        char_literal2 = None
        term1 = None

        term3 = None


        char_literal2_tree = None

        try:
            try:
                # RegExp.g:15:9: ( term ( '|' term )* )
                # RegExp.g:15:11: term ( '|' term )*
                pass 
                root_0 = self._adaptor.nil()

                self._state.following.append(self.FOLLOW_term_in_exp42)
                term1 = self.term()

                self._state.following.pop()
                self._adaptor.addChild(root_0, term1.tree)
                # RegExp.g:15:16: ( '|' term )*
                while True: #loop1
                    alt1 = 2
                    LA1_0 = self.input.LA(1)

                    if (LA1_0 == 11) :
                        alt1 = 1


                    if alt1 == 1:
                        # RegExp.g:15:17: '|' term
                        pass 
                        char_literal2=self.match(self.input, 11, self.FOLLOW_11_in_exp45)

                        char_literal2_tree = self._adaptor.createWithPayload(char_literal2)
                        root_0 = self._adaptor.becomeRoot(char_literal2_tree, root_0)

                        self._state.following.append(self.FOLLOW_term_in_exp48)
                        term3 = self.term()

                        self._state.following.pop()
                        self._adaptor.addChild(root_0, term3.tree)


                    else:
                        break #loop1



                retval.stop = self.input.LT(-1)


                retval.tree = self._adaptor.rulePostProcessing(root_0)
                self._adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop)


            except RecognitionException, re:
                self.reportError(re)
                self.recover(self.input, re)
                retval.tree = self._adaptor.errorNode(self.input, retval.start, self.input.LT(-1), re)
        finally:

            pass
        return retval

    # $ANTLR end "exp"

    class term_return(ParserRuleReturnScope):
        def __init__(self):
            super(RegExpParser.term_return, self).__init__()

            self.tree = None




    # $ANTLR start "term"
    # RegExp.g:17:1: term : factor ( '.' factor )* ;
    def term(self, ):

        retval = self.term_return()
        retval.start = self.input.LT(1)

        root_0 = None

        char_literal5 = None
        factor4 = None

        factor6 = None


        char_literal5_tree = None

        try:
            try:
                # RegExp.g:17:9: ( factor ( '.' factor )* )
                # RegExp.g:17:11: factor ( '.' factor )*
                pass 
                root_0 = self._adaptor.nil()

                self._state.following.append(self.FOLLOW_factor_in_term62)
                factor4 = self.factor()

                self._state.following.pop()
                self._adaptor.addChild(root_0, factor4.tree)
                # RegExp.g:17:18: ( '.' factor )*
                while True: #loop2
                    alt2 = 2
                    LA2_0 = self.input.LA(1)

                    if (LA2_0 == 12) :
                        alt2 = 1


                    if alt2 == 1:
                        # RegExp.g:17:19: '.' factor
                        pass 
                        char_literal5=self.match(self.input, 12, self.FOLLOW_12_in_term65)

                        char_literal5_tree = self._adaptor.createWithPayload(char_literal5)
                        root_0 = self._adaptor.becomeRoot(char_literal5_tree, root_0)

                        self._state.following.append(self.FOLLOW_factor_in_term68)
                        factor6 = self.factor()

                        self._state.following.pop()
                        self._adaptor.addChild(root_0, factor6.tree)


                    else:
                        break #loop2



                retval.stop = self.input.LT(-1)


                retval.tree = self._adaptor.rulePostProcessing(root_0)
                self._adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop)


            except RecognitionException, re:
                self.reportError(re)
                self.recover(self.input, re)
                retval.tree = self._adaptor.errorNode(self.input, retval.start, self.input.LT(-1), re)
        finally:

            pass
        return retval

    # $ANTLR end "term"

    class factor_return(ParserRuleReturnScope):
        def __init__(self):
            super(RegExpParser.factor_return, self).__init__()

            self.tree = None




    # $ANTLR start "factor"
    # RegExp.g:19:1: factor : atom ( '*' | '+' | '?' )? ;
    def factor(self, ):

        retval = self.factor_return()
        retval.start = self.input.LT(1)

        root_0 = None

        char_literal8 = None
        char_literal9 = None
        char_literal10 = None
        atom7 = None


        char_literal8_tree = None
        char_literal9_tree = None
        char_literal10_tree = None

        try:
            try:
                # RegExp.g:19:9: ( atom ( '*' | '+' | '?' )? )
                # RegExp.g:19:11: atom ( '*' | '+' | '?' )?
                pass 
                root_0 = self._adaptor.nil()

                self._state.following.append(self.FOLLOW_atom_in_factor80)
                atom7 = self.atom()

                self._state.following.pop()
                self._adaptor.addChild(root_0, atom7.tree)
                # RegExp.g:19:16: ( '*' | '+' | '?' )?
                alt3 = 4
                LA3 = self.input.LA(1)
                if LA3 == 13:
                    alt3 = 1
                elif LA3 == 14:
                    alt3 = 2
                elif LA3 == 15:
                    alt3 = 3
                if alt3 == 1:
                    # RegExp.g:19:18: '*'
                    pass 
                    char_literal8=self.match(self.input, 13, self.FOLLOW_13_in_factor84)

                    char_literal8_tree = self._adaptor.createWithPayload(char_literal8)
                    root_0 = self._adaptor.becomeRoot(char_literal8_tree, root_0)



                elif alt3 == 2:
                    # RegExp.g:19:25: '+'
                    pass 
                    char_literal9=self.match(self.input, 14, self.FOLLOW_14_in_factor89)

                    char_literal9_tree = self._adaptor.createWithPayload(char_literal9)
                    root_0 = self._adaptor.becomeRoot(char_literal9_tree, root_0)



                elif alt3 == 3:
                    # RegExp.g:19:32: '?'
                    pass 
                    char_literal10=self.match(self.input, 15, self.FOLLOW_15_in_factor94)

                    char_literal10_tree = self._adaptor.createWithPayload(char_literal10)
                    root_0 = self._adaptor.becomeRoot(char_literal10_tree, root_0)







                retval.stop = self.input.LT(-1)


                retval.tree = self._adaptor.rulePostProcessing(root_0)
                self._adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop)


            except RecognitionException, re:
                self.reportError(re)
                self.recover(self.input, re)
                retval.tree = self._adaptor.errorNode(self.input, retval.start, self.input.LT(-1), re)
        finally:

            pass
        return retval

    # $ANTLR end "factor"

    class atom_return(ParserRuleReturnScope):
        def __init__(self):
            super(RegExpParser.atom_return, self).__init__()

            self.tree = None




    # $ANTLR start "atom"
    # RegExp.g:21:1: atom : ( id | '-' id | '(' exp ')' -> exp );
    def atom(self, ):

        retval = self.atom_return()
        retval.start = self.input.LT(1)

        root_0 = None

        char_literal12 = None
        char_literal14 = None
        char_literal16 = None
        id11 = None

        id13 = None

        exp15 = None


        char_literal12_tree = None
        char_literal14_tree = None
        char_literal16_tree = None
        stream_17 = RewriteRuleTokenStream(self._adaptor, "token 17")
        stream_18 = RewriteRuleTokenStream(self._adaptor, "token 18")
        stream_exp = RewriteRuleSubtreeStream(self._adaptor, "rule exp")
        try:
            try:
                # RegExp.g:21:6: ( id | '-' id | '(' exp ')' -> exp )
                alt4 = 3
                LA4 = self.input.LA(1)
                if LA4 == ID:
                    alt4 = 1
                elif LA4 == 16:
                    alt4 = 2
                elif LA4 == 17:
                    alt4 = 3
                else:
                    nvae = NoViableAltException("", 4, 0, self.input)

                    raise nvae

                if alt4 == 1:
                    # RegExp.g:21:8: id
                    pass 
                    root_0 = self._adaptor.nil()

                    self._state.following.append(self.FOLLOW_id_in_atom109)
                    id11 = self.id()

                    self._state.following.pop()
                    self._adaptor.addChild(root_0, id11.tree)


                elif alt4 == 2:
                    # RegExp.g:22:10: '-' id
                    pass 
                    root_0 = self._adaptor.nil()

                    char_literal12=self.match(self.input, 16, self.FOLLOW_16_in_atom120)

                    char_literal12_tree = self._adaptor.createWithPayload(char_literal12)
                    root_0 = self._adaptor.becomeRoot(char_literal12_tree, root_0)

                    self._state.following.append(self.FOLLOW_id_in_atom123)
                    id13 = self.id()

                    self._state.following.pop()
                    self._adaptor.addChild(root_0, id13.tree)


                elif alt4 == 3:
                    # RegExp.g:23:4: '(' exp ')'
                    pass 
                    char_literal14=self.match(self.input, 17, self.FOLLOW_17_in_atom128) 
                    stream_17.add(char_literal14)
                    self._state.following.append(self.FOLLOW_exp_in_atom130)
                    exp15 = self.exp()

                    self._state.following.pop()
                    stream_exp.add(exp15.tree)
                    char_literal16=self.match(self.input, 18, self.FOLLOW_18_in_atom132) 
                    stream_18.add(char_literal16)

                    # AST Rewrite
                    # elements: exp
                    # token labels: 
                    # rule labels: retval
                    # token list labels: 
                    # rule list labels: 
                    # wildcard labels: 

                    retval.tree = root_0

                    if retval is not None:
                        stream_retval = RewriteRuleSubtreeStream(self._adaptor, "rule retval", retval.tree)
                    else:
                        stream_retval = RewriteRuleSubtreeStream(self._adaptor, "token retval", None)


                    root_0 = self._adaptor.nil()
                    # 23:16: -> exp
                    self._adaptor.addChild(root_0, stream_exp.nextTree())



                    retval.tree = root_0


                retval.stop = self.input.LT(-1)


                retval.tree = self._adaptor.rulePostProcessing(root_0)
                self._adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop)


            except RecognitionException, re:
                self.reportError(re)
                self.recover(self.input, re)
                retval.tree = self._adaptor.errorNode(self.input, retval.start, self.input.LT(-1), re)
        finally:

            pass
        return retval

    # $ANTLR end "atom"

    class id_return(ParserRuleReturnScope):
        def __init__(self):
            super(RegExpParser.id_return, self).__init__()

            self.tree = None




    # $ANTLR start "id"
    # RegExp.g:25:1: id : ID ;
    def id(self, ):

        retval = self.id_return()
        retval.start = self.input.LT(1)

        root_0 = None

        ID17 = None

        ID17_tree = None

        try:
            try:
                # RegExp.g:25:4: ( ID )
                # RegExp.g:25:6: ID
                pass 
                root_0 = self._adaptor.nil()

                ID17=self.match(self.input, ID, self.FOLLOW_ID_in_id145)

                ID17_tree = self._adaptor.createWithPayload(ID17)
                self._adaptor.addChild(root_0, ID17_tree)




                retval.stop = self.input.LT(-1)


                retval.tree = self._adaptor.rulePostProcessing(root_0)
                self._adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop)


            except RecognitionException, re:
                self.reportError(re)
                self.recover(self.input, re)
                retval.tree = self._adaptor.errorNode(self.input, retval.start, self.input.LT(-1), re)
        finally:

            pass
        return retval

    # $ANTLR end "id"


    # Delegated rules


 

    FOLLOW_term_in_exp42 = frozenset([1, 11])
    FOLLOW_11_in_exp45 = frozenset([4, 16, 17])
    FOLLOW_term_in_exp48 = frozenset([1, 11])
    FOLLOW_factor_in_term62 = frozenset([1, 12])
    FOLLOW_12_in_term65 = frozenset([4, 16, 17])
    FOLLOW_factor_in_term68 = frozenset([1, 12])
    FOLLOW_atom_in_factor80 = frozenset([1, 13, 14, 15])
    FOLLOW_13_in_factor84 = frozenset([1])
    FOLLOW_14_in_factor89 = frozenset([1])
    FOLLOW_15_in_factor94 = frozenset([1])
    FOLLOW_id_in_atom109 = frozenset([1])
    FOLLOW_16_in_atom120 = frozenset([4])
    FOLLOW_id_in_atom123 = frozenset([1])
    FOLLOW_17_in_atom128 = frozenset([4, 16, 17])
    FOLLOW_exp_in_atom130 = frozenset([18])
    FOLLOW_18_in_atom132 = frozenset([1])
    FOLLOW_ID_in_id145 = frozenset([1])



def main(argv, stdin=sys.stdin, stdout=sys.stdout, stderr=sys.stderr):
    from antlr3.main import ParserMain
    main = ParserMain("RegExpLexer", RegExpParser)
    main.stdin = stdin
    main.stdout = stdout
    main.stderr = stderr
    main.execute(argv)


if __name__ == '__main__':
    main(sys.argv)
