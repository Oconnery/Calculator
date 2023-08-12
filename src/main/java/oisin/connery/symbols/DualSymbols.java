package oisin.connery.symbols;

import lombok.Getter;

@Getter
public abstract class DualSymbols {
    public DualSymbols(char firstSymbol, SymbolType firstSymbolType, char secondSymbol, SymbolType secondSymbolType) {
        this.firstSymbol = firstSymbol;
        this.firstSymbolType = firstSymbolType;
        this.secondSymbol = secondSymbol;
        this.secondSymbolType = secondSymbolType;
    }

    protected char firstSymbol;
    protected SymbolType firstSymbolType;
    protected char secondSymbol;
    protected SymbolType secondSymbolType;
}
