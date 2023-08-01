package oisin.connery.operators;

import java.math.BigDecimal;

public class Subtraction extends BasicArithmeticOperator {

    public Subtraction() {
        super('-');
    }

    @Override
    protected BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber) {
        return leftNumber.subtract(rightNumber);
    }
}
