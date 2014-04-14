package main.calc.calclib;

import main.calc.calclib.Exceptions.CalcException;

/**
 * @author volhovm
 */

public class Not extends UnaryOperations implements Expression3 {
    final short PRIORITY = 4;

    public Not(Expression3 a) {
        super(a);
    }

    @Override
    public int evaluate(int x, int y, int z) throws CalcException {
        return ~(a.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "~" + (a.getPriority() >= PRIORITY ? a.toString() : "(" + a.toString() + ")");
    }

    @Override
    public short getPriority() {
        return PRIORITY;
    }
}
