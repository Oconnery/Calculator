package oisin.connery.operators;

import oisin.connery.symbols.SymbolType;

import java.math.BigDecimal;

public class MultiplicationOperator extends BasicArithmeticOperator {

    public MultiplicationOperator() {
        super(SymbolType.MULTIPLICATION.symbol, SymbolType.MULTIPLICATION);
    }

    @Override
    protected BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber) {
        return leftNumber.multiply(rightNumber);
    }
}
