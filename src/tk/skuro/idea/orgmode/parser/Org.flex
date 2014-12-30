package tk.skuro.idea.orgmode.parser;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

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
WHITE_SPACE_CHAR=[\ \n\r\t\f]
COMMENT="#"[^\r\n]*
// TODO: "Affiliated Keywords" #+/ (plus org comment need a space)
// maybe block
UNDERLINE="_" .* "_"
// see markdown, and replicate for bold verbatim and else
OUTLINE=[*]+ [\ \t\f]+ [^\r\n]*
// see how to count the number of star

%% /** Lexing Rules */

<YYINITIAL> ^{COMMENT}        { yybegin(YYINITIAL); return OrgTokenTypes.COMMENT; }
// TODO comment can start with heading whitechar
<YYINITIAL> ^{OUTLINE}        { yybegin(YYINITIAL); return OrgTokenTypes.OUTLINE; }

{WHITE_SPACE_CHAR}+                      { return OrgTokenTypes.WHITE_SPACE; }
{UNDERLINE}                              { return OrgTokenTypes.UNDERLINE; }
.                                        { return OrgTokenTypes.BAD_CHARACTER; }