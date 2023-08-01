package oisin.connery.operators;

import lombok.Getter;

@Getter
public abstract class Operator {

    public abstract char getSymbol();

    public abstract String evaluate(String expression, int positionInExpression);
}
