const TokenType = require("../../lexer/TokenType");
const ASTNode = require('./ASTNode');
const ASTNodeTypes = require("./ASTNodeTypes");



class Factor extends ASTNode {
  constructor(token) {
    super();
    this.lexeme = token;
    this.label = token.getValue();
  }
  static parse(it) {
    const token = it.peek();
    const type = token.getType();

    if (type == TokenType.VARIABLE) {
      it.next();
      this.type = ASTNodeTypes.VARIABLE
    } else if (token.isScalar()) {
      it.next();
      this.type = ASTNodeTypes.SCALAR
    }
    this.label = token.getValue()
    this.lexeme = token
    return new Factor(token);
  }


}


module.exports = Factor;

const {
  Variable,
  Scalar
} = require("./index");
Factor.parse = it => {
  const token = it.peek();
  const type = token.getType();

  if (type == TokenType.VARIABLE) {
    it.next();
    return new Variable(token);
  } else if (token.isScalar()) {
    it.next();
    return new Scalar(token);
  }
  return null;
};