package parser.ast;

import lexer.Lexer;
import lexer.LexicalException;
import org.junit.jupiter.api.Test;
import parser.util.ParseException;
import parser.util.ParserUtils;
import parser.util.PeekTokenIterator;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author : lihuichuan
 * Time   : 2020/12/7
 */
class StmtTest {
    private PeekTokenIterator createTokenIt(String src) throws LexicalException, ParseException {
        Lexer lexer = new Lexer();
        ArrayList tokens = lexer.analyse(src.chars().mapToObj(x -> (char) x));
        PeekTokenIterator tokenIt = new PeekTokenIterator(tokens.stream());
        return tokenIt;
    }

    @Test
    public void declare() throws LexicalException, ParseException {
        PeekTokenIterator it = createTokenIt("var i = 100 * 2");
        ASTNode stmt = DeclareStmt.parse(it);
        stmt.print(0);
        assertEquals(ParserUtils.toPostfixExpression(stmt), "i 100 2 * =");
    }

    @Test
    public void assign() throws LexicalException, ParseException {
        PeekTokenIterator it = createTokenIt("i = 100 * 2");
        ASTNode stmt = AssignStmt.parse(it);
        stmt.print(0);
        assertEquals(ParserUtils.toPostfixExpression(stmt), "i 100 2 * =");
    }

    @Test
    public void ifstmt() throws LexicalException, ParseException {
        PeekTokenIterator it = createTokenIt("if(a){\n" +
                "a = 1\n" +
                "}"
        );

        IfStmt stmt = (IfStmt) IfStmt.parse(it);
        stmt.print(0);
        Variable expr = (Variable) stmt.getChild(0);
        Block block = (Block) stmt.getChild(1);
        AssignStmt assignStmt = (AssignStmt) block.getChild(0);

        assertEquals("a", expr.getLexeme().getValue());
        assertEquals("=", assignStmt.getLexeme().getValue());
    }

    @Test
    public void ifElseStmt() throws LexicalException, ParseException {
        PeekTokenIterator it = createTokenIt("if(a) {\n" +
                "a = 1\n" +
                "} else {\n" +
                "a = 2\n" +
                "a = a * 3" +
                "}"
        );
        IfStmt stmt = (IfStmt) IfStmt.parse(it);
        Variable expr = (Variable) stmt.getChild(0);
        Block block = (Block) stmt.getChild(1);
        AssignStmt assignStmt = (AssignStmt) block.getChild(0);
        Block elseBlock = (Block) stmt.getChild(2);
        AssignStmt assignStmt2 = (AssignStmt) elseBlock.getChild(0);

        assertEquals("a", expr.getLexeme().getValue());
        assertEquals("=", assignStmt.getLexeme().getValue());
        assertEquals("=", assignStmt2.getLexeme().getValue());
        assertEquals(2, elseBlock.getChildren().size());
    }

}