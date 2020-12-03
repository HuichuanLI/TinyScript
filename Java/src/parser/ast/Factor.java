package parser;

import lexer.Token;

/**
 * Author : lihuichuan
 * Time   : 2020/12/3
 */
public class Factor extends ASTNode {
    public Factor(Token token) {
        super();
        this.lexeme = token;
        this.label = token.getValue();
    }
}