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

PLEFT : '(';
PRIGHT : ')';

IF: 'if';
MENOS : '-';


ID  :
  (LETRAS|'_')(LETRAS|INT|'_')*;

WS_ : (' ' | '\n' ) -> skip;


SL_COMMENT : '//' (~'\n')* '\n' -> skip;

CHAR : '\'' (ESC|LETRAS|INT) '\'';
STRING : '"' (ESC|'"'|LETRAS|INT)* '"';

HEX : '0x'('a'..'f'|'A'..'F'|INT)+;

NUM : INT(INT)*;

fragment
ESC :  '\\' ('n'|'"'|'t'|'\\');

fragment
INT : ('0'..'9')+;

fragment
ESPC : (';'|'_'|'   ');

fragment
LETRAS : ('a'..'z'|'A'..'Z');
