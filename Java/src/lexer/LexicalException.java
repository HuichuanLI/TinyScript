package lexer;

/**
 * Author : lihuichuan
 * Time   : 2020/11/29
 */
public class LexicalException extends Exception {
    private String msg;

    public LexicalException(char c) {
        msg = String.format("Unexpected character %c", c);
    }

    public LexicalException(String _msg) {
        msg = _msg;
    }


    @Override
    public String getMessage() {
        return msg;
    }

    @Override
    public String toString() {
        return "LexicalException{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
