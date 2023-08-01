package oisin.connery.operators;

import java.math.BigDecimal;
import java.math.MathContext;

public class DivisionOperator extends BasicArithmeticOperator {

    @Override
    public char getSymbol() {
        return '/';
    }

    @Override
    protected BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber) {
        return leftNumber.divide(rightNumber, MathContext.DECIMAL64);
    }
}
