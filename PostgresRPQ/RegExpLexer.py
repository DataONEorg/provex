# $ANTLR 3.1.3 Mar 18, 2009 10:09:25 RegExp.g 2012-07-07 22:54:46

import sys
from antlr3 import *
from antlr3.compat import set, frozenset


# for convenience in actions
HIDDEN = BaseRecognizer.HIDDEN

# token types
WS=10
T__16=16
T__15=15
T__18=18
T__17=17
T__12=12
IntegerNumber=7
T__11=11
T__14=14
LETTER=5
T__13=13
CHAR=8
DIGIT=6
ID=4
EOF=-1
STRING=9


class RegExpLexer(Lexer):

    grammarFileName = "RegExp.g"
    antlr_version = version_str_to_tuple("3.1.3 Mar 18, 2009 10:09:25")
    antlr_version_str = "3.1.3 Mar 18, 2009 10:09:25"

    def __init__(self, input=None, state=None):
        if state is None:
            state = RecognizerSharedState()
        super(RegExpLexer, self).__init__(input, state)


        self.dfa6 = self.DFA6(
            self, 6,
            eot = self.DFA6_eot,
            eof = self.DFA6_eof,
            min = self.DFA6_min,
            max = self.DFA6_max,
            accept = self.DFA6_accept,
            special = self.DFA6_special,
            transition = self.DFA6_transition
            )






    # $ANTLR start "T__11"
    def mT__11(self, ):

        try:
            _type = T__11
            _channel = DEFAULT_CHANNEL

            # RegExp.g:7:7: ( '|' )
            # RegExp.g:7:9: '|'
            pass 
            self.match(124)



            self._state.type = _type
            self._state.channel = _channel

        finally:

            pass

    # $ANTLR end "T__11"



    # $ANTLR start "T__12"
    def mT__12(self, ):

        try:
            _type = T__12
            _channel = DEFAULT_CHANNEL

            # RegExp.g:8:7: ( '.' )
            # RegExp.g:8:9: '.'
            pass 
            self.match(46)



            self._state.type = _type
            self._state.channel = _channel

        finally:

            pass

    # $ANTLR end "T__12"



    # $ANTLR start "T__13"
    def mT__13(self, ):

        try:
            _type = T__13
            _channel = DEFAULT_CHANNEL

            # RegExp.g:9:7: ( '*' )
            # RegExp.g:9:9: '*'
            pass 
            self.match(42)



            self._state.type = _type
            self._state.channel = _channel

        finally:

            pass

    # $ANTLR end "T__13"



    # $ANTLR start "T__14"
    def mT__14(self, ):

        try:
            _type = T__14
            _channel = DEFAULT_CHANNEL

            # RegExp.g:10:7: ( '+' )
            # RegExp.g:10:9: '+'
            pass 
            self.match(43)



            self._state.type = _type
            self._state.channel = _channel

        finally:

            pass

    # $ANTLR end "T__14"



    # $ANTLR start "T__15"
    def mT__15(self, ):

        try:
            _type = T__15
            _channel = DEFAULT_CHANNEL

            # RegExp.g:11:7: ( '?' )
            # RegExp.g:11:9: '?'
            pass 
            self.match(63)



            self._state.type = _type
            self._state.channel = _channel

        finally:

            pass

    # $ANTLR end "T__15"



    # $ANTLR start "T__16"
    def mT__16(self, ):

        try:
            _type = T__16
            _channel = DEFAULT_CHANNEL

            # RegExp.g:12:7: ( '-' )
            # RegExp.g:12:9: '-'
            pass 
            self.match(45)



            self._state.type = _type
            self._state.channel = _channel

        finally:

            pass

    # $ANTLR end "T__16"



    # $ANTLR start "T__17"
    def mT__17(self, ):

        try:
            _type = T__17
            _channel = DEFAULT_CHANNEL

            # RegExp.g:13:7: ( '(' )
            # RegExp.g:13:9: '('
            pass 
            self.match(40)



            self._state.type = _type
            self._state.channel = _channel

        finally:

            pass

    # $ANTLR end "T__17"



    # $ANTLR start "T__18"
    def mT__18(self, ):

        try:
            _type = T__18
            _channel = DEFAULT_CHANNEL

            # RegExp.g:14:7: ( ')' )
            # RegExp.g:14:9: ')'
            pass 
            self.match(41)



            self._state.type = _type
            self._state.channel = _channel

        finally:

            pass

    # $ANTLR end "T__18"



    # $ANTLR start "LETTER"
    def mLETTER(self, ):

        try:
            # RegExp.g:31:9: ( 'a' .. 'z' | 'A' .. 'Z' )
            # RegExp.g:
            pass 
            if (65 <= self.input.LA(1) <= 90) or (97 <= self.input.LA(1) <= 122):
                self.input.consume()
            else:
                mse = MismatchedSetException(None, self.input)
                self.recover(mse)
                raise mse





        finally:

            pass

    # $ANTLR end "LETTER"



    # $ANTLR start "DIGIT"
    def mDIGIT(self, ):

        try:
            # RegExp.g:34:8: ( '0' .. '9' )
            # RegExp.g:34:25: '0' .. '9'
            pass 
            self.matchRange(48, 57)




        finally:

            pass

    # $ANTLR end "DIGIT"



    # $ANTLR start "IntegerNumber"
    def mIntegerNumber(self, ):

        try:
            # RegExp.g:38:5: ( '0' | '1' .. '9' ( DIGIT )* )
            alt2 = 2
            LA2_0 = self.input.LA(1)

            if (LA2_0 == 48) :
                alt2 = 1
            elif ((49 <= LA2_0 <= 57)) :
                alt2 = 2
            else:
                nvae = NoViableAltException("", 2, 0, self.input)

                raise nvae

            if alt2 == 1:
                # RegExp.g:38:9: '0'
                pass 
                self.match(48)


            elif alt2 == 2:
                # RegExp.g:39:9: '1' .. '9' ( DIGIT )*
                pass 
                self.matchRange(49, 57)
                # RegExp.g:39:18: ( DIGIT )*
                while True: #loop1
                    alt1 = 2
                    LA1_0 = self.input.LA(1)

                    if ((48 <= LA1_0 <= 57)) :
                        alt1 = 1


                    if alt1 == 1:
                        # RegExp.g:39:19: DIGIT
                        pass 
                        self.mDIGIT()


                    else:
                        break #loop1



        finally:

            pass

    # $ANTLR end "IntegerNumber"



    # $ANTLR start "ID"
    def mID(self, ):

        try:
            _type = ID
            _channel = DEFAULT_CHANNEL

            # RegExp.g:42:5: ( ( LETTER | DIGIT | '_' )* )
            # RegExp.g:42:9: ( LETTER | DIGIT | '_' )*
            pass 
            # RegExp.g:42:9: ( LETTER | DIGIT | '_' )*
            while True: #loop3
                alt3 = 2
                LA3_0 = self.input.LA(1)

                if ((48 <= LA3_0 <= 57) or (65 <= LA3_0 <= 90) or LA3_0 == 95 or (97 <= LA3_0 <= 122)) :
                    alt3 = 1


                if alt3 == 1:
                    # RegExp.g:
                    pass 
                    if (48 <= self.input.LA(1) <= 57) or (65 <= self.input.LA(1) <= 90) or self.input.LA(1) == 95 or (97 <= self.input.LA(1) <= 122):
                        self.input.consume()
                    else:
                        mse = MismatchedSetException(None, self.input)
                        self.recover(mse)
                        raise mse



                else:
                    break #loop3



            self._state.type = _type
            self._state.channel = _channel

        finally:

            pass

    # $ANTLR end "ID"



    # $ANTLR start "CHAR"
    def mCHAR(self, ):

        try:
            _type = CHAR
            _channel = DEFAULT_CHANNEL

            # RegExp.g:44:5: ( '\\'' . '\\'' )
            # RegExp.g:44:7: '\\'' . '\\''
            pass 
            self.match(39)
            self.matchAny()
            self.match(39)



            self._state.type = _type
            self._state.channel = _channel

        finally:

            pass

    # $ANTLR end "CHAR"



    # $ANTLR start "STRING"
    def mSTRING(self, ):

        try:
            _type = STRING
            _channel = DEFAULT_CHANNEL

            # RegExp.g:46:7: ( '\\\"' ( . )* '\\\"' )
            # RegExp.g:46:9: '\\\"' ( . )* '\\\"'
            pass 
            self.match(34)
            # RegExp.g:46:14: ( . )*
            while True: #loop4
                alt4 = 2
                LA4_0 = self.input.LA(1)

                if (LA4_0 == 34) :
                    alt4 = 2
                elif ((0 <= LA4_0 <= 33) or (35 <= LA4_0 <= 65535)) :
                    alt4 = 1


                if alt4 == 1:
                    # RegExp.g:46:14: .
                    pass 
                    self.matchAny()


                else:
                    break #loop4
            self.match(34)



            self._state.type = _type
            self._state.channel = _channel

        finally:

            pass

    # $ANTLR end "STRING"



    # $ANTLR start "WS"
    def mWS(self, ):

        try:
            _type = WS
            _channel = DEFAULT_CHANNEL

            # RegExp.g:48:5: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            # RegExp.g:48:9: ( ' ' | '\\t' | '\\r' | '\\n' )+
            pass 
            # RegExp.g:48:9: ( ' ' | '\\t' | '\\r' | '\\n' )+
            cnt5 = 0
            while True: #loop5
                alt5 = 2
                LA5_0 = self.input.LA(1)

                if ((9 <= LA5_0 <= 10) or LA5_0 == 13 or LA5_0 == 32) :
                    alt5 = 1


                if alt5 == 1:
                    # RegExp.g:
                    pass 
                    if (9 <= self.input.LA(1) <= 10) or self.input.LA(1) == 13 or self.input.LA(1) == 32:
                        self.input.consume()
                    else:
                        mse = MismatchedSetException(None, self.input)
                        self.recover(mse)
                        raise mse



                else:
                    if cnt5 >= 1:
                        break #loop5

                    eee = EarlyExitException(5, self.input)
                    raise eee

                cnt5 += 1
            #action start
            _channel = HIDDEN; 
            #action end



            self._state.type = _type
            self._state.channel = _channel

        finally:

            pass

    # $ANTLR end "WS"



    def mTokens(self):
        # RegExp.g:1:8: ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | ID | CHAR | STRING | WS )
        alt6 = 12
        alt6 = self.dfa6.predict(self.input)
        if alt6 == 1:
            # RegExp.g:1:10: T__11
            pass 
            self.mT__11()


        elif alt6 == 2:
            # RegExp.g:1:16: T__12
            pass 
            self.mT__12()


        elif alt6 == 3:
            # RegExp.g:1:22: T__13
            pass 
            self.mT__13()


        elif alt6 == 4:
            # RegExp.g:1:28: T__14
            pass 
            self.mT__14()


        elif alt6 == 5:
            # RegExp.g:1:34: T__15
            pass 
            self.mT__15()


        elif alt6 == 6:
            # RegExp.g:1:40: T__16
            pass 
            self.mT__16()


        elif alt6 == 7:
            # RegExp.g:1:46: T__17
            pass 
            self.mT__17()


        elif alt6 == 8:
            # RegExp.g:1:52: T__18
            pass 
            self.mT__18()


        elif alt6 == 9:
            # RegExp.g:1:58: ID
            pass 
            self.mID()


        elif alt6 == 10:
            # RegExp.g:1:61: CHAR
            pass 
            self.mCHAR()


        elif alt6 == 11:
            # RegExp.g:1:66: STRING
            pass 
            self.mSTRING()


        elif alt6 == 12:
            # RegExp.g:1:73: WS
            pass 
            self.mWS()







    # lookup tables for DFA #6

    DFA6_eot = DFA.unpack(
        u"\1\11\14\uffff"
        )

    DFA6_eof = DFA.unpack(
        u"\15\uffff"
        )

    DFA6_min = DFA.unpack(
        u"\1\11\14\uffff"
        )

    DFA6_max = DFA.unpack(
        u"\1\174\14\uffff"
        )

    DFA6_accept = DFA.unpack(
        u"\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14"
        )

    DFA6_special = DFA.unpack(
        u"\15\uffff"
        )

            
    DFA6_transition = [
        DFA.unpack(u"\2\14\2\uffff\1\14\22\uffff\1\14\1\uffff\1\13\4\uffff"
        u"\1\12\1\7\1\10\1\3\1\4\1\uffff\1\6\1\2\20\uffff\1\5\74\uffff\1"
        u"\1"),
        DFA.unpack(u""),
        DFA.unpack(u""),
        DFA.unpack(u""),
        DFA.unpack(u""),
        DFA.unpack(u""),
        DFA.unpack(u""),
        DFA.unpack(u""),
        DFA.unpack(u""),
        DFA.unpack(u""),
        DFA.unpack(u""),
        DFA.unpack(u""),
        DFA.unpack(u"")
    ]

    # class definition for DFA #6

    class DFA6(DFA):
        pass


 



def main(argv, stdin=sys.stdin, stdout=sys.stdout, stderr=sys.stderr):
    from antlr3.main import LexerMain
    main = LexerMain(RegExpLexer)
    main.stdin = stdin
    main.stdout = stdout
    main.stderr = stderr
    main.execute(argv)


if __name__ == '__main__':
    main(sys.argv)
