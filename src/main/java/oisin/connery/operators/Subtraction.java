package oisin.connery.operators;

public class Subtraction extends ArithmeticOperator {

    public Subtraction() {
        super('-');
    }

    @Override
    int calculate(int leftNumber, int rightNumber) {
        return Math.subtractExact(leftNumber, rightNumber);
    }
}
