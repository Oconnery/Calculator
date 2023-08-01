package oisin.connery;

import oisin.connery.exceptions.ExpressionFormatException;
import oisin.connery.operators.*;
import oisin.connery.structures.ExpressionAndIndex;
import oisin.connery.validation.ExpressionFormatValidator;
import org.apache.commons.lang3.StringUtils;

// todo: Could store a Map<Map<>> cache of parentheses symbol location, addition symbol location etc. in validation
//  pass and then just go there instead of looping through expression so many times.
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
        Parentheses parentheses = new Parentheses();
        int expressionLength = expression.length();
        for (int i=1; i<expressionLength; i++){
            if (expression.charAt(i) == parentheses.getClosingSymbol()){
                ExpressionAndIndex expressionInsideParentheses = parentheses.extractSubExpressionFromExpression(expression, i);
                String resolvedExpressionInsideParentheses = performArithmetic(expressionInsideParentheses.getExpression());
                StringBuilder stringBuilder = new StringBuilder(expression);
                stringBuilder.replace(expressionInsideParentheses.getLeftSymbolIndex(), i+1, resolvedExpressionInsideParentheses);
                return evaluateAllParentheses(stringBuilder.toString());
            }
        }
        return expression;
    }

    private String performArithmetic(String expression){
        String postFactorialExpression = calculateOperations(expression, new FactorialOperator());
        String postExponentExpression = calculateOperations(postFactorialExpression, new Exponent());
        String postDivisionMultiplicationExpression = calculateOperations(postExponentExpression, new Division(), new Multiplication());
        return calculateOperations(postDivisionMultiplicationExpression, new Addition(), new Subtraction());
    }

    // summary comment
    private String calculateOperations(String expression, Operator operator){
        int expressionLength = expression.length();
        for (int i=1; i<expressionLength; i++){
            if (expression.charAt(i) == operator.getSymbol()){
                String newExpression = operator.evaluate(expression, i);
                return calculateOperations(newExpression, operator);
            }
        }
        return expression;
    }

    private String calculateOperations(String expression, Operator operatorOne, Operator operatorTwo){
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
