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

    public String evaluateOperator(String expression, int positionInExpression){
        NumberAndIndexes leftNumberAndIndexes = getLeftNumber(expression, positionInExpression);
        NumberAndIndexes rightNumberAndIndexes = getRightNumber(expression, positionInExpression);
        BigDecimal result = calculate(leftNumberAndIndexes.getNumber(), rightNumberAndIndexes.getNumber());
        StringBuilder stringBuilder = new StringBuilder(expression); // Can this length be improved to be more accurate
        stringBuilder.replace(leftNumberAndIndexes.getStartingIndex(), rightNumberAndIndexes.getEndingIndex() ,result.toPlainString());
        return stringBuilder.toString(); // StringBuilder code in it's own method?
    }
    // todo: look into String.indexOf methods
    private static NumberAndIndexes getLeftNumber(String expression, int operatorPositionInExpression){
        String expressionSubString = expression.substring(0, operatorPositionInExpression); // name leftExpression instead or similar
        StringBuilder reversedExpressionBuilder = new StringBuilder(expressionSubString).reverse(); // I could improve on performance if I didnt reverse the string, but instead just started to match from the end and reversed the backwards number afterwards
        Pattern numberPattern = Pattern.compile(WHOLE_OR_DECIMAL_NUMBER_REGEX); // I can perhaps add a $ here to read from the back. This will improve performance.
        Matcher matcher = numberPattern.matcher(reversedExpressionBuilder.toString());
        matcher.find();
        StringBuilder numberBuilder = new StringBuilder(matcher.group()).reverse();
        String numberAsString = numberBuilder.toString();

        return NumberAndIndexes.builder()
                .number(new BigDecimal(numberAsString))
                .startingIndex(operatorPositionInExpression - matcher.end())
                .build();
    }
    // todo: left and right number should probably be placed in a different class.
    // I could maybe create an expression class that has a stringBuilder etc.
    private static NumberAndIndexes getRightNumber(String expression, int operatorPositionInExpression){
        String expressionSubString = expression.substring(operatorPositionInExpression+1); // name right expressionInstead
        Pattern numberPattern = Pattern.compile(WHOLE_OR_DECIMAL_NUMBER_REGEX);

        Matcher matcher = numberPattern.matcher(expressionSubString);
        matcher.find();
        BigDecimal number = new BigDecimal(matcher.group());

        return NumberAndIndexes.builder()
                .number(number)
                .endingIndex(operatorPositionInExpression + 1 + matcher.end())
                .build();
    }

    // write a summary here
    abstract BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber); // todo: could have multiple overloaded calculate methods. e.g. int, double and BigDecimal(Just for multiply and divide). This would increase performance.
    // todo: so every arithmeticOperator needs a calculate with (int, int)
    // in some cases it may make sense to use double or int for arithmetic operations because this would save performance
}
