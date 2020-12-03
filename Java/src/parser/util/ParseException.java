package parser.util;

import lexer.Token;

/**
 * Author : lihuichuan
 * Time   : 2020/12/3
 */
public class ParseException extends Exception{
    private String _msg;

    public ParseException(String msg) {
        this._msg = msg;
    }


    public ParseException(Token token) {
        _msg = String.format("Syntax Error, unexpected token %s", token.getValue());
    }

    @Override
    public String getMessage() {
        return _msg;
    }
}
