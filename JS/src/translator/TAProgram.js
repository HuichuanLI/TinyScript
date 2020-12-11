const StaticSymbolTable = require('./symbol/StaticSymbolTable')
const TAInstruction = require('./TAInstruction')
const TAInstructionType = require('./TAInstructionType')
const SymbolType = require('./symbol/SymbolType')

// Three Address
class TAProgram {
    constructor() {

        this.instructions = []
        this.labelCounter = 0
        this.staticSymbolTable = new StaticSymbolTable()
    }

    add(code) {
        this.instructions.push(code)
    }

    getInstructions() {
        return this.instructions
    }

    getStaticSymbolTable() {
        return this.staticSymbolTable
    }

    addLabel() {
        const label = "L" + this.labelCounter++
        const taCode = new TAInstruction(TAInstructionType.LABEL, null, null, null, null)
        taCode.setArg1(label)
        this.instructions.push(taCode)
        return taCode
    }

    setStaticSymbols(symbolTable) {
        for (const symbol of symbolTable.getSymbols()) {
            if (symbol.getType() == SymbolType.IMMEDIATE_SYMBOL) {
                this.staticSymbolTable.add(symbol);
            }
        }

        for (const child of symbolTable.getChildren()) {
            this.setStaticSymbols(child)
        }
    }
}
module.exports = TAProgram