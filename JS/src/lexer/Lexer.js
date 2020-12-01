const PeekIterator = require('../common/PeekIterator');
const AlphabetHelper = require('./AlphabetHelper');
const Token = require('../lexer/Token')
const TokenType = require('../lexer/TokenType')

class Lexer {
    analyse(source) {
        const tokens = []
        const it = new PeekIterator(source, '\0')

        while (it.hasNext()) {
            let c = it.next()
            if (c == "\0") {
                break;
            }
            let lookahead = it.peek();
            if (c == " " || c == "\n" || c == "\r") {
                continue;
            }

            if (c == "{" || c == "}" || c == "(" || c == ")") {
                tokens.push(new Token(TokenType.BRACKET, c));
                continue;
            }

            if (c == '"' || c == "'") {
                it.putBack();
                tokens.push(Token.makeString(it));
                continue;
            }

            if (AlphabetHelper.isLetter(c)) {
                it.putBack();
                tokens.push(Token.makeVarOrKeyword(it));
                continue;
            }

            if (AlphabetHelper.isNumber(c)) {
                it.putBack();
                tokens.push(Token.makeNumber(it));
                continue;
            }

            // +/-
            if ((c == '+' || c == '-') && AlphabetHelper.isNumber(lookahead)) {
                const lastToken = tokens[tokens.length - 1] || null;
                if (lastToken == null || !lastToken.isValue()) {
                    it.putBack();
                    tokens.push(Token.makeNumber(it));
                    continue;
                  }
            }

            if (AlphabetHelper.isOperator(c)) {
                it.putBack();
                tokens.push(Token.makeOp(it));
                continue;
            }

            
            throw LexicalException.fromChar(c);
        }
        return tokens;
    }
}

module.exports = Lexer;