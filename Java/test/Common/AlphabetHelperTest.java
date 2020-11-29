package Common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author : lihuichuan
 * Time   : 2020/11/29
 */
class AlphabetHelperTest {

    @Test
    void isLetter() {

        assertEquals(true, AlphabetHelper.isLetter('a'));
        assertEquals(false, AlphabetHelper.isLetter('*'));
        assertEquals(true, AlphabetHelper.isLiteral('a'));
        assertEquals(true, AlphabetHelper.isLiteral('_'));
        assertEquals(true, AlphabetHelper.isLiteral('9'));
        assertEquals(false, AlphabetHelper.isLiteral('*'));
        assertEquals(false, AlphabetHelper.isLetter('*'));
        assertEquals(true, AlphabetHelper.isNumber('1'));
        assertEquals(true, AlphabetHelper.isNumber('9'));
        assertEquals(false, AlphabetHelper.isNumber('x'));
        assertEquals(true, AlphabetHelper.isOperator('&'));
        assertEquals(true, AlphabetHelper.isOperator('*'));
        assertEquals(true, AlphabetHelper.isOperator('+'));
        assertEquals(true, AlphabetHelper.isOperator('/'));
        assertEquals(true, AlphabetHelper.isOperator('='));
        assertEquals(true, AlphabetHelper.isOperator(','));
        assertEquals(false, AlphabetHelper.isOperator('a'));
    }

    @Test
    void isNumber() {
    }

    @Test
    void isLiteral() {
    }

    @Test
    void isOperator() {
    }
}