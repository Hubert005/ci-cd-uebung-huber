package com.example.cicd;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Hinweise:
 * - Ergänze weitere Testfälle (z. B. negative Zahlen, große Werte)
 * - Diskutiere das Verhalten von divide(… , 0) und entscheide in Ü2, ob Exception sinnvoller ist
 */
class CalculatorTest {

    @Test
    void add_shouldSumTwoIntegers() {
        Calculator c = new Calculator();
        assertEquals(5, c.add(2, 3));
        // TODO: weitere Grenzfälle (z. B. negative Zahlen)
    }

    @Test
    void divide_byZero_shouldReturnZero_currentBehavior() {
        Calculator c = new Calculator();
        // Aktuelles (schlechtes) Verhalten absichtlich bestätigt – Refactoring in Ü2
        assertEquals(0, c.divide(10, 0));
    }

    @Test
    void sum_methods_shouldProduceSameResult() {
        Calculator c = new Calculator();
        List<Integer> nums = Arrays.asList(1, 2, 3, null, 4);
        assertEquals(c.sumUp(nums), c.addAll(nums));
    }
}
