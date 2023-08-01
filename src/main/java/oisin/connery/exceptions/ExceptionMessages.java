package oisin.connery.exceptions;

import java.math.BigDecimal;

public class ExceptionMessages {
    public static String exponentPowerTooLong(BigDecimal baseNumber,BigDecimal power, int maxLength){
        return "The power on the exponent expression is higher than the maximum processable amount of ".concat(String.valueOf(maxLength)).concat(": ").concat(baseNumber.toPlainString()).concat("^").concat(power.toPlainString());
    }

    public static String wrongCharacterOnAddMinusMethodCheck(char firstChar, char secondChar){
        return "The characters: ".concat(String.valueOf(firstChar)).concat(" and ").concat(String.valueOf(secondChar)).concat(" are not + or - characters and have been passed to the wrong method.");
    }
}
