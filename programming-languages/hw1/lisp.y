%{
#include <cstdio>
#include <iostream>
#include <algorithm>
#include <string>

using namespace std;

// stuff from flex that bison needs to know about:
extern "C" int yylex();
extern "C" int yyparse();
extern "C" FILE *yyin;

void yyerror(const char *s);
%}

%union {
	int val;
	/* You may include additional fields as you want. */
	/* char op; */
};


%start prog

%token LPAREN RPAREN
%token PLUS MINUS MUL DIV
%token <val> NUM    /* 'val' is the (only) field declared in %union
                       which represents the type of the token. */
%type <val> list
%type <val> expr
%type <val> multiplication_fold
%type <val> plus_fold
%type <val> term

/* Resolve the ambiguity of the grammar by defining precedence. */

/* Order of directives will determine the precedence. */
%left PLUS MINUS    /* left means left-associativity. */
%left DIV MUL

%%
prog: list { cout << $1 << endl; }
    ;

list: LPAREN expr RPAREN {$$ = $2;}

expr: PLUS plus_fold {$$ = $2;} /*unbounded, arbitrary number of operands*/
          | MUL multiplication_fold {$$ = $2;} /*unbounded, arbitrary number of operands*/
          | MINUS term term {$$ = $2 - $3;}
          | DIV term term {$$ = $2/$3;}
          ;

term:
		NUM
    | list
    ;

plus_fold: plus_fold term { $$ = $1 + $2; }
              | term
              ;

multiplication_fold: multiplication_fold term { $$ = $1 * $2; }
                | term
                ;
%%

int main()
{
    yyparse();
    return 0;
}

void yyerror(const char *s) {
	cout << "No, this is invalid. Invalid, parsing mismatch. " << s << endl;
	// might as well halt now:
	exit(-1);
}
