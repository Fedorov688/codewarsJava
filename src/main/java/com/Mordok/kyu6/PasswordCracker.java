package com.Mordok.kyu6;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;

public class PasswordCracker {
    public static String passwordCracker(String hash) {
        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        int[] wordIndex = {0, 0, 0, 0, 0};
        int max = alphabet.length;
        int[] maxWord = {max, max, max, max, max};
        String tmpWord;
        do {
            tmpWord = "";
            wordIndex[wordIndex.length - 1] += 1;
            for (int charIndex = wordIndex.length - 1; charIndex > 0; charIndex--) {
                if (wordIndex[charIndex] > alphabet.length) {
                    wordIndex[charIndex - 1] += 1;
                    wordIndex[charIndex] = 1;
                }
            }
            for (int index:
                 wordIndex) {
                if (index > 0) {
                    tmpWord += alphabet[index - 1];
                }
            }
        } while (!genHash(tmpWord).equals(hash) || Arrays.equals(wordIndex, maxWord));
        return tmpWord;
    }

    private static String genHash(String passParaphrase) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(passParaphrase.getBytes());
            return byteToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException");
            return "";
        }
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
