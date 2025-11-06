package com.example.cicd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NumberUtilsTest {
    @Test
    void isSpecial(){
        assertTrue(NumberUtils.isSpecial(3));
        assertTrue(NumberUtils.isSpecial(5));
        assertTrue(NumberUtils.isSpecial(7));
        assertTrue(NumberUtils.isSpecial(13));
        assertTrue(NumberUtils.isSpecial(17));
        assertFalse(NumberUtils.isSpecial(1));
        assertFalse(NumberUtils.isSpecial(2));
        assertFalse(NumberUtils.isSpecial(4));
        assertFalse(NumberUtils.isSpecial(8));
    }

}
