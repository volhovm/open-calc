package main.calc.calclib;

/**
 * @author volhovm
 */

public class UnaryMin extends UnaryOperations implements Expression3 {
    final short PRIORITY = 4;

    public UnaryMin(Expression3 a) {
        super(a);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -a.evaluate(x, y, z);
    }

    @Override
    public String toString() {
        return "-" + (a.getPriority() >= PRIORITY ? a.toString() : "(" + a.toString() + ")");
    }

    @Override
    public short getPriority() {
        return PRIORITY;
    }
}
