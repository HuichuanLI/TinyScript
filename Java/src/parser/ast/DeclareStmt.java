package parser.ast;

import lexer.Token;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

/**
 * Author : lihuichuan
 * Time   : 2020/12/3
 */
public class DeclareStmt extends Stmt {
    public DeclareStmt() {
        super(ASTNodeTypes.DECLARE_STMT, "declare");
    }

    public static ASTNode parse(PeekTokenIterator it) throws ParseException {
        DeclareStmt stmt = new DeclareStmt();

        it.nextMatch("var");
        Token tkn = it.peek();
        ASTNode factor = Factor.parse(it);
        if (factor == null) {
            throw new ParseException(tkn);
        }
        stmt.addChild(factor);
        Token lexeme = it.nextMatch("=");
        ASTNode expr = Expr.parse(it);
        stmt.addChild(expr);
        stmt.setLexeme(lexeme);

        return stmt;
    }
}
