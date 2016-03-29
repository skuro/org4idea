package tk.skuro.idea.orgmode.parser;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static tk.skuro.idea.orgmode.parser.OrgTokenTypes.*;

// TODO: see if could reuse org lexer src/org_lexer.l

%%

%class _OrgLexer
%implements FlexLexer
%unicode

%function advance
%eof{  return;
%eof}
%type IElementType

%state BLOCK
%state PROPERTIES

/** Classes definitions */
WHITE_SPACE_CHAR = [\ \t\f\n\r]
COMMENT =[\ \t]*"# "[^\r\n]*

KEYWORD=[\ \t]*"#+"[^\r\n]+

UNDERLINE = "_" .* "_"
BOLD = "*" .* "*"
// see markdown, and replicate for bold verbatim and else
OUTLINE = [*]+ [\ \t\f]+ [^\r\n]*
// see how to count the number of star: maybe yytext()

CODELINE = [\ \t]*": "[^\r\n]*

BLOCK_START= [\ \t]*"#+BEGIN_"[^\r\n\ ]+[^\r\n]*
BLOCK_END= [\ \t]*"#+END_"[^\r\n\ ]+[^\r\n]*

PROPERTIES_START=[\ \t]*":PROPERTIES:"
PROPERTIES_END=[\ \t]*":END:"

%% /** Lexing Rules */

<YYINITIAL> {
    ^{OUTLINE}        { yybegin(YYINITIAL); return OUTLINE; }
    ^{COMMENT}        { yybegin(YYINITIAL); return COMMENT; }
    ^{BLOCK_START}    { yybegin(BLOCK); return BLOCK_DELIMITER; }
    ^{KEYWORD}        { yybegin(YYINITIAL); return KEYWORD; }
    ^{CODELINE}       { yybegin(YYINITIAL); return CODE; }
    ^{WHITE_SPACE_CHAR}*{PROPERTIES_START} { yybegin(PROPERTIES); return DRAWER_DELIMITER; }
}

<PROPERTIES> {
    {PROPERTIES_END} { yybegin(YYINITIAL); return DRAWER_DELIMITER; }
    .                 { return DRAWER_CONTENT; }
}

<BLOCK> {
    ^{BLOCK_END}      { yybegin(YYINITIAL); return BLOCK_DELIMITER; }
    .                 { return BLOCK_CONTENT; }
}

{WHITE_SPACE_CHAR}+   { return WHITE_SPACE; }
{UNDERLINE}           { return UNDERLINE; } // Maybe move in initial block?
{BOLD}                { return BOLD; } // Maybe move in initial block?
.                     { return TEXT; }