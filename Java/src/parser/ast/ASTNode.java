package parser.ast;

import org.apache.commons.lang.StringUtils;

import lexer.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

    private HashMap<String, Object> _props = new HashMap<>();


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


    public void setType(ASTNodeTypes type) {
        this.type = type;
    }

    public ASTNodeTypes getType() {
        return type;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void print(int indent) {
        if (indent == 0) {
            System.out.println("print:" + this);
        }

        System.out.println(StringUtils.leftPad(" ", indent * 2) + label);
        for (ASTNode child : children) {
            child.print(indent + 1);
        }
    }


    public boolean isValueType() {
        return this.type == ASTNodeTypes.VARIABLE || this.type == ASTNodeTypes.SCALAR;
    }

    public Object getProp(String key) {
        if (!this._props.containsKey(key)) {
            return null;
        }
        return this._props.get(key);
    }

    public void setProp(String key, Object value) {
        this._props.put(key, value);
    }
}
