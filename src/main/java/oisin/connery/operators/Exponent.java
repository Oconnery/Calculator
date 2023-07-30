package oisin.connery.operators;

public class Exponent extends ArithmeticOperator {

    public Exponent() {
        super('^');
    }

    @Override
    int calculate(int leftNumber, int rightNumber) {
        return (int) Math.pow(leftNumber, rightNumber);
    }
}
