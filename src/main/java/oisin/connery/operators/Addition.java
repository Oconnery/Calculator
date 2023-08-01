package oisin.connery.operators;

import java.math.BigDecimal;

public class Addition extends ArithmeticOperator {

    public Addition() {
        super('+');
    }

    @Override
    protected BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber) {
        return leftNumber.add(rightNumber);
    }
}
