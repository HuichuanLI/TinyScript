package parser.util;

import Common.PeekIterator;
import lexer.Lexer;
import lexer.LexicalException;
import lexer.Token;
import org.junit.jupiter.api.Test;
import parser.ast.ASTNode;
import parser.ast.Expr;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author : lihuichuan
 * Time   : 2020/12/7
 */
class ParserUtilsTest {

    @Test
    public void simple() throws LexicalException, ParseException {
        ASTNode expr = createExpr("1+1+1");
        assertEquals("1 1 1 + +", ParserUtils.toPostfixExpression(expr));
    }

    private ASTNode createExpr(String src) throws LexicalException, ParseException {
        Lexer lexer = new Lexer();
        ArrayList<Token> tokens = lexer.analyse(src.chars().mapToObj(x -> (char) x));
        PeekTokenIterator tokenIt = new PeekTokenIterator(tokens.stream());
        return Expr.parse(tokenIt);
    }
}