package calc.calclib;

import calc.calclib.exceptions.CalcException;
import calc.calclib.numsystems.CalcNumerable;

/**
 * @author volhovm
 *         Created on 15.04.14
 */


public class BinaryLog<T extends CalcNumerable<T>> extends UnaryOperations<T> {
    @SuppressWarnings("FieldCanBeLocal")
    private final short PRIORITY = 4;
    //    final static double ln2 = 0.30102999566;

    public BinaryLog(Expression<T> a) {
        super(a);
    }

    @Override
    public T evaluate(T x, T y, T z) throws CalcException {
        return a.evaluate(x, y, z).binaryLog();
//        double arg = (double) a.evaluate(x, y, z);
//        if (arg <= 0) {
//            throw new IncorrectLogarithmArgument();
//        }
//        long ret = 31 - Integer.numberOfLeadingZeros((int) arg);
//        if (ret > Integer.MAX_VALUE || ret < Integer.MIN_VALUE) {
//            throw new OverflowException("there was an overflow while evaluating: " + this);
//        }
//        return (int) ret;
    }

    @Override
    public String toString() {
        return ("lb(" + a.toString() + ")");
    }

    @Override
    public short getPriority() {
        return PRIORITY;
    }
}
