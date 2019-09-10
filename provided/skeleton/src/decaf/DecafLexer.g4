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

T_IF	: 'if'
	;

T_ELSE	: 'else'
	;

T_CLASS	: 'class'
	;

T_INT	: 'int'
	;

T_VOID	: 'void'
	;

T_FOR	: 'for'
	;

T_BREAK	: 'break'
	;

T_CALLOUT	: 'callout'
		;

T_CONTINUE	: 'continue'
		;

T_BOOLEAN	: 'boolean'
		;

T_RETURN	: 'return'
		;


BOOLEANLITERAL	: 'true' | 'false'
		;

LCURLY	: '{'
	;

RCURLY	: '}'
	;

CLEFT	: '['
	;

CRIGHT	: ']'
	;

PLEFT	: '('
	;

PRIGHT	: ')'
	;


VEZES	: '*'
	;

MENOS	: '-'
	;

MAIS	: '+'
	;
	
E	: '&&'
	;


MENORQ	: '<'
	;

MENORIGUAL	: '<='
		;

DIFERENTE	: '!='
		;



ID	: (LETRAS|ESPC)(LETRAS|INT|ESPC)*
	;

WS_	: [ \t\r\n]+ -> skip
	;

CHAR	: '\'' (ESC|LETRAS|INT) '\''
	;

STRING	: '\"' (WS_|ESC|LETRAS|INT|SIM)*'\"'
	;

HEX	: '0x'('a'..'f'|'A'..'F'|INT)+
	;

NUM	: INT(INT)*
	;

SL_COMMENT	: '//' (~'\n')* '\n' -> skip
		;


fragment
ESC	:  '\\' ('n'|'"'|'t'|'\\')
	;

fragment 
SIM	: ( '\\\"'|'.'|','|'?'|'\\\''|':')
	;

fragment
INT	: ('0'..'9')+
	;

fragment
ESPC	: ('_')
	;

fragment
LETRAS	: ('a'..'z'|'A'..'Z')
	;
