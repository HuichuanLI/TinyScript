const ASTNode = require('./ASTNode')

class Stmt extends ASTNode {
    constructor(type, label) {
        super(type, label)
    }
}

module.exports = Stmt
