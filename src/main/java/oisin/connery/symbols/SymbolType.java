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
    public static final Map<Character, SymbolType> operatorCharacterToSymbolType;

    static {
        operatorCharacterToSymbolType = new HashMap<>();
        operatorCharacterToSymbolType.put(SymbolType.MULTIPLICATION.symbol, SymbolType.MULTIPLICATION);
        operatorCharacterToSymbolType.put(SymbolType.DIVISION.symbol, SymbolType.DIVISION);
        operatorCharacterToSymbolType.put(SymbolType.EXPONENT.symbol, SymbolType.EXPONENT);
        operatorCharacterToSymbolType.put(SymbolType.FACTORIAL.symbol, SymbolType.FACTORIAL);
        operatorCharacterToSymbolType.put(SymbolType.ADDITION.symbol, SymbolType.ADDITION);
        operatorCharacterToSymbolType.put(SymbolType.SUBTRACTION.symbol, SymbolType.SUBTRACTION);
        operatorCharacterToSymbolType.put(SymbolType.RIGHT_PARENTHESES.symbol, SymbolType.RIGHT_PARENTHESES);
    }

    SymbolType(char symbol) {
        this.symbol = symbol;
    }
}