const TokenType = require("../../lexer/TokenType");
const ASTNode = require('./ASTNode')

class Factor extends ASTNode {
    constructor(token) {
      super();
      this.lexeme = token;
      this.label = token.getValue();
    }
  }
  module.exports = Factor;