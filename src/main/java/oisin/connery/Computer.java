package oisin.connery;

import oisin.connery.exceptions.ExpressionFormatException;
import oisin.connery.operators.*;
import oisin.connery.structures.ExpressionAndIndex;
import oisin.connery.validation.ExpressionFormatValidator;
import org.apache.commons.lang3.StringUtils;

// todo: Could store a Map<Map<>> cache of parentheses symbol location, addition symbol location etc. in validation
//  pass and then just go there instead of looping through expression so many times.
public class Computer {
    //Map<OperatorType, List<String>> operatorLocationsMap;
    // References are static.
    private static final AdditionOperator additionOperator = new AdditionOperator();
    private static final SubtractionOperator subtractionOperator = new SubtractionOperator();
    private static final DivisionOperator divisionOperator = new DivisionOperator();
    private static final MultiplicationOperator multiplicationOperator = new MultiplicationOperator();
    private static final FactorialOperator factorialOperator = new FactorialOperator();
    private static final ExponentOperator exponentOperator = new ExponentOperator();

    public static String calculate(String expression) throws ExpressionFormatException {
        expression = StringUtils.deleteWhitespace(expression);
        ExpressionFormatValidator.validate(expression);
        return performCalculations(expression);
    }

    private static String performCalculations(String expression){
        String postParenthesesExpression = evaluateAllParentheses(expression);
        return performArithmetic(postParenthesesExpression);
    }

    private static String evaluateAllParentheses(String expression){
        int expressionLength = expression.length();
        for (int i=1; i<expressionLength; i++){
            if (expression.charAt(i) == ParenthesesFunctionality.CLOSING_SYMBOL){
                ExpressionAndIndex expressionInsideParentheses = ParenthesesFunctionality.extractSubExpressionFromExpression(expression, i);
                String resolvedExpressionInsideParentheses = performArithmetic(expressionInsideParentheses.getExpression());
                StringBuilder stringBuilder = new StringBuilder(expression);
                stringBuilder.replace(expressionInsideParentheses.getLeftSymbolIndex(), i+1, resolvedExpressionInsideParentheses);
                return evaluateAllParentheses(stringBuilder.toString());
            }
        }
        return expression;
    }

    private static String performArithmetic(String expression){
        String postFactorialExpression = calculateOperations(expression, factorialOperator);
        String postExponentExpression = calculateOperations(postFactorialExpression, exponentOperator);
        String postDivisionMultiplicationExpression = calculateOperations(postExponentExpression, divisionOperator, multiplicationOperator);
        return calculateOperations(postDivisionMultiplicationExpression, additionOperator, subtractionOperator);
    }

    // summary comment
    private static String calculateOperations(String expression, Operator operator){
        int expressionLength = expression.length();
        for (int i=1; i<expressionLength; i++){
            if (expression.charAt(i) == operator.getSymbol()){
                String newExpression = operator.evaluate(expression, i);
                return calculateOperations(newExpression, operator);
            }
        }
        return expression;
    }

    private static String calculateOperations(String expression, Operator operatorOne, Operator operatorTwo){
        int expressionLength = expression.length();
        for (int i=1; i<expressionLength; i++){
            if (expression.charAt(i) == operatorOne.getSymbol()){
                String newExpression = operatorOne.evaluate(expression, i);
                return calculateOperations(newExpression, operatorOne, operatorTwo);
            } else if (expression.charAt(i) == operatorTwo.getSymbol()) {
                String newExpression = operatorTwo.evaluate(expression, i);
                return calculateOperations(newExpression, operatorOne, operatorTwo);
            }
        }
        return expression;
    }
}
