# Graduate Programming Languages Summer 2016

Creating a parser and lexer using flex and bison to read Scheme and XML files.

### Commands:
```
bison -d xml.y
flex xml.l
g++ xml.tab.c lex.yy.c -ll
./a.out < desiredfile.txt
```
