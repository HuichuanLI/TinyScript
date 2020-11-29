package lexer;

import Common.PeekIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author : lihuichuan
 * Time   : 2020/11/29
 */
class TokenTest {

    void assertToken(Token token, String value, TokenType type) {
        assertEquals(value, token.getValue());
        assertEquals(type, token.getType());
    }


    @Test
    public void test_varOrKeyword() {
        PeekIterator it1 = new PeekIterator<Character>("if abc".chars().mapToObj(x -> (char) x));
        PeekIterator it2 = new PeekIterator<Character>("true abc".chars().mapToObj(x -> (char) x));
        Token token1 = Token.makeVarOrKeyword(it1);
        Token token2 = Token.makeVarOrKeyword(it2);

        assertToken(token1, "if", TokenType.KEYWORD);
        assertToken(token2, "true", TokenType.BOOLEAN);
        it1.next();
        Token token3 = Token.makeVarOrKeyword(it1);

        assertToken(token3, "abc", TokenType.VARIABLE);

    }

    @Test
    public void test_makeString() throws LexicalException {
        String[] tests = {
                "\"123\"",
                "\'123\'"
        };

        for (String test : tests) {
            PeekIterator it = new PeekIterator<Character>(test.chars().mapToObj(x -> (char) x));
            Token token = Token.makeString(it);
            assertToken(token, test, TokenType.STRING);
        }

    }

    @Test
    public void test_makeOperator() throws LexicalException {
        String[] tests = {
                "+ xxx",
                "++mmm",
                "/=g",
                "==1",
                "&=3982",
                "&777",
                "||xxx",
                "^=111",
                "%7"
        };

        String[] results = {"+", "++", "/=", "==", "&=", "&", "||", "^=", "%"};

        int i = 0;
        for (String test : tests) {

            PeekIterator it = new PeekIterator<Character>(test.chars().mapToObj(x -> (char) x));
            Token token = Token.makeOp(it);
            assertToken(token, results[i++], TokenType.OPERATOR);
        }
    }
}