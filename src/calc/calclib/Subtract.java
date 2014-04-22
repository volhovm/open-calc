package calc.calclib;

import calc.calclib.exceptions.CalcException;
import calc.calclib.numsystems.CalcNumerable;

import java.util.stream.Collectors;

/**
 * @author volhovm
 */

public class Subtract<T extends CalcNumerable<T>> extends BinaryOperations<T> {
    private final static short PRIORITY = 2;

    public Subtract(Expression3<T>... expressions) {
        super(expressions);
    }

    @SafeVarargs
    @Override
    public final T evaluate(T... args) throws CalcException {
        return arguments.stream()
                .map((Expression3<T> a) -> a.evaluate(args))
                .reduce((a, b) -> a.sub(b))
                .get();
    }

    @Override
    public String toString() {
        return arguments.stream()
                .map((Expression3<T> x) -> x.getPriority() >= PRIORITY ? x.toString() :
                        "(" + x.toString() + ")")
                .collect(Collectors.joining(" - "));
    }

    @Override
    public short getPriority() {
        return PRIORITY;
    }
}
