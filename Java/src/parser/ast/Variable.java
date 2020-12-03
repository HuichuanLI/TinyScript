package parser.ast;

import lexer.Token;

/**
 * Author : lihuichuan
 * Time   : 2020/12/3
 */
public class Variable extends Factor{
    public Variable(Token token) {
        super(token);
        this.type = ASTNodeTypes.VARIABLE;
    }


}
