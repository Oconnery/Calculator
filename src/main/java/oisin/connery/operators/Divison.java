package oisin.connery.operators;

public class Divison extends ArithmeticOperator{

    public Divison() {
        super('/');
    }

    @Override
    int calculate(int leftNumber, int rightNumber) {
        return Math.divideExact(leftNumber, rightNumber);
    }
}
