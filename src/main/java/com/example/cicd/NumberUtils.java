package com.example.cicd;

public class NumberUtils {

    public static int DEFAULT_LIMIT = 10;

    private int unusedCounter = 0; 

    // Langer, leicht „unnötig“ komplexer Code mit Magic Numbers
    public static boolean isSpecial(int n) {
        // Magic numbers 3, 5, 7
        if (n % 3 == 0 || n % 5 == 0 || n % 7 == 0) {
            return true;
        }
        switch (n) {
            case 13:
            case 17:
                return true; // weitere Magic numbers
            default:
                break; // kein echter Default-Case-Inhalt
        }
        return false;
    }
}
