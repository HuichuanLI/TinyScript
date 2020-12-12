const TAProgram = require('./TAProgram')
const SymbolTable = require('./symbol/SymbolTable')
const Token = require('../lexer/Token')
const TokenType = require('../lexer/TokenType')
const ParseException = require('../parser/util/ParseException')
const Expr = require('../parser/ast/Expr')
const TAInstruction = require('../translator/TAInstruction')
const TAInstructionType = require('../translator/TAInstructionType')
const ASTNodeTypes = require('../parser/ast/ASTNodeTypes')


class Translator {

    translate(astNode) {
        const program = new TAProgram()
        const symbolTable = new SymbolTable()
        for (const child of astNode.getChildren()) {

            // mutable 
            // immutable 可以直接并发
            this.translateStmt(program, child, symbolTable)
        }
        program.setStaticSymbols(symbolTable)
        return program
    }

    translateStmt(program, node, symbolTable) {
        switch (node.getType()) {
            case ASTNodeTypes.ASSIGN_STMT:
                this.translateAssignStmt(program, node, symbolTable);
                return;
            case ASTNodeTypes.DECLARE_STMT:
                this.translateDeclareStmt(program, node, symbolTable);
                return;
        }
        throw new Error("Translator not impl. for " + node.getType())
    }

    translateAssignStmt(program, node, symbolTable) {
        // a = exp
        const assigned = symbolTable.createSymbolByLexeme(node.getChild(0).getLexeme())
        const expr = node.getChild(1)
        const addr = this.translateExpr(program, expr, symbolTable)
        program.add(new TAInstruction(TAInstructionType.ASSIGN, assigned, "=", addr, null))
    }

    translateDeclareStmt(program, node, symbolTable) {
        // var a = exp
        const lexeme = node.getChild(0).getLexeme()
        if (symbolTable.exists(lexeme)) {
            throw new ParseException("Syntax Error, Identifier " + lexeme.getValue() + " is already defined.")
        }
        const assigned = symbolTable.createSymbolByLexeme(node.getChild(0).getLexeme())
        const expr = node.getChild(1)
        const addr = this.translateExpr(program, expr, symbolTable)
        program.add(new TAInstruction(TAInstructionType.ASSIGN, assigned, "=", addr, null))
    }

    /**
     * Expr -> Expr1 op Expr2
     *  T: result = Expr1.addr op Expr2.addr
     * Expr1 -> Factor
     *  T: Expr1.addr = symbolTable.find(factor)
     */
    translateExpr(
        program,
        node,
        symbolTable) {
        if (node.isValueType()) {
            const addr = symbolTable.createSymbolByLexeme(node.getLexeme())
            node.setProp("addr", addr)
            return addr
        } else if (node.getType() == ASTNodeTypes.CALL_EXPR) {
            throw 'not impl.'
        } else if (node instanceof Expr) {
            for (const child of node.getChildren()) {
                this.translateExpr(program, child, symbolTable)
            }
            if (node.getProp("addr") == null) {
                node.setProp("addr", symbolTable.createVariable())
            }
            const instruction = new TAInstruction(
                TAInstructionType.ASSIGN,
                node.getProp("addr"),
                node.getLexeme().getValue(),
                node.getChild(0).getProp("addr"),
                node.getChild(1).getProp("addr")
            )
            program.add(instruction)
            return instruction.getResult()
        }
        throw new Error("Unexpected node type :" + node.getType());

    }

}
module.exports = Translator