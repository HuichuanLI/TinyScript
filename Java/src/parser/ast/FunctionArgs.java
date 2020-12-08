package parser.ast;

import lexer.Token;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

/**
 * Author : lihuichuan
 * Time   : 2020/12/8
 */
public class FunctionArgs extends ASTNode {
    public FunctionArgs() {
        super();
        this.label = "args";
    }

    public static ASTNode parse(PeekTokenIterator it) throws ParseException {
        FunctionArgs args = new FunctionArgs();
        while (it.peek().isType()) {
            Token type = it.next();
            Variable variable = (Variable) Factor.parse(it);
            variable.setTypeLexeme(type);
            args.addChild(variable);

            if (!it.peek().getValue().equals(")")) {
                it.nextMatch(",");
            }
        }
        return args;

    }
}