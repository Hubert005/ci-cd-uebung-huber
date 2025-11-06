package com.example.cicd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    void test() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}
