package parser.ast;

/**
 * Author : lihuichuan
 * Time   : 2020/12/3
 */
public class FunctionDeclareStmt extends Stmt {
    public FunctionDeclareStmt() {
        super( ASTNodeTypes.FUNCTION_DECLARE_STMT, "func");
    }
}
