package oisin.connery.validation;

import oisin.connery.exceptions.ExpressionFormatException;

public class ExpressionFormatValidator {
    private static final char [] alwaysAllowedCharacters = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char [] allowedCharactersOneInARowOnly = {'.','*', '/', '^', 'E'};
    private static final char [] plusAndMinusCharacters = {'+', '-'};
    private static final char [] parenthesesCharacters = {'(', ')'};

    public static void validate(String expression) throws ExpressionFormatException {
        if (expression == null)
            throw new ExpressionFormatException("Expression is null");
        if (expression.isBlank()){
            throw new ExpressionFormatException("Expression is empty or contains only whitespace");
        }
        if (!startsWithANumberOrParentheses(expression)){
            throw new ExpressionFormatException("Expression does not start with a number");
        }
        if (!endsWithANumberOrParentheses(expression)){
            throw new ExpressionFormatException("Expression does not end with a number");
        }
        throwExceptionOnIllegitimateText(expression);
    }

    private static boolean startsWithANumberOrParentheses(String expression){
        char firstChar = expression.charAt(0);
        return firstChar == '(' || firstChar >= '0' && firstChar<= '9';
    }

    private static boolean endsWithANumberOrParentheses(String expression){
        char firstChar = expression.charAt(expression.length()-1);
        return firstChar == ')' || firstChar >= '0' && firstChar<= '9';
    }

    private static void throwExceptionOnIllegitimateText(String expression) throws ExpressionFormatException{
        boolean lastCharWasOperator = false;
        boolean lastCharWasPlusOrMinus = false;
        int leftParenthesesCount = 0;
        int rightParenthesesCount = 0;
        int i =-1;

        outerloop:
        for (char c: expression.toCharArray()) {
            i++;
            for (char allowedChar: alwaysAllowedCharacters) {
                if (c == allowedChar) {
                    lastCharWasOperator = false;
                    lastCharWasPlusOrMinus = false;
                    continue outerloop;
                }
            }
            for (char charAllowedOnceInARowOnly: allowedCharactersOneInARowOnly) {
                if (c == charAllowedOnceInARowOnly) {
                    if (lastCharWasOperator || lastCharWasPlusOrMinus){
                        throw new ExpressionFormatException("Expression has two operators in a row which are not allowed to be in a row: "
                                .concat(String.valueOf(c)).concat(" and ").concat(String.valueOf(charAllowedOnceInARowOnly))
                                .concat(" at index positions of ").concat(String.valueOf(i-1)).concat(" and ").concat(String.valueOf(i)).concat(" in the formula"));
                    } else {
                        lastCharWasOperator = true;
                        continue outerloop;
                    }
                }
            }
            for (char plusOrMinusChar: plusAndMinusCharacters){
                if (c == plusOrMinusChar) {
                    lastCharWasPlusOrMinus = true;
                    lastCharWasOperator = false;
                    continue outerloop;
                }
            }
            if (c == parenthesesCharacters[0]){
                leftParenthesesCount++;
                lastCharWasPlusOrMinus = false;
                lastCharWasOperator = false;
                continue outerloop;
            } else if (c == parenthesesCharacters[1]){
                rightParenthesesCount++;
                lastCharWasPlusOrMinus = false;
                lastCharWasOperator = false;
                continue outerloop;
            }
            throw new ExpressionFormatException("Expression has a character which is not allowed: ".concat(String.valueOf(c)).concat(" at index position ").concat(String.valueOf(i)).concat(" in the formula"));
        }
        if (leftParenthesesCount!=rightParenthesesCount){
            throw new ExpressionFormatException("Expression has an uneven number of left parentheses brackets: ".concat(String.valueOf(leftParenthesesCount)).concat(" and right parentheses brackets: ").concat(String.valueOf(rightParenthesesCount)));
        }
    }
}
