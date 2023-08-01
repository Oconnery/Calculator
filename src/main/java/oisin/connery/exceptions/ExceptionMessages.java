package oisin.connery.exceptions;

import java.math.BigDecimal;

public class ExceptionMessages {
    public final static String EXPRESSION_ENDING_INCORRECT =  "Expression does not end with a number";
    public final static String EXPRESSION_START_INCORRECT = "Expression does not start with a number, parentheses or a (-) minus or (+) plus sign";
    public final static String EXPRESSION_IS_EMPTY = "Expression is empty or contains only whitespace";
    public final static String EXPRESSION_IS_NULL = "Expression is null";

    public static String exponentPowerTooLong(BigDecimal baseNumber,BigDecimal power, int maxLength){
        return "The power on the exponent expression is higher than the maximum processable amount of ".concat(String.valueOf(maxLength)).concat(": ").concat(baseNumber.toPlainString()).concat("^").concat(power.toPlainString());
    }

    public static String wrongCharacterOnAddMinusMethodCheck(char firstChar, char secondChar){
        return "The characters: ".concat(String.valueOf(firstChar)).concat(" and ").concat(String.valueOf(secondChar)).concat(" are not + or - characters and have been passed to the wrong method.");
    }

    public static String closingBracketHasNoOpeningBracket(int indexOfOpeningBracket){
        return "There is no opening bracket for the closing bracket in the expression at index".concat(String.valueOf(indexOfOpeningBracket));
    }

    public static String unevenParenthesesNumber(int leftParenthesesCount, int rightParenthesesCount){
        throw new ExpressionFormatException("Expression has an uneven number of left parentheses brackets: ".concat(String.valueOf(leftParenthesesCount)).concat(" and right parentheses brackets: ").concat(String.valueOf(rightParenthesesCount)));
    }

    public static String unallowedChar(char character, int index) {
        throw new ExpressionFormatException("Expression contains a character which is not allowed: ".concat(String.valueOf(character)).concat(" at index position ").concat(String.valueOf(index)).concat(" in the formula"));
    }

    public static String twoCharsInARowNotAllowed(char character, char characterTwo, int index) {
        throw new ExpressionFormatException("Expression has two operators in a row which are not allowed to be in a row: "
                .concat(String.valueOf(character)).concat(" and ").concat(String.valueOf(characterTwo))
                .concat(" at index positions of ").concat(String.valueOf(index - 1)).concat(" and ")
                .concat(String.valueOf(index)).concat(" in the formula"));
    }
}
