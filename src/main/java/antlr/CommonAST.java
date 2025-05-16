package antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.cs.usfca.edu
 * Software rights: http://www.antlr.org/license.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.7/antlr/CommonAST.java#2 $
 */

import antlr.collections.AST;

/** Common AST node implementation */
public class CommonAST extends BaseAST {
    int ttype = Token.INVALID_TYPE;
    String text;

    public CommonAST() {}

    public CommonAST(Token tok) {
        initialize(tok);
    }

    /** Get the token text for this node */
    public String getText() {
        return text;
    }

    /** Set the token text for this node */
    public void setText(String text_) {
        text = text_;
    }

    /** Get the token type for this node */
    public int getType() {
        return ttype;
    }

    /** Set the token type for this node */
    public void setType(int ttype_) {
        ttype = ttype_;
    }

    public void initialize(int t, String txt) {
        setType(t);
        setText(txt);
    }

    public void initialize(AST t) {
        setText(t.getText());
        setType(t.getType());
    }

    public void initialize(Token tok) {
        setText(tok.getText());
        setType(tok.getType());
    }
}
