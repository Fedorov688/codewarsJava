package com.Mordok.kyu7;

public class ValidatePin {
    public static boolean validatePin(String pin) {
        // Your code here...
        if (pin.length() == 4 || pin.length() == 6) {
            for (char i : pin.toCharArray()) {
                if (!Character.isDigit(i)) return false;
            }
            return true;
        } else return false;
    }
}
