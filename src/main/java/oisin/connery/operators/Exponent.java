package oisin.connery.operators;

import java.math.BigDecimal;

public class Exponent extends ArithmeticOperator {

    public Exponent() {
        super('^');
    }

    @Override
    BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber) { // should be int or Integer?
        // todo: implementation
        return leftNumber.pow(rightNumber.intValue());
    }

    BigDecimal calculate(BigDecimal leftNumber, int rightNumber){
        return leftNumber.pow(rightNumber);
    }
}
