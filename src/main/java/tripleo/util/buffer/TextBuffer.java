package tripleo.util.buffer;

public interface TextBuffer extends Buffer {
   void append(String var1);

   void append_s(String var1);

   void append_cb(String var1);

   void decr_i();

   void incr_i();

   void append_nl_i(String var1);

   void append_nl(String var1);

   void append_ln(String var1);
}
