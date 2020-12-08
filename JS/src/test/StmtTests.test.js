const Lexer = require("../lexer/Lexer");
const arrayToGenerator = require("../common/arrayToGenerator");
const PeekTokenIterator = require("../parser/util/PeekTokenIterator");
const ParserUtils = require("../parser/util/ParserUtils");
const path = require('path')
const { assert } = require("chai");
const {DeclareStmt,AssignStmt} = require("../parser/ast/index")

describe("Stmts", () => {

    it("declare", () => {
        const it = createTokenIt("var i = 100 * 2");
        const stmt = DeclareStmt.parse(it);
        assert.equal("i 100 2 * =", ParserUtils.toPostfixExpression(stmt));
      });
    
    it("assign", () => {
        const it = createTokenIt("i = 100 * 2");
        const stmt = AssignStmt.parse(it);
        assert.equal("i 100 2 * =", ParserUtils.toPostfixExpression(stmt));
      });
})

function createTokenIt(src) {
    const lexer = new Lexer();
    const tokens = lexer.analyse(arrayToGenerator([...src]));
    const tokenIt = new PeekTokenIterator(arrayToGenerator(tokens));
    return tokenIt;
  }