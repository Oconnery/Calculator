package oisin.connery;

import oisin.connery.exceptions.ExpressionFormatException;
import oisin.connery.operators.*;
import oisin.connery.structures.ExpressionAndIndex;
import oisin.connery.validation.ExpressionFormatValidator;
import org.apache.commons.lang3.StringUtils;

public class Computer {
    public String evaluateExpression(String expression) throws ExpressionFormatException { // could validate with annotation on expression here with @
        expression = StringUtils.deleteWhitespace(expression);
        ExpressionFormatValidator.validate(expression);
        // todo: validate expression // starts with number or a +-. is no two operations in a row that don't include + or - // no letters
        return performEvaluations(expression);
    }

    private String performEvaluations(String expression){
        String postParenthesesExpression = evaluateAllParentheses(expression);
        return performArithmeticEvaluations(postParenthesesExpression);
    }

    private String evaluateAllParentheses(String expression){
        ParenthesesOperator parenthesesOperator = new ParenthesesOperator();
        int expressionLength = expression.length();
        for (int i=1; i<expressionLength; i++){
            if (expression.charAt(i) == parenthesesOperator.getClosingSymbol()){
                ExpressionAndIndex expressionInsideParentheses = parenthesesOperator.evaluateSingleParentheses(expression, i);
                String resolvedExpressionInsideParentheses = performArithmeticEvaluations(expressionInsideParentheses.getExpression());
                StringBuilder stringBuilder = new StringBuilder(expression);
                stringBuilder.replace(expressionInsideParentheses.getLeftSymbolIndex(), i+1, resolvedExpressionInsideParentheses);
                return evaluateAllParentheses(stringBuilder.toString());
            }
        }
        return expression;
    }

    private String performArithmeticEvaluations(String expression){
        String postExponentExpression = evaluateOperations(expression, new Exponent()); // these new() methods will be done multiple times for parentheses
        String postDivisionMultiplicationExpression = evaluateOperations(postExponentExpression, new Division(), new Multiplication());
        return evaluateOperations(postDivisionMultiplicationExpression, new Addition(), new Subtraction());
    }

    // summary comment
    private String evaluateOperations(String expression, ArithmeticOperator arithmeticOperator){
        int expressionLength = expression.length();
        for (int i=1; i<expressionLength; i++){
            if (expression.charAt(i) == arithmeticOperator.getSymbol()){
                String newExpression = arithmeticOperator.evaluateOperator(expression, i);
                return evaluateOperations(newExpression, arithmeticOperator);
            }
        }
        return expression;
    }

    /*
        write summary comment here about same as above but two with same precedence
    */
    private String evaluateOperations(String expression, ArithmeticOperator arithmeticOperatorOne, ArithmeticOperator arithmeticOperatorTwo){
        int expressionLength = expression.length();
        for (int i=1; i<expressionLength; i++){
            if (expression.charAt(i) == arithmeticOperatorOne.getSymbol()){
                String newExpression = arithmeticOperatorOne.evaluateOperator(expression, i);
                return evaluateOperations(newExpression, arithmeticOperatorOne, arithmeticOperatorTwo);
            } else if (expression.charAt(i) == arithmeticOperatorTwo.getSymbol()) {
                String newExpression = arithmeticOperatorTwo.evaluateOperator(expression, i);
                return evaluateOperations(newExpression, arithmeticOperatorOne, arithmeticOperatorTwo);
            }
        }
        return expression;
    }
}
