package parser.ast;

import lexer.Token;
import lexer.TokenType;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

/**
 * Author : lihuichuan
 * Time   : 2020/12/3
 */
public class FunctionDeclareStmt extends Stmt {
    public FunctionDeclareStmt() {
        super(ASTNodeTypes.FUNCTION_DECLARE_STMT, "func");
    }

    public static ASTNode parse(PeekTokenIterator it) throws ParseException {
        it.nextMatch("func");
        FunctionDeclareStmt func = new FunctionDeclareStmt();
        Token lexeme = it.peek();
        Variable functionVariable = (Variable) Factor.parse(it);
        func.setLexeme(lexeme);
        func.addChild(functionVariable);
        it.nextMatch("(");
        ASTNode args = FunctionArgs.parse(it);
        it.nextMatch(")");
        func.addChild(args);
        Token keyword = it.nextMatch(TokenType.KEYWORD);
        if (!keyword.isType()) {
            throw new ParseException(keyword);
        }

        functionVariable.setTypeLexeme(keyword);
        ASTNode block = Block.parse(it);
        func.addChild(block);
        return func;
    }


    public ASTNode getArgs() {
        return this.getChild(1);
    }

    public Variable getFunctionVariable() {
        return (Variable) this.getChild(0);
    }

    public String getFuncType() {
        return this.getFunctionVariable().getTypeLexeme().getValue();
    }

    public Block getBlock() {
        return (Block) this.getChild(2);
    }
}