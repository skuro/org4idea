package tk.skuro.idea.orgmode.parser;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

%%

%class _OrgLexer
%implements FlexLexer
%unicode

%function advance
%eof{  return;
%eof}
%type IElementType

WHITE_SPACE_CHAR=[\ \n\r\t\f]
COMMENT="#"[^\r\n]*
UNDERLINE="_" .* "_"
OUTLINE=[*]+ [\ \t\f]+ [^\r\n]*

%%

<YYINITIAL> ^{COMMENT}        { yybegin(YYINITIAL); return OrgTokenTypes.COMMENT; }
<YYINITIAL> ^{OUTLINE}        { yybegin(YYINITIAL); return OrgTokenTypes.OUTLINE; }

{WHITE_SPACE_CHAR}+                      { return OrgTokenTypes.WHITE_SPACE; }
{UNDERLINE}                              { return OrgTokenTypes.UNDERLINE; }
.                                        { return OrgTokenTypes.BAD_CHARACTER; }