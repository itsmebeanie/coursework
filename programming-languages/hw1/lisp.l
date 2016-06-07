/*
Kaitlin Gu
Summer 2016, Cory Pluck
kg1292@nyu.edu

Credit: Definitions taken from Professor Pluck's calculator example code.

*/

%{
#include <iostream>
using namespace std;
#define YY_DECL extern "C" int yylex()
#include "lisp.tab.h"
%}

%option noyywrap

%%
[ \t\r\n]+              { /* Ignore white characters. */ }
[0-9]+                  { yylval.val = atoi(yytext); return NUM; }
"+"                     { return PLUS; }
"-"                     { return MINUS; }
"*"                     { return MUL; }
"/"                     { return DIV; }
"("                     { return LPAREN; }
")"                     { return RPAREN; }
. {cout << "No, this is invalid. Invalid token\n"; exit(1);}

%%
