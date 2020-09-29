package com.Mordok.kyu7;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;

public class ValidatePinTest {
    @Test
    public void validPins() {
        assertEquals(true, ValidatePin.validatePin("1234"));
        assertEquals(true, ValidatePin.validatePin("0000"));
        assertEquals(true, ValidatePin.validatePin("1111"));
        assertEquals(true, ValidatePin.validatePin("123456"));
        assertEquals(true, ValidatePin.validatePin("098765"));
        assertEquals(true, ValidatePin.validatePin("000000"));
        assertEquals(true, ValidatePin.validatePin("090909"));
    }

    @Test
    public void nonDigitCharacters() {
        assertEquals(false, ValidatePin.validatePin("a234"));
        assertEquals(false, ValidatePin.validatePin(".234"));
    }

    @Test
    public void invalidLengths() {
        assertEquals(false, ValidatePin.validatePin("1"));
        assertEquals(false, ValidatePin.validatePin("12"));
        assertEquals(false, ValidatePin.validatePin("123"));
        assertEquals(false, ValidatePin.validatePin("12345"));
        assertEquals(false, ValidatePin.validatePin("1234567"));
        assertEquals(false, ValidatePin.validatePin("-1234"));
        assertEquals(false, ValidatePin.validatePin("1.234"));
        assertEquals(false, ValidatePin.validatePin("00000000"));
    }
}
