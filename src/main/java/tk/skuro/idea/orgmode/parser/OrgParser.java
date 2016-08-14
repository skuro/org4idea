// This is a generated file. Not intended for manual editing.
package tk.skuro.idea.orgmode.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static tk.skuro.idea.orgmode.parser.OrgTokenTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class OrgParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == BLOCK) {
      r = block(b, 0);
    }
    else if (t == DRAWER) {
      r = drawer(b, 0);
    }
    else if (t == OUTLINE_BLOCK) {
      r = outlineBlock(b, 0);
    }
    else if (t == TEXT_ELEMENT) {
      r = text_element(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return orgFile(b, l + 1);
  }

  /* ********************************************************** */
  // BLOCK_START BLOCK_CONTENT* BLOCK_END
  public static boolean block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block")) return false;
    if (!nextTokenIs(b, BLOCK_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BLOCK_START);
    r = r && block_1(b, l + 1);
    r = r && consumeToken(b, BLOCK_END);
    exit_section_(b, m, BLOCK, r);
    return r;
  }

  // BLOCK_CONTENT*
  private static boolean block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!consumeToken(b, BLOCK_CONTENT)) break;
      if (!empty_element_parsed_guard_(b, "block_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // DRAWER_DELIMITER DRAWER_CONTENT* DRAWER_DELIMITER
  public static boolean drawer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "drawer")) return false;
    if (!nextTokenIs(b, DRAWER_DELIMITER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DRAWER_DELIMITER);
    r = r && drawer_1(b, l + 1);
    r = r && consumeToken(b, DRAWER_DELIMITER);
    exit_section_(b, m, DRAWER, r);
    return r;
  }

  // DRAWER_CONTENT*
  private static boolean drawer_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "drawer_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!consumeToken(b, DRAWER_CONTENT)) break;
      if (!empty_element_parsed_guard_(b, "drawer_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // COMMENT|KEYWORD|CODE|PROPERTIES|WHITE_SPACE|UNMATCHED_DELIMITER|outlineBlock|block|drawer|text_element
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, KEYWORD);
    if (!r) r = consumeToken(b, CODE);
    if (!r) r = consumeToken(b, PROPERTIES);
    if (!r) r = consumeToken(b, WHITE_SPACE);
    if (!r) r = consumeToken(b, UNMATCHED_DELIMITER);
    if (!r) r = outlineBlock(b, l + 1);
    if (!r) r = block(b, l + 1);
    if (!r) r = drawer(b, l + 1);
    if (!r) r = text_element(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean orgFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "orgFile")) return false;
    int c = current_position_(b);
    while (true) {
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "orgFile", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // OUTLINE
  public static boolean outlineBlock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "outlineBlock")) return false;
    if (!nextTokenIs(b, OUTLINE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OUTLINE);
    exit_section_(b, m, OUTLINE_BLOCK, r);
    return r;
  }

  /* ********************************************************** */
  // TEXT | UNDERLINE | BOLD | CRLF
  public static boolean text_element(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "text_element")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TEXT_ELEMENT, "<text element>");
    r = consumeToken(b, TEXT);
    if (!r) r = consumeToken(b, UNDERLINE);
    if (!r) r = consumeToken(b, BOLD);
    if (!r) r = consumeToken(b, CRLF);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

}
