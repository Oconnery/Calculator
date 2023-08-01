package oisin.connery.operators;

import java.math.BigDecimal;

public class SubtractionOperator extends BasicArithmeticOperator {
    @Override
    public char getSymbol() {
        return '-';
    }

    @Override
    protected BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber) {
        return leftNumber.subtract(rightNumber);
    }
}
