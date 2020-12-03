const ASTNodeTypes = require('./ASTNodeTypes')
const Factor = require('./Factor')
class Variable extends Factor{
    constructor(token){
        super(token)
        this.type = ASTNodeTypes.VARIABLE
        this.typeLexeme = null
    }
}

module.exports = Variable
