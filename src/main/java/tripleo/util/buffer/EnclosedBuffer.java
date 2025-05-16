package tripleo.util.buffer;

public class EnclosedBuffer extends DefaultBuffer {
   private String _payload;
   private String _right;
   private String _left;

   public EnclosedBuffer(String left, String right) {
      super("");
      this._left = left;
      this._right = right;
   }

   public EnclosedBuffer(String left, XX right) {
      super("");
      this._left = left;
      this._right = right.getText();
   }

   public void setPayload(BufferSequenceBuilder sequence) {
      this._payload = sequence.build().getText();
   }

   public String getText() {
      return String.format("%s%s%s", this._left, this._payload, this._right);
   }

   public void setPayload(String string) {
      this._payload = string;
   }
}
