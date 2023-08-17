package oisin.connery.operators;

import oisin.connery.NumberRetriever;
import oisin.connery.structures.NumberAndIndexes;
import oisin.connery.symbols.SymbolType;

import java.math.BigDecimal;

public class FactorialOperator extends Operator {

    public FactorialOperator() {
        super(SymbolType.FACTORIAL.symbol, SymbolType.FACTORIAL);
    }

    @Override
    public String evaluate(String expression, int positionInExpression) {
        NumberAndIndexes leftNumberAndIndexes = NumberRetriever.getNumberLeftOfOperator(expression, positionInExpression);
        BigDecimal result = calculate(leftNumberAndIndexes.getNumber().intValueExact());
        StringBuilder stringBuilder = new StringBuilder(expression);
        stringBuilder.replace(leftNumberAndIndexes.getStartingIndex(), positionInExpression+1,result.toPlainString());
        return stringBuilder.toString();
    }

    protected BigDecimal calculate(int number) {
        BigDecimal result = BigDecimal.ONE;
        for (int i = 2; i <= number; i++)
            result = result.multiply(BigDecimal.valueOf(i));
        return result;
    }
}
