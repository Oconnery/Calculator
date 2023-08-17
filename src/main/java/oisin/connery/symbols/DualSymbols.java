package oisin.connery.symbols;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class DualSymbols {
    protected char firstSymbol;
    protected SymbolType firstSymbolType;
    protected char secondSymbol;
    protected SymbolType secondSymbolType;
}
