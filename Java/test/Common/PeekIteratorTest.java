package Common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author : lihuichuan
 * Time   : 2020/11/28
 */
class PeekIteratorTest {

    @Test
    void peek() {
        String source = "abcdefg";
        PeekIterator<Character> it = new PeekIterator<Character>(source.chars().mapToObj(c -> (char) c));

        assertEquals('a', it.next());
        assertEquals('b', it.next());

        it.next();
        it.next();
        assertEquals('e', it.next());
        assertEquals('f', it.peek());
        assertEquals('f', it.peek());

    }

    @Test
    void putBack() {
    }
}