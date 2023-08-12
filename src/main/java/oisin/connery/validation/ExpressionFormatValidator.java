package oisin.connery.validation;

import oisin.connery.exceptions.ExceptionMessages;
import oisin.connery.exceptions.ExpressionFormatException;
import oisin.connery.symbols.SymbolType;

import java.util.*;

import static oisin.connery.symbols.SymbolType.characterToSymbolType;

public class ExpressionFormatValidator {
    private static final char [] alwaysAllowedCharacters = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char [] allowedCharactersOneInARowOnly = {'.','*', '/', '^', 'E', '!'};
    private static final char [] plusAndMinusCharacters = {'+', '-'};
    private static final char [] parenthesesCharacters = {'(', ')'};

    /**
     * Validates the formatting of the given expression.
     * Creates and returns a cache of the operators in the given expression.
     * @param expression
     * @return operator cache
     * @throws ExpressionFormatException Expression must be formatted correctly.
     */
    public static EnumMap<SymbolType, List<Integer>> validateExpressionAndCreateOperatorCache(String expression) throws ExpressionFormatException {
        if (expression == null)
            throw new ExpressionFormatException(ExceptionMessages.EXPRESSION_IS_NULL);
        if (expression.isBlank()){
            throw new ExpressionFormatException(ExceptionMessages.EXPRESSION_IS_EMPTY);
        }
        if (!startsWithANumberOrParenthesesOrFactorial(expression)){
            throw new ExpressionFormatException(ExceptionMessages.EXPRESSION_START_INCORRECT);
        }
        if (!endsWithANumberOrParenthesesOrFactorial(expression)){
            throw new ExpressionFormatException(ExceptionMessages.EXPRESSION_ENDING_INCORRECT);
        }
        return cacheOperatorsInExpressionAndValidateLegitimacy(expression);
    }

    private static boolean startsWithANumberOrParenthesesOrFactorial(String expression){
        char firstChar = expression.charAt(0);
        return firstChar == '(' || firstChar == plusAndMinusCharacters[0] ||  firstChar == plusAndMinusCharacters[1] || firstChar >= '0' && firstChar<= '9' || firstChar == '!';
    }

    private static boolean endsWithANumberOrParenthesesOrFactorial(String expression){
        char firstChar = expression.charAt(expression.length()-1);
        return firstChar == ')' || firstChar >= '0' && firstChar<= '9' || firstChar == '!';
    }

    private static EnumMap<SymbolType, List<Integer>> cacheOperatorsInExpressionAndValidateLegitimacy(String expression) throws ExpressionFormatException{
        EnumMap<SymbolType, List<Integer>> operatorLocationsCache = new EnumMap<>(SymbolType.class);

        for (SymbolType symbolType : SymbolType.values()) {
            operatorLocationsCache.put(symbolType, new ArrayList<>());
        }

        boolean lastCharWasOperator = false;
        boolean lastCharWasPlusOrMinus = false;
        int leftParenthesesCount = 0;
        int rightParenthesesCount = 0;
        int i =-1;

        outer_loop:
        for (char c: expression.toCharArray()) {
            i++;
            for (char allowedChar: alwaysAllowedCharacters) {
                if (c == allowedChar) {
                    lastCharWasOperator = false;
                    lastCharWasPlusOrMinus = false;
                    continue outer_loop;
                }
            }
            for (char charAllowedOnceInARowOnly: allowedCharactersOneInARowOnly) {
                if (c == charAllowedOnceInARowOnly) {
                    if (characterToSymbolType.containsKey(c))
                        ifOperatorThenAddToCache(operatorLocationsCache, c, i);
                    if (lastCharWasOperator || lastCharWasPlusOrMinus){
                        throw new ExpressionFormatException(ExceptionMessages.twoCharsInARowNotAllowed(c, i));
                    } else {
                        lastCharWasOperator = true;
                        continue outer_loop;
                    }
                }
            }
            for (char plusOrMinusChar: plusAndMinusCharacters){
                if (c == plusOrMinusChar) {
                    addOperatorToCache(operatorLocationsCache, c, i);
                    lastCharWasPlusOrMinus = true;
                    lastCharWasOperator = false;
                    continue outer_loop;
                }
            }
            if (c == parenthesesCharacters[0]){
                leftParenthesesCount++;
                lastCharWasPlusOrMinus = false;
                lastCharWasOperator = false;
                continue outer_loop;
            } else if (c == parenthesesCharacters[1]){
                addOperatorToCache(operatorLocationsCache, c, i);
                rightParenthesesCount++;
                lastCharWasPlusOrMinus = false;
                lastCharWasOperator = false;
                continue outer_loop;
            }
            throw new ExpressionFormatException(ExceptionMessages.disallowedChar(c,i));
        }
        if (leftParenthesesCount!=rightParenthesesCount){
            throw new ExpressionFormatException(ExceptionMessages.unevenParenthesesNumber(leftParenthesesCount, rightParenthesesCount));
        }
        return operatorLocationsCache;
    }

    private static void ifOperatorThenAddToCache(EnumMap<SymbolType, List<Integer>> operatorLocationsCache, char c, int indexToAdd){
        if (characterToSymbolType.containsKey(c))
            operatorLocationsCache.get(characterToSymbolType.get(c)).add(indexToAdd);
    }

    private static void addOperatorToCache(EnumMap<SymbolType, List<Integer>> operatorLocationsCache, char c, int indexToAdd){
        operatorLocationsCache.get(characterToSymbolType.get(c)).add(indexToAdd);
    }
}
