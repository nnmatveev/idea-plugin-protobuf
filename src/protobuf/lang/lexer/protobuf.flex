package protobuf.lang.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import protobuf.lang.ProtobufTokenTypes;

%%

%class _ProtobufLexer
%implements FlexLexer, ProtobufTokenTypes
%unicode
%public

%type IElementType

%function advance

%eof{ return;
%eof}


EOL = \r|\n|\r\n
WHITE_SPACE = " " | \t | \f | {EOL}
INPUT_CHARACTER = [^\r\n]

DIGIT = [0-9]
HEX_DIGIT = [A-Fa-f]|{DIGIT}
LETTER = [:letter:]|"_"

IDENTIFIER = ({LETTER})({LETTER}|{DIGIT})*

LINE_COMMENT = "/""/"{INPUT_CHARACTER}*
C_STYLE_COMMENT="/*"((.|{EOL})*?)"*/"

HEX_INT32="-"?"0""x"{HEX_DIGIT}{8}
HEX_INT64="-"?"0""x"{HEX_DIGIT}{16}

NUM_INT = "-"?{DIGIT}{DIGIT}*
NUM_DOUBLE = ("-"?(({DIGIT}+(("."{DIGIT}+)|((e|E)"-"?{DIGIT}+)))|("inf"))|("nan"))

STRING_DOUBLE_QUOTED = \"(\\\"|[^\"\r\n])*(\")
STRING_SINGLE_QUOTED = \'(\\\'|[^\'\r\n])*(\')

WRONG_STRING_DOUBLE_QUOTED = \"[^\"]*(\r\n)
WRONG_STRING_SINGLE_QUOTED = \'[^\']*(\r\n)


%%

{LINE_COMMENT}                      {return (LINE_COMMENT);}
{C_STYLE_COMMENT}                   {return (C_STYLE_COMMENT);}

{STRING_DOUBLE_QUOTED}              {return (STRING_LITERAL);}
{STRING_SINGLE_QUOTED}              {return (STRING_LITERAL);}

{WRONG_STRING_DOUBLE_QUOTED}        {return (WRONG_STRING_LITERAL);}
{WRONG_STRING_SINGLE_QUOTED}        {return (WRONG_STRING_LITERAL);}

{WHITE_SPACE}                       {return (WHITE_SPACE);}

{HEX_INT64}                         {return (NUM_INT);}
{HEX_INT32}                         {return (NUM_INT);}
{NUM_INT}                           {return (NUM_INT);}
{NUM_DOUBLE}                        {return (NUM_DOUBLE);}



"("                                 {return (OPEN_PARANT);}
")"                                 {return (CLOSE_PARANT);}
"["                                 {return (OPEN_BRACE);}
"]"                                 {return (CLOSE_BRACE);}
"{"                                 {return (OPEN_BLOCK);}
"}"                                 {return (CLOSE_BLOCK);}

"."                                 {return (DOT);}
"="                                 {return (EQUAL);}
";"                                 {return (SEMICOLON);}
","                                 {return (COMMA);}

"import"                            {return (IMPORT);}
"package"                           {return (PACKAGE);}

"message"                           {return (MESSAGE);}
"extend"                            {return (EXTEND);}
"service"                           {return (SERVICE);}
"rpc"                               {return (RPC);}
"enum"                              {return (ENUM);}
"returns"                           {return (RETURNS);}
"option"                            {return (OPTION);}
"group"                             {return (GROUP);}
"extensions"                        {return (EXTENSIONS);}

"true"                              {return (TRUE);}
"false"                             {return (FALSE);}

"required"                          {return (REQUIRED);}
"optional"                          {return (OPTIONAL);}
"repeated"                          {return (REPEATED);}

"to"                                {return (TO);}
"max"                               {return (MAX);}

"double"                            {return (DOUBLE);}
"float"                             {return (FLOAT);}
"int32"                             {return (INT32);}
"int64"                             {return (INT64);}
"uint32"                            {return (UINT32);}
"uint64"                            {return (UINT64);}
"sint32"                            {return (SINT32);}
"sint64"                            {return (SINT64);}
"fixed32"                           {return (FIXED32);}
"fixed64"                           {return (FIXED64);}
"sfixed32"                          {return (SFIXED32);}
"sfixed64"                          {return (SFIXED64);}
"bool"                              {return (BOOL);}
"string"                            {return (STRING);}
"bytes"                             {return (BYTES);}


{IDENTIFIER}                        {return (IDENTIFIER);}


.                                   {return BAD_CHARACTER;}


