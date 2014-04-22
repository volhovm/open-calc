package calc.parser;

import calc.calclib.*;
import calc.calclib.numsystems.CalcNumerable;

/**
 * @author volhovm
 */

public class ExpressionParser {
    //A -> B {("+" | "-") B}
    //B -> C {("*" | "/") C}
    //C -> D ["^" (D | C)]
    //D -> "lb" C | "abs" C | "~" C | "-" C | variable | const | "(" C ")"


    public static <T extends CalcNumerable<T>> Expression<T>
    parse(T type, String expression) throws ParseException {
        ExpressionReader reader = new ExpressionReader(expression.trim());
        return firstLevel(type, reader);
    }

    @SuppressWarnings("Convert2Diamond")
    private static <T extends CalcNumerable<T>> Expression<T>
    firstLevel(T type, ExpressionReader reader) throws ParseException {
        Expression<T> ret = secondLevel(type, reader);
        String s = reader.next();
        while (s.equals(" + ") || s.equals(" - ")) {
            reader.consume();
            switch (s) {
                case " + ":
                    ret = new Add<T>(ret, secondLevel(type, reader));
                    break;
                case " - ":
                    ret = new Subtract<T>(ret, secondLevel(type, reader));
                    break;
            }
            s = reader.next();
            if (s.equals("EOF")) {
                break;
            }
        }
        return ret;
    }

    @SuppressWarnings("Convert2Diamond")
    private static <T extends CalcNumerable<T>> Expression<T>
    secondLevel(T type, ExpressionReader reader) throws ParseException {
        Expression<T> ret = thirdLevel(type, reader);
        String s = reader.next();
        while (s.equals(" * ") || s.equals(" / ")) {
            reader.consume();
            switch (s) {
                case " * ":
                    ret = new Multiply<T>(ret, thirdLevel(type, reader));
                    break;
                case " / ":
                    ret = new Divide<T>(ret, thirdLevel(type, reader));
                    break;
            }
            s = reader.next();
            if (s.equals("EOF")) {
                break;
            }
        }
        return ret;
    }

    @SuppressWarnings("Convert2Diamond")
    private static <T extends CalcNumerable<T>> Expression<T>
    thirdLevel(T type, ExpressionReader reader) throws ParseException {
        Expression<T> ret = fourthLevel(type, reader);
        String s = reader.next();
        while (s.equals(" ^ ")) {
            reader.consume();
            ret = new Power<T>(ret, thirdLevel(type, reader));
            s = reader.next();
            if (s.equals("EOF")) {
                break;
            }
        }
        return ret;
    }

    @SuppressWarnings("Convert2Diamond")
    private static <T extends CalcNumerable<T>> Expression<T>
    fourthLevel(T type, ExpressionReader reader) throws ParseException {
        String s = reader.next();
        Expression<T> ret;
        if (s.equals("abs")) {
            reader.consume();
            ret = new Abs<>(fourthLevel(type, reader));
        } else if (s.equals("lb")) {
            reader.consume();
            ret = new BinaryLog<>(fourthLevel(type, reader));
        } else if (s.length() == 1 && Character.isLowerCase(s.charAt(0)) &&
                Character.isAlphabetic(s.charAt(0))) {
            reader.consume();
            ret = new Variable<>(s);
        } else if (Character.isDigit(s.charAt(0))) {
            reader.consume();
            ret = new Const<>(type.parse(s));
        } else if (s.equals("(")) {
            reader.consume();
            ret = firstLevel(type, reader);
            if (!reader.next().equals(")")) {
                throw new ParseException("stuff", ") -- bracket is not closed.");
            } else {
                reader.consume();
            }
        } else if (s.equals(" - ")) {
            reader.consume();
            ret = new UnaryMin<T>(fourthLevel(type, reader));
        } else if (s.equals(" ~ ")) {
            reader.consume();
            ret = new Not<T>(fourthLevel(type, reader));
        } else if (Character.isAlphabetic(s.charAt(0)) && Character.isLowerCase(s.charAt(0))) {
            reader.consume();
            ret = new AbstractFunction<T>(s, fourthLevel(type, reader));
        } else {
            throw new ParseException("Symbol in a wrong place: '" + s + "' while parsing " +
                    reader.getString() + " at position " + reader
                    .getPosition());
        }
        return ret;
    }
}
