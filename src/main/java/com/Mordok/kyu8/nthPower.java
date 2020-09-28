package com.Mordok.kyu8;

public class nthPower {
    public static int nthPower1(int[] array, int n) {
        try {
            return (int) Math.pow(array[n], n);
        } catch (ArrayIndexOutOfBoundsException e) {
            return -1;
        }
    }
}
