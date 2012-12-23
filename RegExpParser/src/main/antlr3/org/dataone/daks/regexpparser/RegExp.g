/*
   This grammar is used to generate a lexer and a parser to process regular expressions.
*/

grammar RegExp;

//The output is an abstract syntax tree
options {
	output=AST;
	ASTLabelType=CommonTree;
}

// applies only to the parser:
@header {package org.dataone.daks.regexpparser;}

// applies only to the lexer:
@lexer::header {package org.dataone.daks.regexpparser;}



exp     :	term ('|'^ term)* ;

term    :	factor ('.'^ factor)* ;

factor  :	atom ( '*'^ | '+'^ | '?'^ ) ?  ;

atom	:	id
	|       '-'^ id
	|	'(' exp ')' -> exp ;

id :	ID ;
	
	
// L e x i c a l  R u l e s

fragment
LETTER 	:	               'a'..'z' |'A'..'Z' ;

fragment
DIGIT 	:	               '0'..'9' ;

fragment
IntegerNumber
    :   '0' 
    |   '1'..'9' (DIGIT)*            
    ;

ID  :   (LETTER | DIGIT | '_')*  ;

CHAR:	'\'' . '\'' ;

STRING:	'\"' .* '\"' ;

WS  :   ( ' ' | '\t' | '\r' | '\n' )+ { $channel = HIDDEN; } ;   










