package oisin.connery;

import oisin.connery.structures.NumberAndIndexes;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// todo: I could improve on performance if I didn't reverse the string on getNumberLeftOfOperator,
//  but instead just started to match from the end and reversed the backwards number afterwards
// todo: I could implement my own search to improve performance since regex is greedy.
public class NumberRetriever {

    private static final String WHOLE_OR_DECIMAL_NUMBER_REGEX = "\\d*\\.?\\d+";

    // String.indexOf() to improve performance
    public static NumberAndIndexes getNumberLeftOfOperator(String expression, int operatorIndex){
        boolean isNegative = false;
        String expressionLeftOfOperator = expression.substring(0, operatorIndex);
        String reversedExpressionOfLeft = new StringBuilder(expressionLeftOfOperator).reverse().toString();
        Matcher matcher = Pattern.compile(WHOLE_OR_DECIMAL_NUMBER_REGEX).matcher(reversedExpressionOfLeft);

        matcher.find();
        StringBuilder numberBuilder = new StringBuilder(matcher.group()).reverse();
        String numberAsString = numberBuilder.toString();
        BigDecimal number = new BigDecimal(numberAsString);

        if (expression.charAt(0) == '-')
            isNegative = true;

        return NumberAndIndexes.builder()
                .number(isNegative?number.negate():number)
                .startingIndex(operatorIndex - matcher.end() - (isNegative?1:0))
                .build();
    }

    public static NumberAndIndexes getNumberRightOfOperator(String expression, int operatorIndex){
        int indexRightOfOperator = operatorIndex+1;
        String expressionRightOfOperator = expression.substring(indexRightOfOperator);
        Matcher matcher = Pattern.compile(WHOLE_OR_DECIMAL_NUMBER_REGEX).matcher(expressionRightOfOperator);
        matcher.find();
        BigDecimal number = new BigDecimal(matcher.group());

        return NumberAndIndexes.builder()
                .number(number)
                .endingIndex(indexRightOfOperator + matcher.end())
                .build();
    }
}