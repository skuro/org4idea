// This is a generated file. Not intended for manual editing.
package tk.skuro.idea.orgmode.parser;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import tk.skuro.idea.orgmode.psi.impl.*;

public interface OrgTokenTypes {

  IElementType BLOCK = new OrgTokenType("BLOCK");
  IElementType DRAWER = new OrgTokenType("DRAWER");
  IElementType OUTLINE_BLOCK = new OrgTokenType("OUTLINE_BLOCK");
  IElementType TEXT_ELEMENT = new OrgTokenType("TEXT_ELEMENT");

  IElementType BLOCK_CONTENT = new OrgElementType("BLOCK_CONTENT");
  IElementType BLOCK_END = new OrgElementType("BLOCK_END");
  IElementType BLOCK_START = new OrgElementType("BLOCK_START");
  IElementType BOLD = new OrgElementType("BOLD");
  IElementType CODE = new OrgElementType("CODE");
  IElementType COMMENT = new OrgElementType("COMMENT");
  IElementType CRLF = new OrgElementType("CRLF");
  IElementType DRAWER_CONTENT = new OrgElementType("DRAWER_CONTENT");
  IElementType DRAWER_DELIMITER = new OrgElementType("DRAWER_DELIMITER");
  IElementType KEYWORD = new OrgElementType("KEYWORD");
  IElementType OUTLINE = new OrgElementType("OUTLINE");
  IElementType PROPERTIES = new OrgElementType("PROPERTIES");
  IElementType TEXT = new OrgElementType("TEXT");
  IElementType UNDERLINE = new OrgElementType("UNDERLINE");
  IElementType UNMATCHED_DELIMITER = new OrgElementType("UNMATCHED_DELIMITER");
  IElementType WHITE_SPACE = new OrgElementType("WHITE_SPACE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == BLOCK) {
        return new OrgBlockImpl(node);
      }
      else if (type == DRAWER) {
        return new OrgDrawerImpl(node);
      }
      else if (type == OUTLINE_BLOCK) {
        return new OrgOutlineBlockImpl(node);
      }
      else if (type == TEXT_ELEMENT) {
        return new OrgTextElementImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
