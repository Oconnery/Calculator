package oisin.connery.operators;

public class Addition extends ArithmeticOperator {

    public Addition() {
        super('+');
    }

    @Override
    int calculate(int leftNumber, int rightNumber) {
        return Math.addExact(leftNumber, rightNumber);
    }
}
