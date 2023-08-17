package oisin.connery.symbols;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Symbol {
    protected char symbol;
    protected SymbolType symbolType;
}
