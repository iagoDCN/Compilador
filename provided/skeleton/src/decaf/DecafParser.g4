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

field_decl: (type_id | type_id CLEFT int_literal CRIGHT) (VIRGULA ID)* PONTVIRG;

method_decl: (type | T_VOID) ID PLEFT (type_id(VIRGULA type_id)*)? PRIGHT block;

block: LCURLY var_decl* statement* RCURLY;

var_decl: type (ID(VIRGULA ID)*)* PONTVIRG;

type_id: type ID ;

type: T_INT | T_BOOLEAN;

statement: location assing_op expr PONTVIRG
		| method_call PONTVIRG
		| T_IF PLEFT expr PRIGHT block (T_ELSE block)? 
		| T_FOR ID ATRIB expr VIRGULA expr block 
		| T_RETURN expr? PONTVIRG
		| T_BREAK PONTVIRG
		| T_CONTINUE PONTVIRG
		| block
	 	;

assing_op: ATRIB | MAIS_IGUAL | MENOS_IGUAL;

method_call: method_name PLEFT(expr (VIRGULA expr)*)? PRIGHT | T_CALLOUT PLEFT STRING (VIRGULA callout_arg(VIRGULA callout_arg)*)? PRIGHT;

method_name: ID;

location: ID | ID CLEFT expr CRIGHT;

expr: location
	| method_call
	| literal
	| expr bin_op expr
	| SUBTRAC expr
	| EXCL expr
	| PLEFT expr PRIGHT
	;

callout_arg: expr | string_literal;

bin_op: arith_op | rel_op | eq_op | cond_op;

arith_op: ADIC | SUBTRAC | MULT | DIV | MOD;

rel_op: MENORQ | MAIORQ | MENORIGUAL | MAIORIGUAL;

eq_op: IG | DIFERENTE;

cond_op: E | OU;

literal: int_literal | char_literal | bool_literal;

alpha_num: alpha | digit;

alpha: LET;

digit: NUM;

hex_digit: digit | LET+;

int_literal: decimal_literal | hex_literal;

decimal_literal: NUM;

hex_literal: HEX;

bool_literal: BOOLEANLITERAL;

char_literal: CHAR;

string_literal: STRING;







