grammar Expr;
options
{
  // antlr will generate java lexer and parser
  language = Java;

}
WS      : [ \t\r\n]+ -> skip ;
OP      : '&&' | '||';
COMP    : '==' | '<' | '>' | '<=' | '>=' | '!=';
fragment INT     : [0-9]+;
REAL    : INT '.' INT | INT;

ACTION  : 'P' INT ':' INT;
MEASURE : 'M' INT;

// ***************** parser rules:
compared   : REAL | MEASURE;
cond       : compared COMP compared;
condexpr   : '(' condexpr ')' | cond OP condexpr | cond;
actionexpr : ACTION actionexpr | ACTION;
expression : 'ON' condexpr 'DO' actionexpr;
