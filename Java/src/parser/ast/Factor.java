package parser.ast;

import lexer.Token;
import lexer.TokenType;
import parser.util.PeekTokenIterator;

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

    public static ASTNode parse(PeekTokenIterator it) {
        Token token = it.peek();
        TokenType type = token.getType();

        if (type == TokenType.VARIABLE) {
            it.next();
            return new Variable(token);
        } else if (token.isScalar()) {
            it.next();
            return new Scalar(token);
        }
        return null;
    }
}