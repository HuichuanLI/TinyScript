const Factor = require('../ast/Factor')
const ASTNodeTypes = require('../ast/ASTNodeTypes')
const LinkedList = require('linkedlist')

class ParserUtils {
    static toPostfixExpression(node) {
        if (node instanceof Factor) {
            return node.getLexeme().getValue()
        }

        const prts = []
        for (const child of node.getChildren()) {
            prts.push(ParserUtils.toPostfixExpression(child));
        }
        const lexemeStr = node.getLexeme() != null ? node.getLexeme().getValue() : "";
        if (lexemeStr.length > 0) {
            return prts.join(" ") + " " + lexemeStr
        } else {
            return prts.join(" ")
        }
    }

    static toBFSString(root,max){
        const queue = new LinkedList()
        const list = []
        queue.push(root)

        let c = 0
        while(queue.length > 0 && c++ < max) {
            const node = queue.shift()
            list.push(node.getLabel())
            for(const child of node.getChildren()) {
                queue.push(child);
            }
        }
        return list.join(" ")
    }
}
module.exports = ParserUtils