package oisin.connery.operators;

import java.math.BigDecimal;

public class Multiplication extends ArithmeticOperator{

    public Multiplication() {
        super('*');
    }

    @Override
    BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber) {
        return leftNumber.multiply(rightNumber);
    }
}
