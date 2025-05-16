package tripleo.util.buffer;

import java.util.*;

public class BufferSequence {
   private List parts = new ArrayList();

   public void add(TextBuffer element) {
      this.parts.add(element);
   }

   public String getText() {
      StringBuffer sb = new StringBuffer();
      Iterator var2 = this.parts.iterator();

      while(var2.hasNext()) {
         TextBuffer element = (TextBuffer)var2.next();
         sb.append(element.getText());
      }

      return sb.toString();
   }
}
