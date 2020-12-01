const Token = require('../lexer/Token')
const TokenType = require('../lexer/TokenType')
const PeekIterator = require('../common/PeekIterator')
const arrayToGenerator = require('../common/arrayToGenerator')
const {assert} = require("chai")


describe("Token",()=>{
    function assertToken(token, value, type) {
        assert.equal(token.getValue(), value)
        assert.equal(token.getType(), type)
    }
    it("varOrKeyword", () => {
        const it1 = new PeekIterator(arrayToGenerator([..."if abc"]))
        const it2 = new PeekIterator(arrayToGenerator([..."true abc"]))

        const token1 = Token.makeVarOrKeyword(it1)
        const token2 = Token.makeVarOrKeyword(it2)
        it1.next()

        const token3 = Token.makeVarOrKeyword(it1)
        assertToken(token1, "if", TokenType.KEYWORD)
        assertToken(token2, "true", TokenType.BOOLEAN)
        assertToken(token3, "abc", TokenType.VARIABLE)
        
    })
})