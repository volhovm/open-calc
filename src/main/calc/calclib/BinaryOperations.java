package main.calc.calclib;

/**
 * @author volhovm
 */

public abstract class BinaryOperations implements Expression3 {
    public Expression3 a, b;

    protected BinaryOperations(Expression3 a, Expression3 b) {
        this.a = a;
        this.b = b;
    }

    @Override
    abstract public int evaluate(int x, int y, int z);

    @Override
    abstract public String toString();

    //    @Override
    //    abstract public Expression3 simplify();
}
