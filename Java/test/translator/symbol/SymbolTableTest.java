package translator.symbol;

import lexer.Token;
import lexer.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author : lihuichuan
 * Time   : 2020/12/10
 */
class SymbolTableTest {
    @Test
    public void symbolTable() {
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.createLabel("L0", new Token(TokenType.VARIABLE, "foo"));
        symbolTable.createVariable();
        symbolTable.createSymbolByLexeme(new Token(TokenType.VARIABLE, "a"));
        assertEquals(2, symbolTable.localSize());
    }

    @Test
    public void symbolTableChain() {
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.createSymbolByLexeme(new Token(TokenType.VARIABLE, "a"));

        SymbolTable childTable = new SymbolTable();
        symbolTable.addChild(childTable);

        SymbolTable childChildTable = new SymbolTable();
        childTable.addChild(childChildTable);
        assertEquals(true, childChildTable.exists(new Token(TokenType.VARIABLE, "a")));
        assertEquals(true, childTable.exists(new Token(TokenType.VARIABLE, "a")));

    }

    @Test
    public void symbolOffset() {

        SymbolTable symbolTable = new SymbolTable();


        symbolTable.createSymbolByLexeme(new Token(TokenType.INTEGER, "100"));
        Symbol symbolA = symbolTable.createSymbolByLexeme(new Token(TokenType.VARIABLE, "a"));
        Symbol symbolB = symbolTable.createSymbolByLexeme(new Token(TokenType.VARIABLE, "b"));


        SymbolTable childTable = new SymbolTable();
        symbolTable.addChild(childTable);
        Symbol anotherSymbolB = childTable.createSymbolByLexeme(new Token(TokenType.VARIABLE, "b"));
        Symbol symbolC = childTable.createSymbolByLexeme(new Token(TokenType.VARIABLE, "c"));

        assertEquals(0, symbolA.getOffset());
        assertEquals(1, symbolB.getOffset());
        assertEquals(1, anotherSymbolB.getOffset());
        assertEquals(1, anotherSymbolB.getLayerOffset());
        assertEquals(0, symbolC.getOffset());
        assertEquals(0, symbolC.getLayerOffset());


    }


}