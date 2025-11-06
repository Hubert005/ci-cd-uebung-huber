package com.example.cicd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NumberUtilsTest {
    @Test
    void isSpecial(){
        assertTrue(NumberUtils.isSpecial(3));
        assertTrue(NumberUtils.isSpecial(17));
        assertFalse(NumberUtils.isSpecial(1));
        assertFalse(NumberUtils.isSpecial(2));
    }

}
