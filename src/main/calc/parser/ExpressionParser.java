package main.calc.parser;

import main.calc.calclib.*;

/**
 * Created by volhovm on 31.03.14.
 */
public class ExpressionParser {

    public static void main(String[] args) {
        try {
            Expression3 exp = parse("-(-(-\t\t-5 + 16   *x*y) + 1 * z) -(((-11)))");
            System.out.println(exp.evaluate(0, 0, 0));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static class Reader {
        int position;
        String string;

        private Reader(String string) {
            this.string = string;
            position = 0;
        }

        private class Pair {
            String str;
            int value;

            private Pair(String str, int value) {
                this.str = str;
                this.value = value;
            }
        }

        private Pair getToken() {
            if (position >= string.length()) return new Pair("EOF", 0);
            int i;
            for (i = position; i < string.length() && Character.isWhitespace(string.charAt(i)); i++) {
                position++;
            }
            Character c = string.charAt(position);
            if (c == ')' || c == '(') {
                return new Pair(c.toString(), i);
            }
            if (c == '+') {
                return new Pair(" + ", i);
            }
            if (c == '-') {
                return new Pair(" - ", i);
            }
            if (c == '/') {
                return new Pair(" / ", i);
            }
            if (c == '*') {
                return new Pair(" * ", i);
            }
            if (c == '~') {
                return new Pair(" ~ ", i);
            }
            if (c == '^') {
                return new Pair(" ^ ", i);
            }
            if (c == 'a'){
                return new Pair(" abs ", i + 2); //TODO +-1
            }
            if (c == 'x' || c == 'y' || c == 'z') {
                return new Pair(c.toString(), i);
            }
            if (Character.isDigit(c)) {
                String number = "";
                int j;
                for (j = i; j < string.length() && Character.isDigit(string.charAt(j)); j++) {
                    number += string.charAt(j);
                }
                return new Pair(number, j - 1);
            }
            return new Pair("EOF", 0);
        }

        public String next() {
            return getToken().str;
        }

        public void consume() {
            position = getToken().value + 1;
        }
    }

    public static Expression3 parse(String expression) throws ParseException {
        Reader reader = new Reader(expression.trim());
        return firstLevel(reader);
    }

    private static Expression3 firstLevel(Reader reader) throws ParseException {
        Expression3 ret = secondLevel(reader);
        String s = reader.next();
        while (s.equals(" + ") || s.equals(" - ")) {
            reader.consume();
            switch (s) {
                case " + ":
                    ret = new Add(ret, secondLevel(reader));
                    break;
                case " - ":
                    ret = new Subtract(ret, secondLevel(reader));
                    break;
            }
            s = reader.next();
            if (s.equals("EOF")) break;
        }
        return ret;
    }

    private static Expression3 secondLevel(Reader reader) throws ParseException {
        Expression3 ret = thirdLevel(reader);
        String s = reader.next();
        while (s.equals(" * ") || s.equals(" / ")) {
            reader.consume();
            switch (s) {
                case " * ":
                    ret = new Multiply(ret, thirdLevel(reader));
                    break;
                case " / ":
                    ret = new Divide(ret, thirdLevel(reader));
                    break;
            }
            s = reader.next();
            if (s.equals("EOF")) break;
        }
        return ret;
    }

    private static Expression3 thirdLevel(Reader reader) throws ParseException {
        String s = reader.next();
        Expression3 ret = null;
        if (s.equals("z") || s.equals("x") || s.equals("y")) {
            reader.consume();
            ret = new Variable(s);
        } else if (Character.isDigit(s.charAt(0))){
            reader.consume();
            ret = new Const((int) Long.parseLong(s));
        } else if (s.equals("(")) {
            reader.consume();
            ret = firstLevel(reader);
            if (!reader.next().equals(")")) {
                throw new ParseException("stuff", ") -- bracket is not closed.");
            } else reader.consume();
        } else if (s.equals(" - ")) {
            reader.consume();
            ret = new UnaryMin(thirdLevel(reader));
        } else if (s.equals(" ~ ")) {
            reader.consume();
            ret = new Not(thirdLevel(reader));
        } else if (s.equals(" abs ")){
            reader.consume();
            ret = new Abs(thirdLevel(reader));
        } else if (s.equals(" ^ ")){
            reader.consume();
            ret = new Power(thirdLevel(reader));
        } else     throw new ParseException("Some strange symbol: " + s + " while parsing " + reader.position);
        return ret;
    }
}