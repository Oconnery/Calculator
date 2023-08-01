package oisin.connery.operators;

import java.math.BigDecimal;
import java.math.MathContext;

public class Division extends ArithmeticOperator{

    public Division() {
        super('/');
    }

    @Override
    protected BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber) {
        return leftNumber.divide(rightNumber, MathContext.DECIMAL64);
    }
}
