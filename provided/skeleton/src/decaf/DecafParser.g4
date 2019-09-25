parser grammar DecafParser;

@header {
package decaf;
}

options
{
  language=Java;
  tokenVocab=DecafLexer;
}

program: T_CLASS ID LCURLY field_decl method_decl RCURLY EOF;

field_decl: ;

method_decl: (type | T_VOID) ID PLEFT PRIGHT block;

block: LCURLY var_decl* statement* RCURLY;

var_decl: type ID (VIRGULA type? ID)* PONTVIRG;

type: T_INT | T_BOOLEAN;
 	

