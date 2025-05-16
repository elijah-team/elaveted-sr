package tripleo.util.buffer;

public interface Buffer {
   String getText();

   Buffer next();

   Buffer previous();

   Buffer parent();
}
