package parser;

/**
 * Author : lihuichuan
 * Time   : 2020/12/3
 */
public abstract class Stmt extends ASTNode{
    public Stmt(ASTNodeTypes _type, String _label) {
        super(_type, _label);
    }
}
