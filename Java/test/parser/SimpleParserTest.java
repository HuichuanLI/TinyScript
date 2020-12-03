package parser;

import Common.PeekIterator;
import lexer.Lexer;
import lexer.LexicalException;
import org.junit.jupiter.api.Test;
import parser.ast.ASTNode;
import parser.ast.Expr;
import parser.ast.Scalar;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author : lihuichuan
 * Time   : 2020/12/3
 */
class SimpleParserTest {

    @Test
    void parse() throws LexicalException, ParseException {
        Stream source = "1+2+3+4".chars().mapToObj(x->(char)x);
        Lexer lexer = new Lexer();
        PeekTokenIterator it = new PeekTokenIterator(lexer.analyse(source).stream());
        ASTNode expr = SimpleParser.parse(it);

        assertEquals(2, expr.getChildren().size());
        Scalar v1 = (Scalar)expr.getChild(0);
        assertEquals("1", v1.getLexeme().getValue());
        assertEquals("+", expr.getLexeme().getValue());
//
        Expr e2 = (Expr)expr.getChild(1);
        Scalar v2 = (Scalar)e2.getChild(0);
        assertEquals("2", v2.getLexeme().getValue());
        assertEquals("+", e2.getLexeme().getValue());
//
        Expr e3 = (Expr)e2.getChild(1);
        Scalar v3 = (Scalar)e3.getChild(0);
        assertEquals("3", v3.getLexeme().getValue());
        assertEquals("+", e3.getLexeme().getValue());

        Scalar v4 = (Scalar)e3.getChild(1);
        assertEquals("4", v4.getLexeme().getValue());

        expr.print(0);


    }
}