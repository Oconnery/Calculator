package oisin.connery.symbols;

import lombok.Getter;

@Getter
public abstract class Symbol {
    public Symbol(char symbol, SymbolType symbolType) {
        this.symbol = symbol;
        this.symbolType = symbolType;
    }

    protected char symbol;
    protected SymbolType symbolType;
}
