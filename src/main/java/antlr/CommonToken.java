package antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.cs.usfca.edu
 * Software rights: http://www.antlr.org/license.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.7/antlr/CommonToken.java#2 $
 */

public class CommonToken extends Token {
    // most tokens will want line and text information
    protected int line;
    protected String text = null;
    protected int col;

    public CommonToken() {}

    public CommonToken(int t, String txt) {
        type = t;
        setText(txt);
    }

    public CommonToken(String s) {
        text = s;
    }

    /** Return token's start column */
    public int getColumn() {
        return col;
    }

    public int getLine() {
        return line;
    }

    public String getText() {
        return text;
    }

    public void setText(String s) {
        text = s;
    }

    public void setLine(int l) {
        line = l;
    }

    public String toString() {
        return "[\"" + getText() + "\",<" + type + ">,line=" + line + ",col=" + col + "]";
    }

    public void setColumn(int c) {
        col = c;
    }
}
