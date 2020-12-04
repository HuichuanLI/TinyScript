const ASTNodeTypes = require('./ASTNodeTypes')
const Factor = require('./Factor')
class Variable extends Factor{

  
    constructor(token){
        super(token)
        this.type = ASTNodeTypes.VARIABLE
        this.typeLexeme = null
    }


    setTypeLexeme(lexeme){
        this.typeLexeme = lexeme
    }

    getTypeLexeme(){
        return this.typeLexeme
    }


    
}

module.exports = Variable
