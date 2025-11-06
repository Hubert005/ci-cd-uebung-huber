package com.example.cicd;

public class TextUtils {

    private TextUtils() {}

    public static boolean isPalindrome(String input) {
        if (input == null) return false;
        String reversed = new StringBuilder(input).reverse().toString();
        return input.equalsIgnoreCase(reversed);
    }

    // Leerer Catch-Block + sehr generische Exception
    public static int safeParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            // absichtlich leer gelassen
        }
        return 0; // „Fallback“ – diskutabel
    }
}
