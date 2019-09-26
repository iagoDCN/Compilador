parser grammar DecafParser;

@header {
package decaf;
}

options
{
  language=Java;
  tokenVocab=DecafLexer;
}

program: T_CLASS ID LCURLY field_decl* method_decl* RCURLY EOF;

field_decl: (type ID | type ID CLEFT NUM CRIGHT) (VIRGULA type? ID | VIRGULA type? ID CLEFT NUM CRIGHT)* PONTVIRG;

method_decl: (type | T_VOID) ID PLEFT PRIGHT block;

block: LCURLY var_decl* statement* RCURLY;

var_decl: type ID (VIRGULA ID)* PONTVIRG;

type: T_INT | T_BOOLEAN;

statement: location assing_op expr PONTVIRG
		| method_call PONTVIRG
		| T_IF PLEFT expr PRIGHT block (T_ELSE block)? PONTVIRG
		| T_FOR ID ATRIB expr VIRGULA expr block PONTVIRG
		| T_RETURN expr? PONTVIRG
		| T_BREAK PONTVIRG
		| T_CONTINUE PONTVIRG
		| block PONTVIRG
	 	;

assing_op: ATRIB | MAIS_IGUAL | MENOS_IGUAL;

method_call: method_name PLEFT expr+ PRIGHT | T_CALLOUT PLEFT STRING (VIRGULA callout_arg) PRIGHT;

method_name: ID;

location: ID | ID CLEFT expr CRIGHT;

expr: location
	| method_call
	| NUM
	| expr bin_op expr
	| SUBTRAC expr
	| EXCL expr
	| PLEFT expr PRIGHT
	;

callout_arg: expr | STRING;

bin_op: arith_op | rel_op | eq_op | cond_op;

arith_op: ADIC | SUBTRAC | MULT | DIV | MOD;

rel_op: MENORQ | MAIORQ | MENORIGUAL | MAIORIGUAL;

eq_op: IG | DIFERENTE;

cond_op: E | OU;








