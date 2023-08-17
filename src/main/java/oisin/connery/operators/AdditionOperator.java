package oisin.connery.operators;

import oisin.connery.symbols.SymbolType;

import java.math.BigDecimal;

public class AdditionOperator extends BasicArithmeticOperator {

    public AdditionOperator() {
        super(SymbolType.ADDITION.symbol, SymbolType.ADDITION);
    }

    @Override
    protected BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber) {
        return leftNumber.add(rightNumber);
    }
}
