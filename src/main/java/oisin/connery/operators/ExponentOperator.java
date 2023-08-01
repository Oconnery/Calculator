package oisin.connery.operators;

import oisin.connery.exceptions.ExceptionMessages;
import oisin.connery.exceptions.ExpressionFormatException;

import java.math.BigDecimal;

public class ExponentOperator extends BasicArithmeticOperator {
    public static final int MAX_PROCESSABLE_POWER_LENGTH = 10;

    @Override
    public char getSymbol() {
        return '^';
    }

    @Override
    protected BigDecimal calculate(BigDecimal base, BigDecimal power) {
        if (!isPowerTooLarge(power))
            return base.pow(power.intValue());
        else
            throw new ExpressionFormatException(ExceptionMessages.exponentPowerTooLong(base, power, MAX_PROCESSABLE_POWER_LENGTH));
    }

    private boolean isPowerTooLarge(BigDecimal power){
        return power.precision() >= MAX_PROCESSABLE_POWER_LENGTH;
    }
}