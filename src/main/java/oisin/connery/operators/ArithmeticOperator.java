package oisin.connery.operators;

import lombok.Getter;
import oisin.connery.NumberAndIndexes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public abstract class ArithmeticOperator {

    private final char symbol;

     protected ArithmeticOperator(char symbol) {
        this.symbol = symbol;
    }

    public String evaluateOperator(String expression, int expressionLength, int positionInExpression){
        NumberAndIndexes leftNumberAndIndexes = getLeftNumber(expression, positionInExpression);
        NumberAndIndexes rightNumberAndIndexes = getRightNumber(expression, positionInExpression);
        int result = calculate(leftNumberAndIndexes.getNumber(), rightNumberAndIndexes.getNumber());
        StringBuilder stringBuilder = new StringBuilder(expressionLength);
        stringBuilder.append(expression, 0, leftNumberAndIndexes.getStartingIndex());
        stringBuilder.append(result);
        stringBuilder.append(expression,rightNumberAndIndexes.getEndingIndex(), expressionLength);
        return stringBuilder.toString(); // StringBuilder code in it's own method?
    }

    private static NumberAndIndexes getLeftNumber(String expression, int operatorPositionInExpression){
        String expressionSubString = expression.substring(0, operatorPositionInExpression); // name leftExpression instead or similar
        StringBuilder reversedExpressionBuilder = new StringBuilder(expressionSubString).reverse(); // I could improve on performance if I didnt reverse the string, but instead just started to match from the end and reversed the backwards number afterwards
        Pattern integerPattern = Pattern.compile("-?\\d+"); // I can perhaps add a $ here to read from the back. This will improve performance.
        Matcher matcher = integerPattern.matcher(reversedExpressionBuilder.toString());
        matcher.find();
        StringBuilder numberBuilder = new StringBuilder(matcher.group()).reverse();
        String numberAsString = numberBuilder.toString();

        return NumberAndIndexes.builder()
                .number(Integer.parseInt(numberAsString))
                .startingIndex(operatorPositionInExpression - matcher.end())
                .build();
    }
    // todo: left and right number should probably be placed in a different class.
    // I could maybe create an expression class that has a stringBuilder etc.
    private static NumberAndIndexes getRightNumber(String expression, int operatorPositionInExpression){
        String expressionSubString = expression.substring(operatorPositionInExpression+1); // name right expressionInstead
        Pattern integerPattern = Pattern.compile("-?\\d+");
        Matcher matcher = integerPattern.matcher(expressionSubString);
        matcher.find();
        int number = Integer.parseInt(matcher.group());

        return NumberAndIndexes.builder()
                .number(number)
                .endingIndex(operatorPositionInExpression + 1 + matcher.end())
                .build();
    }

    // write a summary here
    abstract int calculate(int leftNumber, int rightNumber);
}
