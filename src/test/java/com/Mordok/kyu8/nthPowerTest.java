package com.Mordok.kyu8;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;

public class nthPowerTest {
    @Test
    public void basicTests() {
        assertEquals(-1, nthPower.nthPower1(new int[] {1,2}, 2));
        assertEquals(8, nthPower.nthPower1(new int[] {3,1,2,2}, 3));
        assertEquals(4, nthPower.nthPower1(new int[] {3,1,2}, 2));
    }
}
