const ASTNodeTypes = require("./ASTNodeTypes")
const Stmt = require('./Stmt')
class IfStmt extends Stmt {
  constructor() {
    super(ASTNodeTypes.IF_STMT, "if");
  }
}
module.exports = IfStmt
