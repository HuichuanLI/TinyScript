package parser.ast;

/**
 * Author : lihuichuan
 * Time   : 2020/12/3
 */
public class DeclareStmt extends Stmt{
    public DeclareStmt() {
        super(ASTNodeTypes.DECLARE_STMT, "declare");
    }
}
