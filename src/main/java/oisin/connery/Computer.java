package oisin.connery;

import oisin.connery.exceptions.ExpressionFormatException;
import oisin.connery.operators.*;
import oisin.connery.structures.ExpressionAndIndex;
import oisin.connery.validation.ExpressionFormatValidator;
import org.apache.commons.lang3.StringUtils;

public class Computer {
    public String calculateExpression(String expression) throws ExpressionFormatException {
        expression = StringUtils.deleteWhitespace(expression);
        ExpressionFormatValidator.validate(expression);
        return performCalculations(expression);
    }

    private String performCalculations(String expression){
        String postParenthesesExpression = evaluateAllParentheses(expression);
        return performArithmetic(postParenthesesExpression);
    }

    private String evaluateAllParentheses(String expression){
        ParenthesesOperator parenthesesOperator = new ParenthesesOperator();
        int expressionLength = expression.length();
        for (int i=1; i<expressionLength; i++){
            if (expression.charAt(i) == parenthesesOperator.getClosingSymbol()){
                ExpressionAndIndex expressionInsideParentheses = parenthesesOperator.evaluateSingleParentheses(expression, i);
                String resolvedExpressionInsideParentheses = performArithmetic(expressionInsideParentheses.getExpression());
                StringBuilder stringBuilder = new StringBuilder(expression);
                stringBuilder.replace(expressionInsideParentheses.getLeftSymbolIndex(), i+1, resolvedExpressionInsideParentheses);
                return evaluateAllParentheses(stringBuilder.toString());
            }
        }
        return expression;
    }

    private String performArithmetic(String expression){
        String postExponentExpression = calculateOperations(expression, new Exponent()); // these new() methods will be done multiple times for parentheses
        String postDivisionMultiplicationExpression = calculateOperations(postExponentExpression, new Division(), new Multiplication());
        return calculateOperations(postDivisionMultiplicationExpression, new Addition(), new Subtraction());
    }

    // summary comment
    private String calculateOperations(String expression, ArithmeticOperator arithmeticOperator){
        int expressionLength = expression.length();
        for (int i=1; i<expressionLength; i++){
            if (expression.charAt(i) == arithmeticOperator.getSymbol()){
                String newExpression = arithmeticOperator.evaluateOperator(expression, i);
                return calculateOperations(newExpression, arithmeticOperator);
            }
        }
        return expression;
    }

    /*
        write summary comment here about same as above but two with same precedence
    */
    private String calculateOperations(String expression, ArithmeticOperator arithmeticOperatorOne, ArithmeticOperator arithmeticOperatorTwo){
        int expressionLength = expression.length();
        for (int i=1; i<expressionLength; i++){
            if (expression.charAt(i) == arithmeticOperatorOne.getSymbol()){
                String newExpression = arithmeticOperatorOne.evaluateOperator(expression, i);
                return calculateOperations(newExpression, arithmeticOperatorOne, arithmeticOperatorTwo);
            } else if (expression.charAt(i) == arithmeticOperatorTwo.getSymbol()) {
                String newExpression = arithmeticOperatorTwo.evaluateOperator(expression, i);
                return calculateOperations(newExpression, arithmeticOperatorOne, arithmeticOperatorTwo);
            }
        }
        return expression;
    }
}
