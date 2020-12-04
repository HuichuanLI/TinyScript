const ASTNodeTypes = require('./ASTNodeTypes')
const Stmt = require('./Stmt')
class Block extends Stmt {
    constructor() {
        super(ASTNodeTypes.BLOCK, 'block')
    }
}

module.exports = Block 
