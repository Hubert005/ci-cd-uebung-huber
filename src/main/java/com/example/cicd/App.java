package com.example.cicd;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        int sum = calc.add(2, 3);
        System.out.println("Sum(2,3) = " + sum);
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        int s1 = calc.sumUp(numbers); 
        int s2 = calc.addAll(numbers);   
        System.out.println("sumUp=" + s1 + ", addAll=" + s2);

        System.out.println("isPalindrome('Anna')? " + TextUtils.isPalindrome("Anna"));
        System.out.println("safeParseInt('42'): " + TextUtils.safeParseInt("42"));
        System.out.println("safeParseInt('x'): " + TextUtils.safeParseInt("x"));
    }
}
