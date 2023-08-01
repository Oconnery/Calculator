package oisin.connery.operators;

import java.math.BigDecimal;

public class Addition extends BasicArithmeticOperator {

    public Addition() {
        super('+');
    }

    @Override
    protected BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber) {
        return leftNumber.add(rightNumber);
    }
}
