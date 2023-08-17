package oisin.connery.operators;

import oisin.connery.symbols.Symbol;
import oisin.connery.symbols.SymbolType;


public abstract class Operator extends Symbol {

    public Operator(char symbol, SymbolType symbolType) {
        super(symbol, symbolType);
    }

    public abstract String evaluate(String expression, int positionInExpression);
}
