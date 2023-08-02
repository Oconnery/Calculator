package oisin.connery;

import oisin.connery.exceptions.ExpressionFormatException;
import oisin.connery.operators.*;
import oisin.connery.structures.ExpressionAndIndex;
import oisin.connery.validation.ExpressionFormatValidator;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.Map;

import static oisin.connery.operators.SymbolTypes.RIGHT_PARENTHESES;

public class Computer {
    private static Map<SymbolTypes,List<Integer>> operatorLocationsCache;
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
        operatorLocationsCache = ExpressionFormatValidator.validate(expression);
        return performCalculations(expression);
    }

    private static String performCalculations(String expression){
        expression = evaluateAllParentheses(expression);
        return performArithmetic(expression);
    }

    private static String evaluateAllParentheses(String expression) {
         int amountExpressionReducedBy = 0; // this works for parentheses because I know that all the remaining parentheses will be to the right of this one. Make the cache an ordered linkedList if I have to.
        List<Integer> operatorLocationList = operatorLocationsCache.get(RIGHT_PARENTHESES); // so this should work for everything else too? I would have to provide them with the
        for (Integer index : operatorLocationList){
            int expressionLength = expression.length();
            index -= amountExpressionReducedBy;
            expression = evaluateParentheses(expression, index);
            amountExpressionReducedBy += (expressionLength-expression.length());
            //operatorLocationList.remove(index);
        }
        return expression;
    }

    private static String evaluateParentheses(String expression, int index){
        ExpressionAndIndex expressionInsideParentheses = ParenthesesFunctionality.extractSubExpressionFromExpression(expression, index);
        String expressionWithResolvedParentheses = performArithmetic(expressionInsideParentheses.getExpression());
        StringBuilder expressionWithResolvedParenthesesSb = new StringBuilder(expression);
        expressionWithResolvedParenthesesSb.replace(expressionInsideParentheses.getLeftSymbolIndex(), index+1, expressionWithResolvedParentheses);
        return expressionWithResolvedParenthesesSb.toString();
    }

    private static String performArithmetic(String expression){
        String postFactorialExpression = calculateOperations(expression, factorialOperator);
        String postExponentExpression = calculateOperations(postFactorialExpression, exponentOperator);
        String postDivisionMultiplication = calculateOperations(postExponentExpression, divisionOperator, multiplicationOperator);
        return calculateOperations(postDivisionMultiplication, additionOperator, subtractionOperator);
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

//        int amountExpressionReducedBy = 0;
//        List<Integer> operatorLocationList = operatorLocationsCache.get(characterToOperatorType.get(operator.getSymbol()));
//        for (Integer index : operatorLocationList) {
//                int expressionLength = expression.length();
//                // if (index < expressionLength+amountExpressionReducedBy) { // causing other problems. investigate // second pass is a problem because the
//                //index -= amountExpressionReducedBy;
//                if (isInRange(index, expStartIndex, expEndIndex)){
//                    expression = operator.evaluate(expression, index);
//                    operatorLocationList.remove(index);
//
//                    // update expStartIndex and expEndIndex
//                }
//        }
//        return expression;
    }

    private static boolean isInRange(int number, int start, int end){
        return number >= start && number <= end;
    }

    private static String calculateOperations(String expression , Operator operatorOne, Operator operatorTwo){
        // combine the two
        int expressionLength = expression.length();
        for (int i=1; i<expressionLength; i++){
            if (expression.charAt(i) == operatorOne.getSymbol()){
                expression = operatorOne.evaluate(expression, i);
                return calculateOperations(expression, operatorOne, operatorTwo);
            } else if (expression.charAt(i) == operatorTwo.getSymbol()) {
                expression = operatorTwo.evaluate(expression, i);
                return calculateOperations(expression, operatorOne, operatorTwo);
            }
        }
        return expression;
    }
}
