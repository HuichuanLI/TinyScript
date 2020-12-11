package translator;

import com.sun.corba.se.impl.oa.toa.TOA;
import lexer.Token;
import org.apache.commons.lang.NotImplementedException;
import parser.ast.*;
import parser.util.ParseException;
import translator.symbol.Symbol;
import translator.symbol.SymbolTable;

/**
 * 构建翻译器
 * Author : lihuichuan
 * Time   : 2020/12/11
 */
public class Translator {
    public TAProgram translate(ASTNode astNode) throws ParseException {
        TAProgram program = new TAProgram();
        SymbolTable symbolTable = new SymbolTable();

        for (ASTNode child : astNode.getChildren()) {
            translateStmt(program, child, symbolTable);

        }

        return program;
    }

    public void translateStmt(TAProgram program, ASTNode node, SymbolTable symbolTable) throws ParseException {
        switch (node.getType()) {
//            case BLOCK:
//                translateBlock(program, (Block) node, symbolTable);
//                return;
//            case IF_STMT:
//                translateIfStmt(program, (IfStmt) node, symbolTable);
//                return;
            case ASSIGN_STMT:
                translateAssignStmt(program, node, symbolTable);
                return;
            case DECLARE_STMT:
                translateDeclareStmt(program, node, symbolTable);
                return;
//            case FUNCTION_DECLARE_STMT:
//                translateFunctionDeclareStmt(program, node, symbolTable);
//                return;
//            case RETURN_STMT:
//                translateReturnStmt(program, node, symbolTable);
//                return;
//            case CALL_EXPR:
//                translateCallExpr(program, node, symbolTable);
//                return;
        }
        throw new NotImplementedException("Translator not impl. for " + node.getType());
    }

    private void translateAssignStmt(TAProgram program, ASTNode node, SymbolTable symbolTable) throws ParseException {
        // var a = expr
        // 翻译这个Expression的child(1)
        Symbol assigned = symbolTable.createSymbolByLexeme(node.getChild(0).getLexeme());
        ASTNode expr = node.getChild(1);
        Symbol addr = translateExpr(program, expr, symbolTable);
        program.add(new TAInstruction(TAInstructionType.ASSIGN, assigned, "=", addr, null));

    }

    // SDD:
    // E -> E1
    public Symbol translateExpr(
            TAProgram program,
            ASTNode node,
            SymbolTable symbolTable) throws ParseException {
        if (node.isValueType()) {
            Symbol addr = symbolTable.createSymbolByLexeme(node.getLexeme());
            node.setProp("addr", addr);
            return addr;
        } else if (node.getType() == ASTNodeTypes.CALL_EXPR) {
            throw new NotImplementedException("not impl");
//            var addr = translateCallExpr(program, node, symbolTable);
//            node.setProp("addr", addr);
//            return addr;
        } else if (node instanceof Expr) {
            for (ASTNode child : node.getChildren()) {
                translateExpr(program, child, symbolTable);
            }
            if (node.getProp("addr") == null) {
                node.setProp("addr", symbolTable.createVariable());
            }
            TAInstruction instruction = new TAInstruction(
                    TAInstructionType.ASSIGN,
                    (Symbol) (node.getProp("addr")),
                    node.getLexeme().getValue(),
                    (Symbol) (node.getChild(0).getProp("addr")),
                    (Symbol) (node.getChild(1).getProp("addr"))
            );
            program.add(instruction);
            return instruction.getResult();
        }
        throw new NotImplementedException("Unexpected node type :" + node.getType());

    }

    // 翻译 a = 1+2
    private void translateDeclareStmt(TAProgram program, ASTNode node, SymbolTable symbolTable) throws ParseException {
        Token lexeme = node.getChild(0).getLexeme();
        if (symbolTable.exists(lexeme)) {
            throw new ParseException("Syntax Error, Identifier " + lexeme.getValue() + " is already defined.");
        }
        Symbol assigned = symbolTable.createSymbolByLexeme(node.getChild(0).getLexeme());
        ASTNode expr = node.getChild(1);
        Symbol addr = translateExpr(program, expr, symbolTable);
        program.add(new TAInstruction(TAInstructionType.ASSIGN, assigned, "=", addr, null));
    }

    private void translateReturnStmt(TAProgram program, ASTNode node, SymbolTable symbolTable) throws
            ParseException {
        Symbol resultValue = null;
        if (node.getChild(0) != null) {
            resultValue = translateExpr(program, node.getChild(0), symbolTable);
        }
        program.add(new TAInstruction(TAInstructionType.RETURN, null, null, resultValue, null));
    }

}