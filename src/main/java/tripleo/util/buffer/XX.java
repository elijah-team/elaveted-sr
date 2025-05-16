package tripleo.util.buffer;

import tripleo.util.*;

public enum XX {
   SPACE,
   LPAREN,
   RPAREN,
   COMMA,
   INDENT,
   LBRACE,
   RBRACE;

   public String toString() {
      return this.getText();
   }

   public String getText() {
      if (this == SPACE) {
         return " ";
      } else if (this == LPAREN) {
         return "(";
      } else if (this == RPAREN) {
         return ")";
      } else if (this == LBRACE) {
         return "{";
      } else if (this == RBRACE) {
         return "}";
      } else if (this == COMMA) {
         return ",";
      } else {
         NotImplementedException.raise();
         return null;
      }
   }
}
