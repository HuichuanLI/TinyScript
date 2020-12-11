package translator;

import lexer.LexicalException;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.ast.ASTNode;
import parser.util.ParseException;
import translator.symbol.SymbolTable;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author : lihuichuan
 * Time   : 2020/12/11
 */
class TransExprTests {
    void assertOpcodes(String[] lines, ArrayList<TAInstruction> opcodes) {
        for (int i = 0; i < opcodes.size(); i++) {
            TAInstruction opcode = opcodes.get(i);
            String strVal = opcode.toString();
            assertEquals(lines[i], strVal);
        }
    }

    @Test
    public void transExpr() throws LexicalException, ParseException {
        String source = "a+(b-c)+d*(b-c)*2";
        ASTNode p = Parser.parse(source);
        ASTNode exprNode = p.getChild(0);


        Translator translator = new Translator();
        SymbolTable symbolTable = new SymbolTable();
        TAProgram program = new TAProgram();
        translator.translateExpr(program, exprNode, symbolTable);

        String[] expectedResults = new String[]{
                "p0 = b - c",
                "p1 = b - c",
                "p2 = p1 * 2",
                "p3 = d * p2",
                "p4 = p0 + p3",
                "p5 = a + p4"
        };
        System.out.println(program.getInstructions());
        assertOpcodes(expectedResults, program.getInstructions());

    }

    @Test
    public void testAssignStmt1() throws LexicalException, ParseException {
        String source = "a=1";
        ASTNode astNode = Parser.parse(source);
        astNode.getChildren();
        Translator translator = new Translator();
        TAProgram program = translator.translate(astNode);
        String code = program.toString();
        System.out.println(code);

        assertEquals("a = 1", code);
    }

}