const ASTNode = require("./ASTNode");
const ASTNodeTypes = require("./ASTNodeTypes");

class Expr extends ASTNode {
  constructor() {
    super();
  }

  static fromToken(type, token) {
    const expr = new Expr();
    expr.label = token.getValue();
    expr.lexeme = token;
    expr.type = type;
    return expr;
  }
}

module.exports = Expr;