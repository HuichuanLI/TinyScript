package lexer;

import Common.AlphabetHelper;
import Common.PeekIterator;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Author : lihuichuan
 * Time   : 2020/11/28
 */
public class Lexer {

    public ArrayList<Token> analyse(Stream source) throws LexicalException {
        ArrayList<Token> tokens = new ArrayList<>();
        PeekIterator<Character> it = new PeekIterator<>(source, (char) 0);

        while (it.hasNext()) {
            char c = it.next();
            if (c == 0) {
                break;
            }
            char lookahead = it.peek();

            if (c == ' ' || c == '\n') {
                continue;
            }
            if (c == '{' || c == '}' || c == '(' || c == ')') {
                tokens.add(new Token(TokenType.BRACKET, c + ""));
                continue;
            }

            if (c == '"' || c == '\'') {
                it.putBack();
                tokens.add(Token.makeString(it));
                continue;
            }

            if (AlphabetHelper.isLetter(c)) {
                it.putBack();
                ;
                tokens.add(Token.makeVarOrKeyword(it));
                continue;
            }


            if (AlphabetHelper.isNumber(c)) {
                it.putBack();
                tokens.add(Token.makeNumber(it));
                continue;
            }


            // + -
            // +-: 3+5,+5,3 *-5
            if ((c == '+' || c == '-' || c == '.') && AlphabetHelper.isNumber(lookahead)) {
                Token lastToken = tokens.size() == 0 ? null : tokens.get(tokens.size() - 1);

                if (lastToken == null || !lastToken.isValue() || lastToken.isOperator()) {
                    it.putBack();
                    tokens.add(Token.makeNumber(it));
                    continue;
                }
            }

            if (AlphabetHelper.isOperator(c)) {
                it.putBack();
                tokens.add(Token.makeOp(it));
                continue;
            }

            throw new LexicalException(c);

        }
        return tokens;

    }
}
