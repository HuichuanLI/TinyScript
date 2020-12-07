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

    @Test
    public void simple1() throws LexicalException, ParseException {
        ASTNode expr = createExpr("\"1\" == \"\"");
        assertEquals("\"1\" \"\" ==", ParserUtils.toPostfixExpression(expr));
    }

    @Test
    public void complex() throws LexicalException, ParseException {
        ASTNode expr1 = createExpr("1+2*3");
        ASTNode expr2 = createExpr("1*2+3");
        ASTNode expr3 = createExpr("10 * (7 + 4)");
        ASTNode expr4 = createExpr("(1*2!=7)==3!=4*5+6");

        assertEquals("1 2 3 * +", ParserUtils.toPostfixExpression(expr1));
        assertEquals("1 2 * 3 +", ParserUtils.toPostfixExpression(expr2));
        assertEquals("10 7 4 + *", ParserUtils.toPostfixExpression(expr3));
        assertEquals("1 2 * 7 != 3 4 5 * 6 + != ==", ParserUtils.toPostfixExpression(expr4));
        // i++ ++i
    }
    private ASTNode createExpr(String src) throws LexicalException, ParseException {
        Lexer lexer = new Lexer();
        ArrayList<Token> tokens = lexer.analyse(src.chars().mapToObj(x -> (char) x));
        PeekTokenIterator tokenIt = new PeekTokenIterator(tokens.stream());
        return Expr.parse(tokenIt);
    }
}