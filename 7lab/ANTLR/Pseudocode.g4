grammar Pseudocode;

// Parser Rules
program: statement* EOF;

statement:
      move
    | checkObstacle
    | ifStatement
    | forLoop
    | whileLoop
    | print
    | setVariable
    | delay
;

move: ('move' | 'go') ('up' | 'down' | 'left' | 'right');
checkObstacle: 'check' 'obstacle' ('up' | 'down' | 'left' | 'right');
ifStatement: 'if' condition 'then' statement* 'end';
forLoop: 'for' VARIABLE 'in' INT 'to' INT 'do' statement* 'end';
whileLoop: 'while' condition 'do' statement* 'end';
print: 'print' VARIABLE;
setVariable: VARIABLE '=' expr;
delay: 'delay' INT;

condition: expr comparison_operator expr;

expr: INT                   // Matches an integer
    | VARIABLE              // Matches a variable
    | expr ('+'|'-') expr   // Matches an addition or subtraction expression
;

comparison_operator: '<' | '<=' | '>' | '>=' | '==' | '!=';

// Lexer Rules
VARIABLE: [a-zA-Z]+;
INT: [0-9]+;
WS: [ \t\r\n]+ -> skip;
