package translator.symbol;

import lexer.Token;

/**
 * Author : lihuichuan
 * Time   : 2020/12/10
 */
public class Symbol {

    // union
    SymbolTable parent;
    // Token 就是对应词法分析的Token
    Token lexeme;
    // label 是只用label会使用
    String label;
    // 为了储存offset 位置
    int offset;
    int layerOffset = 0;
    SymbolType type;

    public Symbol(SymbolType type) {
        this.type = type;
    }

    public static Symbol createAddressSymbol(Token lexeme, int offset) {
        Symbol symbol = new Symbol(SymbolType.ADDRESS_SYMBOL);
        // 词法lexeme
        symbol.lexeme = lexeme;
        symbol.offset = offset;
        return symbol;
    }

    public static Symbol createImmediateSymbol(Token lexeme) {

        // 会存到静态区
        Symbol symbol = new Symbol(SymbolType.IMMEDIATE_SYMBOL);
        symbol.lexeme = lexeme;
        return symbol;
    }

    public static Symbol createLabelSymbol(String label, Token lexeme) {
        Symbol symbol = new Symbol(SymbolType.LABEL_SYMBOL);
        symbol.label = label;
        symbol.lexeme = lexeme;
        return symbol;
    }

    public Symbol copy() {
        Symbol symbol = new Symbol(this.type);
        symbol.lexeme = this.lexeme;
        symbol.label = this.label;
        symbol.offset = this.offset;
        symbol.layerOffset = this.layerOffset;
        symbol.type = this.type;
        return symbol;
    }

    public void setParent(SymbolTable parent) {
        this.parent = parent;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public SymbolType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        if (this.type == SymbolType.LABEL_SYMBOL) {
            return this.label;
        }
        return lexeme.getValue();
    }

    public void setLexeme(Token lexeme) {
        this.lexeme = lexeme;
    }

    public int getOffset() {
        return this.offset;
    }

    public Token getLexeme() {
        return this.lexeme;
    }

    public void setLayerOffset(int offset) {
        this.layerOffset = offset;
    }

    public int getLayerOffset() {
        return this.layerOffset;
    }

    public String getLabel() {
        return this.label;
    }
}


