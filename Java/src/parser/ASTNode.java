package parser;

import lexer.Token;

import java.util.ArrayList;

/**
 * Author : lihuichuan
 * Time   : 2020/12/1
 */

// 抽象语法树
public abstract class ASTNode {
    /* 树 */
    protected ArrayList<ASTNode> children = new ArrayList<>();
    protected ASTNode parent;

    protected ASTNodeTypes type; // 类型


    /* 关键信息 */
    protected Token lexeme; // 词法单元
    protected String label; // 备注(标签)


    public ASTNode() {
    }

    public ASTNode(ASTNodeTypes _type, String _label) {
        this.type = _type;
        this.label = _label;
    }


    public ArrayList<ASTNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ASTNode> children) {
        this.children = children;
    }

    public ASTNode getChild(int index) {
        if (index >= this.children.size()) {
            return null;
        }
        return this.children.get(index);
    }

    public void addChild(ASTNode node) {
        node.parent = this;
        children.add(node);
    }


    public Token getLexeme() {
        return lexeme;
    }

    public void setLexeme(Token lexeme) {
        this.lexeme = lexeme;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
