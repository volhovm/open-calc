package main.calc.calclib;

/**
 * @author volhovm
 */

public abstract class UnaryOperations implements Expression3 {
    public Expression3 a;

    protected UnaryOperations(Expression3 a) {
        this.a = a;
    }

    @Override
    public abstract int evaluate(int x, int y, int z);
}