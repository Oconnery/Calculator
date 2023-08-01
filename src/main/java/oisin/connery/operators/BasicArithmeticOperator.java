package oisin.connery.operators;

import lombok.Getter;
import oisin.connery.NumberRetriever;
import oisin.connery.exceptions.ExceptionMessages;
import oisin.connery.structures.NumberAndIndexes;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

@Getter
public abstract class BasicArithmeticOperator implements Operator{

    private final char symbol;

    protected BasicArithmeticOperator(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public String evaluate(String expression, int positionInExpression){
        char rightChar = expression.charAt(positionInExpression+1);

        if (rightChar == '-' || rightChar == '+'){
            expression = evaluateMultipleSignOperators(expression, positionInExpression, rightChar);
            positionInExpression++;
            if (positionInExpression == expression.length()-1){
                return expression;
            } else {
                return evaluate(expression, positionInExpression);
            }
        }

        NumberAndIndexes leftNumberAndIndexes = NumberRetriever.getNumberLeftOfOperator(expression, positionInExpression);
        NumberAndIndexes rightNumberAndIndexes = NumberRetriever.getNumberRightOfOperator(expression, positionInExpression);
        BigDecimal result = calculate(leftNumberAndIndexes.getNumber(), rightNumberAndIndexes.getNumber());

        StringBuilder stringBuilder = new StringBuilder(expression);
        stringBuilder.replace(leftNumberAndIndexes.getStartingIndex(), rightNumberAndIndexes.getEndingIndex() ,result.toPlainString());
        return stringBuilder.toString(); // StringBuilder code in it's own method?
    }

    private String evaluateMultipleSignOperators(String expression, int positionInExpression, char rightChar) { // improve method name
        StringBuilder resolvePlusMinusBuilder = new StringBuilder(expression);
        resolvePlusMinusBuilder.deleteCharAt(positionInExpression +1);
        resolvePlusMinusBuilder.setCharAt(positionInExpression, evaluateSigns(symbol, rightChar));
        return resolvePlusMinusBuilder.toString();
    }

    private char evaluateSigns(char firstChar, char secondChar){
        if (firstChar == '+' && secondChar == '-') {
            return '-';
        } else if (firstChar == '-' && secondChar == '+') {
            return '-';
        } else if (firstChar == '-' && secondChar == '-') {
            return '+';
        } else if (firstChar == '+' && secondChar == '+') {
            return '+';
        } else{
            throw new InvalidParameterException(ExceptionMessages.wrongCharacterOnAddMinusMethodCheck(firstChar, secondChar));
        }
    }

    // todo: look into String.indexOf methods
    // write a summary here
    protected abstract BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber);
}
