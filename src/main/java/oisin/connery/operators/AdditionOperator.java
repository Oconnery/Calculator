package oisin.connery.operators;

import java.math.BigDecimal;

public class AdditionOperator extends BasicArithmeticOperator {

    @Override
    public char getSymbol() { // todo: should these be a string or a Char?
        return '+';
    }

    @Override
    protected BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber) {
        return leftNumber.add(rightNumber);
    }
}
