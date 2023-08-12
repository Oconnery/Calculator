package oisin.connery.operators;

import oisin.connery.symbols.SymbolType;

import java.math.BigDecimal;

public class SubtractionOperator extends BasicArithmeticOperator {

    public SubtractionOperator() {
        super(SymbolType.SUBTRACTION.symbol, SymbolType.SUBTRACTION);
    }

    @Override
    protected BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber) {
        return leftNumber.subtract(rightNumber);
    }
}
