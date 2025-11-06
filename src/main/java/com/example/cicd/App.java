package com.example.cicd;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class App {

    static Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        int sum = calc.add(2, 3);
        logger.info("Sum(2,3) = " + sum);
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        int s1 = calc.sumUp(numbers); 
        int s2 = calc.addAll(numbers);
        logger.info("sumUp=" + s1 + ", addAll=" + s2);

        logger.info("isPalindrome('Anna')? " + TextUtils.isPalindrome("Anna"));
        logger.info("safeParseInt('42'): " + TextUtils.safeParseInt("42"));
        logger.info("safeParseInt('x'): " + TextUtils.safeParseInt("x"));
    }
}
