package translator.symbol;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Author : lihuichuan
 * Time   : 2020/12/10
 */
// 静态表 常量表 实现HASH表 为了映射到内存
public class StaticSymbolTable {
    private Hashtable<String, Symbol> offsetMap;
    private int offsetCounter = 0;
    private ArrayList<Symbol> symbols;

    public StaticSymbolTable() {
        symbols = new ArrayList<>();
        offsetMap = new Hashtable<>();
    }

    public void add(Symbol symbol) {
        String lexval = symbol.getLexeme().getValue();
        if (!offsetMap.containsKey(lexval)) {
            offsetMap.put(lexval, symbol);
            symbol.setOffset(offsetCounter++);
            symbols.add(symbol);
        } else {
            Symbol sameSymbol = offsetMap.get(lexval);
            symbol.setOffset(sameSymbol.offset);
        }
    }

    public int size() {
        return this.symbols.size();
    }

    @Override
    public String toString() {

        ArrayList list = new ArrayList<String>();
        for (int i = 0; i < this.symbols.size(); i++) {
            list.add(i + ":" + this.symbols.get(i).toString());
        }
        return StringUtils.join(list, "\n");
    }

    public ArrayList<Symbol> getSymbols() {
        return this.symbols;
    }
}
