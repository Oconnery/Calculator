package oisin.connery;

import oisin.connery.exceptions.ExpressionFormatException;
import oisin.connery.operators.*;
import oisin.connery.structures.ExpressionAndIndex;
import oisin.connery.validation.ExpressionFormatValidator;
import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;
import java.util.List;
import java.util.Map;

public class Computer {
    private static final AdditionOperator additionOperator;
    private static final SubtractionOperator subtractionOperator;
    private static final DivisionOperator divisionOperator;
    private static final MultiplicationOperator multiplicationOperator;
    private static final FactorialOperator factorialOperator;
    private static final ExponentOperator exponentOperator;

    static {
        additionOperator = new AdditionOperator();
        subtractionOperator = new SubtractionOperator();
        divisionOperator = new DivisionOperator();
        multiplicationOperator = new MultiplicationOperator();
        factorialOperator = new FactorialOperator();
        exponentOperator = new ExponentOperator();
    }

    public static String calculate(String expression) throws ExpressionFormatException {
        expression = StringUtils.deleteWhitespace(expression);
        Map<OperatorType, List<Integer>> operatorLocationsCache = ExpressionFormatValidator.validate(expression);
        return performCalculations(expression, operatorLocationsCache);
    }

    private static String performCalculations(String expression, Map<OperatorType, List<Integer>> operatorLocationsCache){
        String postParenthesesExpression = evaluateAllParentheses(expression, operatorLocationsCache);
        return performArithmetic(postParenthesesExpression);
    }

    private static String evaluateAllParentheses(String expression, Map<OperatorType,List<Integer>> operatorLocationsCache) {
        int amountExpressionReducedBy =0;
        for (Integer index : operatorLocationsCache.get(OperatorType.RIGHT_PARENTHESES)){
            index -= amountExpressionReducedBy;
            Pair<String, Integer> newExpressionAndNewIndex = evaluateParentheses(expression, index);
            expression = newExpressionAndNewIndex.getValue0();
            amountExpressionReducedBy += newExpressionAndNewIndex.getValue1();
        }
        return expression;
    }

    private static Pair<String, Integer> evaluateParentheses(String expression, int index){
        ExpressionAndIndex expressionInsideParentheses = ParenthesesFunctionality.extractSubExpressionFromExpression(expression, index);
        String resolvedExpressionInsideParentheses = performArithmetic(expressionInsideParentheses.getExpression());
        StringBuilder parenthesesResolvedStringBuilder = new StringBuilder(expression);
        parenthesesResolvedStringBuilder.replace(expressionInsideParentheses.getLeftSymbolIndex(), index+1, resolvedExpressionInsideParentheses);
        int expressionLengthChange = expression.length() - parenthesesResolvedStringBuilder.length();
        return Pair.with(parenthesesResolvedStringBuilder.toString(), expressionLengthChange);
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
