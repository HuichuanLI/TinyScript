package parser.ast;

import lexer.Token;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

/**
 * Author : lihuichuan
 * Time   : 2020/12/8
 */
public class ReturnStmt extends ASTNode {
    public ReturnStmt() {
        super(ASTNodeTypes.RETURN_STMT, "return");
    }

    public static ASTNode parse(PeekTokenIterator it) throws ParseException {
        Token lexeme = it.nextMatch("return");
        ASTNode expr = Expr.parse(it);

        ReturnStmt stmt = new ReturnStmt();
        stmt.setLexeme(lexeme);
        if (expr != null) {
            stmt.addChild(expr);
        }
        return stmt;
    }

}
