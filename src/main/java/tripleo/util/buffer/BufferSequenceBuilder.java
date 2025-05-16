package tripleo.util.buffer;

import java.util.*;

public class BufferSequenceBuilder {
   private int semi_eol_count = 0;
   private String _built;
   private Map parts;
   private Map part_names;

   public BufferSequenceBuilder(int i) {
      this.parts = new HashMap(i);
      this.part_names = new HashMap(i);
   }

   public BufferSequenceBuilder named(String string) {
      this.part_names.put(this.part_names.size(), string);
      return this;
   }

   public BufferSequenceBuilder semieol() {
      String key = "klkkl" + ++this.semi_eol_count;
      this.parts.put(key, new DefaultBuffer(";\n"));
      this.part_names.put(this.part_names.size() + 1, key);
      return this;
   }

   public void set(String part_name, String setTo) {
      this.parts.put(part_name, new DefaultBuffer(setTo));
   }

   public void set(String part_name, String setTo, char sep) {
      this.parts.put(part_name, new DefaultBuffer(setTo + sep));
   }

   public BufferSequence build() {
      BufferSequence bsq = new BufferSequence();
      Iterator var2 = this.part_names.values().iterator();

      while(var2.hasNext()) {
         String s = (String)var2.next();
         bsq.add((TextBuffer)this.parts.get(s));
      }

      return bsq;
   }

   public void set(String part_name, String setTo, XX sep) {
      this.parts.put(part_name, new DefaultBuffer(setTo + sep.toString()));
   }

   public void set(String part_name, TextBuffer sb2) {
      this.parts.put(part_name, sb2);
   }

   public String fieldNamed(String string) {
      return ((TextBuffer)this.parts.get(string)).getText();
   }

   public boolean fieldIsSemiEol(int i) {
      return ((String)this.part_names.get(i)).matches("klklkl[0-9]+");
   }
}
