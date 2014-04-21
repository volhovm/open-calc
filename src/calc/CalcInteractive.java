//package calc;
//
//import calc.calclib.Expression3;
//import calc.calclib.numsystems.CalcInt;
//import calc.parser.ExpressionParser;
//import calc.parser.ParseException;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.HashMap;
//
///**
// * @author volhovm
// *         Created on 15.04.14
// */
//
//
//public class CalcInteractive {
//    public static void main(String[] args) throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        HashMap<String, Expression3<CalcInt>> mem = new HashMap<>();
//        while (true) {
//            String s = reader.readLine();
//            try {
//                if (s.startsWith("let")) {
//                    s = s.substring(4);
//                    mem.put(s.split("=")[0],
//                            ExpressionParser.parse(new CalcInt(-1), s.split("=")[1]));
//                } else {
//                    Expression3<CalcInt> z = ExpressionParser.parse(new CalcInt(-1), s);
//                    System.out.println(z);
//                }
//            } catch (ParseException e) {
//                e.getMessage();
//            }
//        }
//    }
//}
