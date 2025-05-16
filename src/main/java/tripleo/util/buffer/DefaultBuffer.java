package tripleo.util.buffer;

public class DefaultBuffer implements TextBuffer {
   StringBuilder text = new StringBuilder();
   private Buffer _parent;
   private Buffer _next;
   private Buffer _previous;
   private int incr = 0;

   public DefaultBuffer(String string) {
      this.append(string);
   }

   public void append(String string) {
      this.text.append(string);
   }

   public void append_s(String string) {
      this.text.append(string);
      this.text.append(" ");
   }

   public void append_cb(String string) {
      this.text.append(string);
      this.text.append("}");
   }

   public void decr_i() {
      --this.incr;
   }

   public void incr_i() {
      ++this.incr;
   }

   public void append_nl_i(String string) {
      this.text.append(string);
      this.text.append("\n");
      this.doIndent();
   }

   private void doIndent() {
      this.text.append(new_String('\t', this.incr));
   }

   public static String new_String(char c, int length) {
      StringBuilder s = new StringBuilder(length);

      while(length-- > 0) {
         s.append(c);
      }

      return s.toString();
   }

   public void append_nl(String string) {
      this.text.append(string);
      this.text.append("\n");
      this.doIndent();
   }

   public void append_ln(String string) {
      this.text.append(string);
      this.text.append("\n");
      this.doIndent();
   }

   public void append_s(String string, XX sep) {
      this.text.append(string);
      this.text.append(sep.getText());
   }

   public String getText() {
      return this.text.toString();
   }

   public Buffer next() {
      return this._next;
   }

   public Buffer previous() {
      return this._previous;
   }

   public Buffer parent() {
      return this._parent;
   }
}
