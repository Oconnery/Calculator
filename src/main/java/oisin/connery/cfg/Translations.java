package oisin.connery.cfg;

import oisin.connery.operators.SymbolTypes;

import java.util.HashMap;
import java.util.Map;

public class Translations {
    public static final Map<Character, SymbolTypes> characterToOperatorType;

    static {
        characterToOperatorType = new HashMap<>();
        characterToOperatorType.put('*', SymbolTypes.MULTIPLICATION);
        characterToOperatorType.put('/', SymbolTypes.DIVISION);
        characterToOperatorType.put('^', SymbolTypes.EXPONENT);
        characterToOperatorType.put('!', SymbolTypes.FACTORIAL);
        characterToOperatorType.put('+', SymbolTypes.ADDITION);
        characterToOperatorType.put('-', SymbolTypes.SUBTRACTION);
        characterToOperatorType.put(')', SymbolTypes.RIGHT_PARENTHESES);
    }
}
