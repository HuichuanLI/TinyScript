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


    /* 关键信息 */
    protected Token lexeme; // 词法单元
    protected String label; // 备注(标签)


    public ASTNode() {
    }


    public ArrayList<ASTNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ASTNode> children) {
        this.children = children;
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
