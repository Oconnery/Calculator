package oisin.connery;

import oisin.connery.structures.NumberAndIndexes;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberRetriever {

    private static final String WHOLE_OR_DECIMAL_NUMBER_REGEX = "\\d*\\.?\\d+";

    public static NumberAndIndexes getNumberLeftOfOperator(String expression, int operatorPositionInExpression, boolean isNegative){
        String expressionLeftOfOperator = expression.substring(0, operatorPositionInExpression);
        String reversedExpressionOfLeft = new StringBuilder(expressionLeftOfOperator).reverse().toString(); // I could improve on performance if I didnt reverse the string, but instead just started to match from the end and reversed the backwards number afterwards
        Pattern numberPattern = Pattern.compile(WHOLE_OR_DECIMAL_NUMBER_REGEX); // I can perhaps add a $ here to read from the back. This will improve performance.
        Matcher matcher = numberPattern.matcher(reversedExpressionOfLeft);

        if (expression.charAt(0) == '-')
            isNegative = true;

        matcher.find();
        StringBuilder numberBuilder = new StringBuilder(matcher.group()).reverse();
        String numberAsString = numberBuilder.toString();
        BigDecimal number = new BigDecimal(numberAsString);

        return NumberAndIndexes.builder()
                .number(isNegative?number.negate():number)
                .startingIndex(operatorPositionInExpression - matcher.end() - (isNegative?1:0))
                .build();
    }

    public static NumberAndIndexes getNumberRightOfOperator(String expression, int indexRightOfOperator, boolean isNegative){
        String expressionRightOfOperator = expression.substring(indexRightOfOperator); // name right expressionInstead
        Pattern numberPattern = Pattern.compile(WHOLE_OR_DECIMAL_NUMBER_REGEX);

        Matcher matcher = numberPattern.matcher(expressionRightOfOperator);
        matcher.find();
        BigDecimal number = new BigDecimal(matcher.group());

        return NumberAndIndexes.builder()
                .number(isNegative?number.negate():number)
                .endingIndex(indexRightOfOperator + matcher.end())
                .build();
    }

}
