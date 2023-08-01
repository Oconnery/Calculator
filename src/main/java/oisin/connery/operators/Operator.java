package oisin.connery.operators;

import lombok.Getter;

@Getter
public abstract class Operator {
    protected final char symbol;

    public Operator(char symbol) {
        this.symbol = symbol;
    }

    public abstract String evaluate(String expression, int positionInExpression);
}
