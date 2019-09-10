lexer grammar DecafLexer;

@header {
package decaf;
}

options
{
  language=Java;
}

tokens
{
  TK_class
}

IF: 'if';

LCURLY : '{';
RCURLY : '}';
CLEFT : '[';
CRIGHT : ']';
PLEFT : '(';
PRIGHT : ')';

VEZES : '*';
MENOS : '-';
MAIS  : '+';
E    : '&&';

DIFERENTE : '!=';
MENORQ    : '<';
MENORIGUAL : '<=';


ID  :
  (LETRAS|ESPC)(LETRAS|INT|ESPC)*;

WS_ : [ \t\r\n]+ -> skip;

SL_COMMENT : '//' (~'\n')* '\n' -> skip;

CHAR : '\'' (ESC|LETRAS|INT) '\'';
STRING : '\"' (WS_|ESC|LETRAS|INT|SIM)*'\"';

HEX : '0x'('a'..'f'|'A'..'F'|INT)+;

NUM : INT(INT)*;

fragment
ESC :  '\\' ('n'|'"'|'t'|'\\');

fragment 
SIM : ( '\\\"'|'.'|','|'?'|'\\\''|':');

fragment
INT : ('0'..'9')+;

fragment
ESPC : ('_');

fragment
LETRAS : ('a'..'z'|'A'..'Z');
