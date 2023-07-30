package oisin.connery;

import oisin.connery.operators.*;
import org.apache.commons.lang3.StringUtils;

public class Computer {
    public String evaluateExpression(String expression) { // could validate with annotation on expression here with @
        if (expression == null || expression.isEmpty())
            return null; // throw exception
        expression = StringUtils.deleteWhitespace(expression);
        // todo: validate expression // starts with number or a +-. is no two operations in a row that don't include + or - // no letters
        return performEvaluations(expression);
    }

    private String performEvaluations(String expression){
        String postExponentExpression = evaluateOperations(expression, new Exponent());
        String postDivisionExpression = evaluateOperations(postExponentExpression, new Divison());
        String postMultiplicationExpression = evaluateOperations(postDivisionExpression, new Multiplication());
        return evaluateOperations(postMultiplicationExpression, new Addition(), new Subtraction());
    }

    // summary comment
    private String evaluateOperations(String expression, ArithmeticOperator arithmeticOperator){
        int expressionLength = expression.length();
        for (int i=1; i<expressionLength; i++){
            if (expression.charAt(i) == arithmeticOperator.getSymbol()){
                String newExpression = arithmeticOperator.evaluateOperator(expression, expressionLength, i);
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
                String newExpression = arithmeticOperatorOne.evaluateOperator(expression, expressionLength, i);
                return evaluateOperations(newExpression, arithmeticOperatorOne, arithmeticOperatorTwo);
            } else if (expression.charAt(i) == arithmeticOperatorTwo.getSymbol()) {
                String newExpression = arithmeticOperatorTwo.evaluateOperator(expression, expressionLength, i);
                return evaluateOperations(newExpression, arithmeticOperatorOne, arithmeticOperatorTwo);
            }
        }
        return expression;
    }
}
