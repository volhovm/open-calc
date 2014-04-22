package calc.calclib.numsystems;

import calc.calclib.exceptions.CalcException;
import calc.calclib.exceptions.DivideByZeroException;
import calc.calclib.exceptions.FunctionNotDefined;
import calc.calclib.exceptions.IncorrectLogarithmArgument;
import com.sun.istack.internal.NotNull;

/**
 * @author volhovm
 * Created on 21.04.14
 */

public class CalcInt implements CalcNumerable<CalcInt>, Comparable<CalcInt> {
    private
    @NotNull
    final
    Integer a;

    public CalcInt(Integer a) {
        this.a = a;
    }

    @Override
    public CalcInt id() {
        return this;
    }

    @Override
    public CalcInt sin() {
        throw new FunctionNotDefined("sin in integer");
    }

    @Override
    public CalcInt factorial() {
        int ret = 1;
        for (int i = 1; i <= a; i++) {
            i *= a;
        }
        return new CalcInt(ret);
    }

    @Override
    public CalcInt plus(CalcInt b) throws CalcException {
        long ret = (long) a + (long) b.a;
//        if (ret > Integer.MAX_VALUE || ret < Integer.MIN_VALUE)
//            throw new OverflowException(a.toString());
        return new CalcInt((int) ret);
    }

    @Override
    public CalcInt mul(CalcInt b) {
        long ret = (long) a * (long) b.a;
//        if (ret > Integer.MAX_VALUE || ret < Integer.MIN_VALUE)
//            throw new OverflowException(a.toString());
        return new CalcInt((int) ret);
    }

    @Override
    public CalcInt div(CalcInt b) {
        if (b.a == 0) throw new DivideByZeroException(a.toString());
        long ret = (long) a / (long) b.a;
//        if (ret > Integer.MAX_VALUE || ret < Integer.MIN_VALUE)
//            throw new OverflowException(a.toString());
        return new CalcInt((int) ret);
    }

    @Override
    public CalcInt sub(CalcInt b) {
        long ret = (long) a - (long) b.a;
//        if (ret > Integer.MAX_VALUE || ret < Integer.MIN_VALUE)
//            throw new OverflowException(a.toString());
        return new CalcInt((int) ret);
    }

    @Override
    public CalcInt power(CalcInt b) {
        long ret = 1;
        for (int i = 0; i < b.a; i++) {
            ret = ret * a;
        }
//        if (ret > Integer.MAX_VALUE || ret < Integer.MIN_VALUE)
//            throw new OverflowException(a.toString());
        return new CalcInt((int) ret);
    }

    @Override
    public CalcInt abs() {
        return new CalcInt(Math.abs(a));
    }

    @Override
    public CalcInt binaryLog() {
        if (a <= 0) {
            throw new IncorrectLogarithmArgument();
        }
        long ret = 31 - Integer.numberOfLeadingZeros(a);
//        if (ret > Integer.MAX_VALUE || ret < Integer.MIN_VALUE) {
//            throw new OverflowException(a.toString());
//        }
        return new CalcInt((int) ret);
    }

    @Override
    public CalcInt not() {
        return new CalcInt(~a);
    }

    @Override
    public CalcInt unaryMin() {
        return new CalcInt(-a);
    }

    @Override
    public CalcInt parse(String s) {
        return new CalcInt(Integer.parseInt(s));
    }

    public String toString() {
        return a.toString();
    }

    @Override
    public Integer getInnerVariable() {
        return a;
    }

    @Override
    public int compareTo(@NotNull CalcInt o) {
        if (o == null) throw new NullPointerException("comparing null objects");
        return a.compareTo(o.a);
    }

    @Override
    public <Z extends Number> CalcInt getInstance(Z a) {
        return new CalcInt(a.intValue());
    }
}
