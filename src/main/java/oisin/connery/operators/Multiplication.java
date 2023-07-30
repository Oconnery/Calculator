package oisin.connery.operators;

public class Multiplication extends ArithmeticOperator{

    public Multiplication() {
        super('*');
    }

    @Override
    int calculate(int leftNumber, int rightNumber) {
        return Math.multiplyExact(leftNumber, rightNumber);
    }
}
