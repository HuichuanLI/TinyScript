package translator.symbol;

import lexer.Token;
import lexer.TokenType;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Author : lihuichuan
 * Time   : 2020/12/10
 */

// 符号表 ： 为了存储源代码的数据类型 ： 树+哈希表
public class SymbolTable {

    // parent 的符号表
    private SymbolTable parent = null;
    private ArrayList<SymbolTable> children;
    // 当前的符号表
    private ArrayList<Symbol> symbols;

    // 为了创建Expression的id
    private int tempIndex = 0;
    // var a = 1
    // var b = 2 offset 的位置
    private int offsetIndex = 0;
    // 当前节点
    private int level = 0;

    public SymbolTable() {
        this.children = new ArrayList<>();
        this.symbols = new ArrayList<>();
    }


    public void addSymbol(Symbol symbol) {
        this.symbols.add(symbol);
        symbol.setParent(this);
    }


    public boolean exists(Token lexeme) {
        Optional<Symbol> _symbol = this.symbols.stream().filter(x -> x.lexeme.getValue().equals(lexeme.getValue())).findFirst();
        if (_symbol.isPresent()) {
            return true;
        }
        if (this.parent != null) {
            return this.parent.exists(lexeme);
        }
        return false;
    }

    public Symbol cloneFromSymbolTree(Token lexeme, int layerOffset) {
        Optional<Symbol> _symbol = this.symbols.stream()
                .filter(x -> x.lexeme.getValue().equals(lexeme.getValue()))
                .findFirst();
        if (_symbol.isPresent()) {

            Symbol symbol = _symbol.get().copy();
            symbol.setLayerOffset(layerOffset);
            return symbol;
        }
        if (this.parent != null) {
            return this.parent.cloneFromSymbolTree(lexeme, layerOffset + 1);
        }
        return null;
    }

    public Symbol createSymbolByLexeme(Token lexeme) {
        Symbol symbol = null;
        if (lexeme.isScalar()) {
            symbol = Symbol.createImmediateSymbol(lexeme);
            this.addSymbol(symbol);
        } else {
            Optional<Symbol> _symbol = this.symbols.stream().filter(x -> x.getLexeme().getValue().equals(lexeme.getValue())).findFirst();
            if (!_symbol.isPresent()) {
                symbol = cloneFromSymbolTree(lexeme, 0);
                if (symbol == null) {
                    symbol = Symbol.createAddressSymbol(lexeme, this.offsetIndex++);
                }
                this.addSymbol(symbol);
            } else {
                symbol = _symbol.get();
            }

        }
        return symbol;
    }

    public Symbol createVariable() {
        Token lexeme = new Token(TokenType.VARIABLE, "p" + this.tempIndex++);
        Symbol symbol = Symbol.createAddressSymbol(lexeme, this.offsetIndex++);
        this.addSymbol(symbol);
        return symbol;
    }

    public void addChild(SymbolTable child) {
        child.parent = this;
        child.level = this.level + 1;
        this.children.add(child);
    }

    public int localSize() {
        return this.offsetIndex;
    }

    public ArrayList<Symbol> getSymbols() {
        return this.symbols;
    }

    public ArrayList<SymbolTable> getChildren() {
        return this.children;
    }

    // 函数的函数名
    public void createLabel(String label, Token lexeme) {
        Symbol labelSymbol = Symbol.createLabelSymbol(label, lexeme);
        this.addSymbol(labelSymbol);

    }
}