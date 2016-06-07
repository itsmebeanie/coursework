/*
Kaitlin Gu
Summer 2016, Cory Pluck
kg1292@nyu.edu

Attempted to create separate definitions for DATA and ATTRIBUTE but ended up
putting them into one definition because of continual errors. Goal was to
ensure that = " went before content and a " followed.

*/

%{
#include <iostream>
using namespace std;
#define YY_DECL extern "C" int yylex()
#include "xml.tab.h" /* We want the tokens defined in xml.y. */
%}

%option noyywrap
%%

[ \t\r\n]+      { /* Ignore white characters. */ }
"<"[a-zA-Z0-9]+ {yylval.sval = strdup(yytext); return OPENING_TAG;}
"</"[a-zA-Z0-9]+  { yylval.sval = strdup(yytext); return CLOSING_TAG;}
">"        {  return MATCHING_END_TAG;}
[ \t\r\n/.=:"0-9a-zA-Z]+    {yylval.sval = strdup(yytext); return CONTENT; }
. {cout << "No, this is invalid. Invalid token\n"; exit(1);}
%%
