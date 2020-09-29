package com.Mordok.kyu6;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;

public class PasswordCrackerTest {
    @Test
    public void test() {
        assertEquals("code", PasswordCracker.passwordCracker("e6fb06210fafc02fd7479ddbed2d042cc3a5155e"));
        assertEquals("test", PasswordCracker.passwordCracker("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3"));
    }
}
