package oisin.connery.operators;

import oisin.connery.NumberRetriever;
import oisin.connery.exceptions.ExceptionMessages;
import oisin.connery.structures.NumberAndIndexes;
import oisin.connery.symbols.SymbolType;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

public abstract class BasicArithmeticOperator extends Operator{

    public BasicArithmeticOperator(char symbol, SymbolType symbolType) {
        super(symbol, symbolType);
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
        return stringBuilder.toString(); // StringBuilder code in its own method?
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

    protected abstract BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber);
}
