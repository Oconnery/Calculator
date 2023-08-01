package oisin.connery.operators;

import java.math.BigDecimal;

public class MultiplicationOperator extends BasicArithmeticOperator {

    @Override
    public char getSymbol() {
        return '*';
    }

    @Override
    protected BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber) {
        return leftNumber.multiply(rightNumber);
    }
}
