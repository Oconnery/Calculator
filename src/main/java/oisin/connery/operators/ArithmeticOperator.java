package oisin.connery.operators;

import lombok.Getter;
import oisin.connery.NumberRetriever;
import oisin.connery.exceptions.ExceptionMessages;
import oisin.connery.structures.NumberAndIndexes;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

@Getter
public abstract class ArithmeticOperator {

    private final char symbol;

    protected ArithmeticOperator(char symbol) {
        this.symbol = symbol;
    }

    public String evaluateOperator(String expression, int positionInExpression){
        boolean isNegativeNumber = false;
        char rightChar = expression.charAt(positionInExpression+1);

        if (rightChar == '-' || rightChar == '+'){
            expression = dealWithMultipleAddsOrMinuses(expression, positionInExpression, rightChar);
            positionInExpression++;
            if (positionInExpression == expression.length()-1){
                return expression;
            } else {
                return evaluateOperator(expression, positionInExpression);
            }
        }
        NumberAndIndexes leftNumberAndIndexes = NumberRetriever.getNumberLeftOfOperator(expression, positionInExpression, isNegativeNumber);
        NumberAndIndexes rightNumberAndIndexes = NumberRetriever.getNumberRightOfOperator(expression, positionInExpression, isNegativeNumber);
        BigDecimal result = calculate(leftNumberAndIndexes.getNumber(), rightNumberAndIndexes.getNumber());

        StringBuilder stringBuilder = new StringBuilder(expression);
        stringBuilder.replace(leftNumberAndIndexes.getStartingIndex(), rightNumberAndIndexes.getEndingIndex() ,result.toPlainString());
        return stringBuilder.toString(); // StringBuilder code in it's own method?
    }

    private String dealWithMultipleAddsOrMinuses(String expression, int positionInExpression, char rightChar) {
        boolean isNegativeNumber = mixOfAddsAndMinusesResultsInNegative(symbol, rightChar);
        StringBuilder resolvePlusMinusBuilder = new StringBuilder(expression);
        resolvePlusMinusBuilder.deleteCharAt(positionInExpression +1);
        resolvePlusMinusBuilder.setCharAt(positionInExpression, isNegativeNumber?'-':'+');
        return resolvePlusMinusBuilder.toString();
    }

    private boolean mixOfAddsAndMinusesResultsInNegative(char firstChar, char secondChar){
        if (firstChar == '+'){
            return secondChar == '-';
        } else if (firstChar == '-'){
            return secondChar != '-';
        } else{
            throw new InvalidParameterException(ExceptionMessages.wrongCharacterOnAddMinusMethodCheck(firstChar, secondChar));
        }
    }

    // todo: look into String.indexOf methods

    // write a summary here
    protected abstract BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber);
}
