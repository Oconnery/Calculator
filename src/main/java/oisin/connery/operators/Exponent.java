package oisin.connery.operators;

import java.math.BigDecimal;

public class Exponent extends ArithmeticOperator {

    public Exponent() {
        super('^');
    }

    @Override
    BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber) {
        return leftNumber.pow(rightNumber.intValue());
    }
}
