package oisin.connery.cfg;

import oisin.connery.operators.OperatorTypes;

import java.util.HashMap;
import java.util.Map;

public class Translations {
    public static final Map<Character, OperatorTypes> characterToOperatorType;

    static {
        characterToOperatorType = new HashMap<>(); // todo: put into config file
        characterToOperatorType.put('*', OperatorTypes.MULTIPLICATION);
        characterToOperatorType.put('/', OperatorTypes.DIVISION);
        characterToOperatorType.put('^', OperatorTypes.EXPONENT);
        characterToOperatorType.put('!', OperatorTypes.FACTORIAL);
        characterToOperatorType.put('+', OperatorTypes.ADDITION);
        characterToOperatorType.put('-', OperatorTypes.SUBTRACTION);
        characterToOperatorType.put(')', OperatorTypes.RIGHT_PARENTHESES);
    }
}
