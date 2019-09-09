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

LCURLY : '{';
RCURLY : '}';

IF : 'if';

ID  :
  (LETRAS|ESPC)+(LETRAS|INT|ESPC)*;

WS_ : (' ' | '\n' ) -> skip;

SL_COMMENT : '//' (~'\n')* '\n' -> skip;

CHAR : '\'' (ESC|LETRAS|INT) '\'';
STRING : '"' (ESC|'"'|LETRAS|INT)* '"';

HEX : '0x'('a'..'f'|'A'..'F'|INT)+;


fragment
ESC :  '\\' ('n'|'"'|'t'|'\\');

fragment
INT : [0-9]+;

fragment
ESPC : (';'|'-'|'_'|'   ');

fragment
LETRAS : ('a'..'z'|'A'..'Z');
