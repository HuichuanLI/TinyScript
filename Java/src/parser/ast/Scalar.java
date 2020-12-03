package parser;

import lexer.Token;

/**
 * Author : lihuichuan
 * Time   : 2020/12/3
 */
public class Scalar extends Factor{
    public Scalar(Token token) {
        super(token);
        this.type = ASTNodeTypes.SCALAR;
    }
}