package oisin.connery.symbols;

import java.util.HashMap;
import java.util.Map;

public enum SymbolType {
    ADDITION('+'),
    SUBTRACTION('-'),
    MULTIPLICATION('*'),
    DIVISION('/'),
    EXPONENT('^'),
    FACTORIAL('!'),
    LEFT_PARENTHESES('('),
    RIGHT_PARENTHESES(')');

    public final char symbol;
    public static final Map<Character, SymbolType> characterToSymbolType;

    static {
        characterToSymbolType = new HashMap<>();
        characterToSymbolType.put(SymbolType.MULTIPLICATION.symbol, SymbolType.MULTIPLICATION);
        characterToSymbolType.put(SymbolType.DIVISION.symbol, SymbolType.DIVISION);
        characterToSymbolType.put(SymbolType.EXPONENT.symbol, SymbolType.EXPONENT);
        characterToSymbolType.put(SymbolType.FACTORIAL.symbol, SymbolType.FACTORIAL);
        characterToSymbolType.put(SymbolType.ADDITION.symbol, SymbolType.ADDITION);
        characterToSymbolType.put(SymbolType.SUBTRACTION.symbol, SymbolType.SUBTRACTION);
        characterToSymbolType.put(SymbolType.RIGHT_PARENTHESES.symbol, SymbolType.RIGHT_PARENTHESES);
    }

    SymbolType(char symbol) {
        this.symbol = symbol;
    }
}