package parser.ast;

/**
 * Author : lihuichuan
 * Time   : 2020/12/3
 */
public class Block extends Stmt{
    public Block() {
        super(ASTNodeTypes.BLOCK, "block");
    }
}
