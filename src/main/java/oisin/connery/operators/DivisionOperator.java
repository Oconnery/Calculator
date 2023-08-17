package oisin.connery.operators;

import oisin.connery.symbols.SymbolType;

import java.math.BigDecimal;
import java.math.MathContext;

public class DivisionOperator extends BasicArithmeticOperator {

    public DivisionOperator() {
        super(SymbolType.DIVISION.symbol, SymbolType.DIVISION);
    }

    @Override
    protected BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber) {
        return leftNumber.divide(rightNumber, MathContext.DECIMAL64);
    }
}
