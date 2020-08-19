grammar CustomSqlParser;      // 定义一个名为Hello的语法，名字与文件名一致
import CommonLexerRules;

// #是别名
customcal
: helloWordStatement EOF   #helloWord
| checkpointStatement EOF #checkpoint
;

// helloWordStatement
helloWordStatement
  : PRT word=STRING      // 定义一个r规则，匹配一个关键字Hello和紧随其后的标识符ID
  ;     // "|"是备选分支的分隔符

// chckpoint 表数据到某hdfs路径。将spark里面的 view表存到hdfs
checkpointStatement
:CHECKPOINT table=tableIdentifier INTO location=STRING
;


tableIdentifier
    : (db=IDENTIFIER '.')? table=IDENTIFIER
    ;






