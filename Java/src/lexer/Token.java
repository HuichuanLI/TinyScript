package lexer;

import Common.AlphabetHelper;
import Common.PeekIterator;

/**
 * Author : lihuichuan
 * Time   : 2020/11/28
 */
public class Token {
    TokenType _type;
    String _value;

    public Token(TokenType type, String value) {
        this._type = type;
        this._value = value;
    }

    public TokenType getType() {
        return _type;
    }

    public String getValue() {
        return _value;
    }

    public boolean isVariable() {
        return _type == TokenType.VARIABLE;
    }

    public boolean isScalar() {
        return _type == TokenType.INTEGER || _type == TokenType.FLOAT ||
                _type == TokenType.STRING || _type == TokenType.BOOLEAN;
    }

    /**
     * 提取变量或关键字
     *
     * @param it
     * @return Token
     */
    public static Token makeVarOrKeyword(PeekIterator<Character> it) {
        String s = "";
        while (it.hasNext()) {
            if (AlphabetHelper.isLiteral(it.peek())) {
                s += it.peek();
            } else {
                break;
            }
            it.next();
        }

        // 判断关键词OR变量
        if (Keywords.isKeyword(s)) {
            return new Token(TokenType.KEYWORD, s);
        }

        if (s.equals("true") || s.equals("false")) {
            return new Token(TokenType.BOOLEAN, s);
        }

        return new Token(TokenType.VARIABLE, s);
    }

    @Override
    public String toString() {
        return String.format("type %s, value %s", _type, _value);
    }


}
