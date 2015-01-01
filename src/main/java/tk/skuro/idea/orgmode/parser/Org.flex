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

/** Classes definitions */
WHITE_SPACE_CHAR = [\ \t\f\n\r]
COMMENT =[\ \t]*"# "[^\r\n]*
// FIXME Leading whitespace not working...
KEYWORD=[\ \t]*"#+"[^\r\n]+

UNDERLINE = "_" .* "_"
// see markdown, and replicate for bold verbatim and else
OUTLINE = [*]+ [\ \t\f]+ [^\r\n]*
// see how to count the number of star: maybe yytext()

CODELINE = [\ \t]*": "[^\r\n]*

%% /** Lexing Rules */

<YYINITIAL> {
    ^{OUTLINE}        { yybegin(YYINITIAL); return OUTLINE; }
    ^{COMMENT}        { yybegin(YYINITIAL); return COMMENT; }
    ^{KEYWORD}        { yybegin(YYINITIAL); return KEYWORD; }
    ^{CODELINE}       { yybegin(YYINITIAL); return CODE; }
}

{WHITE_SPACE_CHAR}+   { return WHITE_SPACE; }
{UNDERLINE}           { return UNDERLINE; } // Mayeb move in initial block?
.                     { return BAD_CHARACTER; }