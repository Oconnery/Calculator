package oisin.connery;

import oisin.connery.structures.NumberAndIndexes;

import java.math.BigDecimal;

public class NumberRetriever {

    public static NumberAndIndexes getNumberLeftOfOperator(String expression, int operatorIndex){
        boolean isNegative = expression.charAt(0) == '-';
        String expressionLeftOfOperator = expression.substring(0, operatorIndex);
        int length = expressionLeftOfOperator.length();
        StringBuilder numberBuilder = new StringBuilder();

        for (int i = length-1; i>-1; i--) {
            char c = expressionLeftOfOperator.charAt(i);
            if (Character.isDigit(c) || c=='.')
                numberBuilder.append((c));
            else
                break;
        }
        numberBuilder.reverse();
        BigDecimal number = new BigDecimal(numberBuilder.toString());

        return NumberAndIndexes.builder()
                .number(isNegative?number.negate():number)
                .startingIndex(operatorIndex -numberBuilder.length() - (isNegative?1:0))
                .build();
    }

    public static NumberAndIndexes getNumberRightOfOperator(String expression, int operatorIndex){
        int indexRightOfOperator = operatorIndex+1;
        String expressionRightOfOperator = expression.substring(indexRightOfOperator);
        StringBuilder numberBuilder = new StringBuilder();
        for (char c: expressionRightOfOperator.toCharArray()) {
            if (Character.isDigit(c) || c=='.')
                numberBuilder.append((c));
            else
                break;
        }
        return NumberAndIndexes.builder()
                .number(new BigDecimal(numberBuilder.toString()))
                .endingIndex(indexRightOfOperator +numberBuilder.length())
                .build();
    }
}