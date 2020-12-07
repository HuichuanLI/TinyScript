package parser.ast;

import lexer.Token;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

/**
 * Author : lihuichuan
 * Time   : 2020/12/3
 */
public class IfStmt extends Stmt {
    public IfStmt() {
        super(ASTNodeTypes.IF_STMT, "if");
    }

    public static ASTNode parse(PeekTokenIterator it) throws ParseException {
        return parseIF(it);
    }

    // IfStmt -> If(Expr) Block Tail
    public static ASTNode parseIF(PeekTokenIterator it) throws ParseException {
        Token lexeme = it.nextMatch("if");
        it.nextMatch("(");
        IfStmt ifStmt = new IfStmt();
        ifStmt.setLexeme(lexeme);
        ASTNode expr = Expr.parse(it);
        ifStmt.addChild(expr);
        it.nextMatch(")");
        ASTNode block = Block.parse(it);
        ifStmt.addChild(block);

        ASTNode tail = parseTail(it);
        if (tail != null) {
            ifStmt.addChild(tail);
        }
        return ifStmt;

    }

    // Tail -> else {Block} | else IFStmt | ε
    public static ASTNode parseTail(PeekTokenIterator it) throws ParseException {
        if (!it.hasNext() || !it.peek().getValue().equals("else")) {
            return null;
        }
        it.nextMatch("else");
        Token lookahead = it.peek();

        if (lookahead.getValue().equals("{")) {
            return Block.parse(it);
        } else if (lookahead.getValue().equals("if")) {
            return parseIF(it);
        } else {
            return null;
        }

    }

}