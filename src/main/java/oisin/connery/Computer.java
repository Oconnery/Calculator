package oisin.connery;

import oisin.connery.operators.*;
import org.apache.commons.lang3.StringUtils;

public class Computer {
    public String calculate (String expression) { // call evaluateExpression instead? // could validation with annotation on expression here with @
        if (expression == null || expression.isEmpty())
            return null; // throw exception
        expression = StringUtils.deleteWhitespace(expression);
        // todo: validate expression // starts with number or a +-. is no two operations in a row that don't include + or - // no letters
        expression = calculateExpression(expression);
        return calculateExpression(expression);
    }

    private String calculateExpression(String expression){
        //int expressionLength = expression.length();
        String postExponentExpression = calculateOperations(expression, new Exponent());
        String postDivisionExpression = calculateOperations(postExponentExpression, new Divison());
        String postMultiplicationExpression = calculateOperations(postDivisionExpression, new Multiplication());
        return calculateOperations(postMultiplicationExpression, new Addition(), new Subtraction());
    }

    // summary comment
    private String calculateOperations(String expression, ArithmeticOperator arithmeticOperator){
        int expressionLength = expression.length();
        for (int i=1; i<expressionLength; i++){
            if (expression.charAt(i) == arithmeticOperator.getSymbol()){
                String newExpression = calculateOperator(expression, expressionLength, i, arithmeticOperator);
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
                String newExpression = calculateOperator(expression, expressionLength, i, arithmeticOperatorOne);
                return calculateOperations(newExpression, arithmeticOperatorOne, arithmeticOperatorTwo);
            } else if (expression.charAt(i) == arithmeticOperatorTwo.getSymbol()) {
                String newExpression = calculateOperator(expression, expressionLength, i, arithmeticOperatorTwo);
                return calculateOperations(newExpression, arithmeticOperatorOne, arithmeticOperatorTwo);
            }
        }
        return expression;
    }

    private String calculateOperator(String expression, int expressionLength, int positionInExpression, ArithmeticOperator arithmeticOperator){
        return arithmeticOperator.calculateExpression(expression, expressionLength, positionInExpression);
    }
}
