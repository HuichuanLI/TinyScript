package parser.ast;

import lexer.Lexer;
import lexer.LexicalException;
import org.junit.jupiter.api.Test;
import parser.util.ParseException;
import parser.util.ParserUtils;
import parser.util.PeekTokenIterator;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.function.IntFunction;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author : lihuichuan
 * Time   : 2020/12/7
 */
class StmtTest {
    private PeekTokenIterator createTokenIt(String src) throws LexicalException, ParseException {
        Lexer lexer = new Lexer();
        ArrayList tokens = lexer.analyse(src.chars().mapToObj((IntFunction<?>) x -> (char) x));
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
        stmt.print(0);
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

    @Test
    public void function() throws FileNotFoundException, UnsupportedEncodingException, LexicalException, ParseException {
        ArrayList tokens = Lexer.fromFile("./example/function.ts");
        FunctionDeclareStmt functionStmt = (FunctionDeclareStmt) Stmt.parseStmt(new PeekTokenIterator(tokens.stream()));
        functionStmt.print(0);
        ASTNode args = functionStmt.getArgs();
        assertEquals("a", args.getChild(0).getLexeme().getValue());
        assertEquals("b", args.getChild(1).getLexeme().getValue());
//
        String type = functionStmt.getFuncType();
        assertEquals("int", type);
//
        ASTNode functionVariable = functionStmt.getFunctionVariable();
        assertEquals("add", functionVariable.getLexeme().getValue());

        ASTNode block = functionStmt.getBlock();
        assertEquals(true, block.getChild(0) instanceof ReturnStmt);

    }

    @Test
    public void function1() throws FileNotFoundException, UnsupportedEncodingException, LexicalException, ParseException {
        ArrayList tokens = Lexer.fromFile("./example/recursion.ts");
        FunctionDeclareStmt functionStmt = (FunctionDeclareStmt) Stmt.parseStmt(new PeekTokenIterator(tokens.stream()));
        functionStmt.print(0);
//        assertEquals("func fact args block", ParserUtils.toBFSString(functionStmt, 4));
//        assertEquals("args n", ParserUtils.toBFSString(functionStmt.getArgs(), 2));
//        assertEquals("block if return", ParserUtils.toBFSString(functionStmt.getBlock(), 3));

    }


}