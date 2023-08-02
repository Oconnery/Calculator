package oisin.connery.validation;

import oisin.connery.exceptions.ExceptionMessages;
import oisin.connery.exceptions.ExpressionFormatException;
import oisin.connery.operators.SymbolTypes;

import java.util.*;

import static oisin.connery.cfg.Translations.characterToOperatorType;

public class ExpressionFormatValidator {
    private static final char [] alwaysAllowedCharacters = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char [] allowedCharactersOneInARowOnly = {'.','*', '/', '^', 'E', '!'};
    private static final char [] plusAndMinusCharacters = {'+', '-'};
    private static final char [] parenthesesCharacters = {'(', ')'};

    public static EnumMap<SymbolTypes, List<Integer>> validate(String expression) throws ExpressionFormatException {
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
        return cacheOperatorsInExpression(expression);
    }

    private static boolean startsWithANumberOrParenthesesOrFactorial(String expression){
        char firstChar = expression.charAt(0);
        return firstChar == '(' || firstChar == plusAndMinusCharacters[0] ||  firstChar == plusAndMinusCharacters[1] || firstChar >= '0' && firstChar<= '9' || firstChar == '!';
    }

    private static boolean endsWithANumberOrParenthesesOrFactorial(String expression){
        char firstChar = expression.charAt(expression.length()-1);
        return firstChar == ')' || firstChar >= '0' && firstChar<= '9' || firstChar == '!';
    }

    private static EnumMap<SymbolTypes, List<Integer>> cacheOperatorsInExpression(String expression) throws ExpressionFormatException{
        EnumMap<SymbolTypes, List<Integer>> operatorLocationsCache = new EnumMap<>(SymbolTypes.class);

        for (SymbolTypes symbolTypes : SymbolTypes.values()) {
            operatorLocationsCache.put(symbolTypes, new ArrayList<>());
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
                    if (characterToOperatorType.containsKey(c))
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

    private static void ifOperatorThenAddToCache(EnumMap<SymbolTypes, List<Integer>> operatorLocationsCache, char c, int indexToAdd){
        if (characterToOperatorType.containsKey(c))
            operatorLocationsCache.get(characterToOperatorType.get(c)).add(indexToAdd);
    }

    private static void addOperatorToCache(EnumMap<SymbolTypes, List<Integer>> operatorLocationsCache, char c, int indexToAdd){
        operatorLocationsCache.get(characterToOperatorType.get(c)).add(indexToAdd);
    }
}
