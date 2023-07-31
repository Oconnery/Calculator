package oisin.connery.operators;

import lombok.Getter;
import oisin.connery.structures.NumberAndIndexes;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public abstract class ArithmeticOperator {

    private final char symbol;
    public static final String WHOLE_OR_DECIMAL_NUMBER_REGEX = "\\d*\\.?\\d+";

    protected ArithmeticOperator(char symbol) {
        this.symbol = symbol;
    }

    public String evaluateOperator(String expression, int positionInExpression){ // 4--6 should be read as 7+6 -7+6 should be -1 answer.
        boolean isNegativeNumber = false;
        char leftChar = expression.charAt(positionInExpression-1);
        char rightChar = expression.charAt(positionInExpression+1);

        if (leftChar == '-' || leftChar == '+'){
            isNegativeNumber = mixOfAddsAndMinusesResultsInNegative(leftChar, symbol);
            StringBuilder stringBuilder = new StringBuilder(expression).deleteCharAt(positionInExpression-1);
            stringBuilder.setCharAt(positionInExpression, isNegativeNumber?'-':'+');
            positionInExpression--;
            expression = stringBuilder.toString();
            if (positionInExpression == 0 || positionInExpression == expression.length()-1){
                return expression;
            } else {
                evaluateOperator(expression, positionInExpression);
            }
        }
        if (rightChar == '-' || rightChar == '+'){
            isNegativeNumber = mixOfAddsAndMinusesResultsInNegative(symbol, rightChar); // 7--2
            StringBuilder stringBuilder = new StringBuilder(expression).deleteCharAt(positionInExpression+1);
            stringBuilder.setCharAt(positionInExpression, isNegativeNumber?'-':'+');
            positionInExpression++;
            expression = stringBuilder.toString();
            if (positionInExpression == 0 || positionInExpression == expression.length()-1){
                return expression;
            } else {
                evaluateOperator(expression, positionInExpression);
            }
        }
        NumberAndIndexes leftNumberAndIndexes = getLeftNumber(expression, positionInExpression, isNegativeNumber);
        NumberAndIndexes rightNumberAndIndexes = getRightNumber(expression, positionInExpression, isNegativeNumber);
        BigDecimal result = calculate(leftNumberAndIndexes.getNumber(), rightNumberAndIndexes.getNumber());
        StringBuilder stringBuilder = new StringBuilder(expression); // Can this length be improved to be more accurate
        stringBuilder.replace(leftNumberAndIndexes.getStartingIndex(), rightNumberAndIndexes.getEndingIndex() ,result.toPlainString());
        return stringBuilder.toString(); // StringBuilder code in it's own method?
    }

    private boolean mixOfAddsAndMinusesResultsInNegative(char firstChar, char secondChar){
        if (firstChar == '+'){
            return secondChar == '-';
        } else if (firstChar == '-'){
            return secondChar != '-';
        }
        //else throw
        return false;
    }

    // todo: look into String.indexOf methods
    private static NumberAndIndexes getLeftNumber(String expression, int operatorPositionInExpression, boolean isNegative){
        String expressionSubString = expression.substring(0, operatorPositionInExpression); // name leftExpression instead or similar
        String reversedExpression = new StringBuilder(expressionSubString).reverse().toString(); // I could improve on performance if I didnt reverse the string, but instead just started to match from the end and reversed the backwards number afterwards
        Pattern numberPattern = Pattern.compile(WHOLE_OR_DECIMAL_NUMBER_REGEX); // I can perhaps add a $ here to read from the back. This will improve performance.
        Matcher matcher = numberPattern.matcher(reversedExpression);

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
    // todo: left and right number should probably be placed in a different class.
    // I could maybe create an expression class that has a stringBuilder etc.
    private static NumberAndIndexes getRightNumber(String expression, int operatorPositionInExpression, boolean isNegative){
        String expressionSubString = expression.substring(operatorPositionInExpression+1); // name right expressionInstead
        Pattern numberPattern = Pattern.compile(WHOLE_OR_DECIMAL_NUMBER_REGEX);

        Matcher matcher = numberPattern.matcher(expressionSubString);
        matcher.find();
        BigDecimal number = new BigDecimal(matcher.group());

        return NumberAndIndexes.builder()
                .number(isNegative?number.negate():number)
                .endingIndex(operatorPositionInExpression + 1 + matcher.end())
                .build();
    }

    // write a summary here
    abstract BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber);
}
