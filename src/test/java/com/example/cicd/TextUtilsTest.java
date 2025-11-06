package com.example.cicd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Hinweis:
 * - Dieser Test wird voraussichtlich FAILEN, weil isPalindrome '==' nutzt.
 * - In Uebung 2 sollte dieser Bug gefixt werden.
 */
class TextUtilsTest {

    @Test
    void safeParseInt_validNumber_returnsValue() {
        assertEquals(42, TextUtils.safeParseInt("42"));
    }

    @Test
    void safeParseInt_invalidNumber_returnsZero() {
        assertEquals(0, TextUtils.safeParseInt("x"));
    }

    @Test
    void isPalindrome_caseInsensitive_expectedTrue() {
        assertTrue(TextUtils.isPalindrome("Anna"));
    }
}
