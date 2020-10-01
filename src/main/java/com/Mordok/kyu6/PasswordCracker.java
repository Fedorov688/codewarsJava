package com.Mordok.kyu6;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class PasswordCracker {
    public static String passwordCracker(String hash) {
//        int[] wordIndex = {0, 0, 0, 0, 0};
//        int[] maxWord = {max, max, max, max, max};
        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        int max = alphabet.length; // max = 26
        ParallelHashGen.setResult("");
        ParallelHashGen.setmFinish(false);
        List<Thread> threads = new ArrayList<>(Collections.<Thread>emptyList());

//        ParallelHashGen firstTread = new ParallelHashGen(); // 3.26s -> 1.67 -> 1.53
        int plus = 10;
        int maxPlus = max%plus;
        int residual = max - maxPlus;


        threads.add(genTread(hash, alphabet, new int[]{0}, new int[]{max})); // word.length = 1

        for (int i = 1; i < max/plus*plus; i += plus) {
//            System.out.println("i = " + i);
            threads.add(genTread(hash, alphabet, new int[]{i, 0}, new int[]{i + plus-1, max})); // word.length = 2
            threads.add(genTread(hash, alphabet, new int[]{i, 1, 0}, new int[]{i + plus-1, max, max})); // word.length = 3
            threads.add(genTread(hash, alphabet, new int[]{i, 1, 1, 0}, new int[]{i + plus-1, max, max, max})); // word.length = 4
            threads.add(genTread(hash, alphabet, new int[]{i, 1, 1, 1, 0}, new int[]{i + plus-1, max, max, max, max})); // word.length = 5
        }
        if (maxPlus != 0) {
//            System.out.println("residual = " + residual);
            threads.add(genTread(hash, alphabet, new int[]{residual, 0}, new int[]{max, max})); // word.length = 2
            threads.add(genTread(hash, alphabet, new int[]{residual, 1, 0}, new int[]{max, max, max})); // word.length = 3
            threads.add(genTread(hash, alphabet, new int[]{residual, 1, 1, 0}, new int[]{max, max, max, max})); // word.length = 4
            threads.add(genTread(hash, alphabet, new int[]{residual, 1, 1, 1, 0}, new int[]{max, max, max, max, max})); // word.length = 5
        }

        // Ожидание окончания всех потоков
        try {
            for (Thread thread: threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ParallelHashGen.getResult();
    }

    public static Thread genTread(String hash, char[] alphabet, int[] startWordIndex, int[] endWordIndex) {
        ParallelHashGen thread = new ParallelHashGen();
        thread.hash = hash;
        thread.alphabet = alphabet;
        thread.startWordIndex = startWordIndex;
        thread.endWordIndex = endWordIndex;
        thread.start();
        return thread;
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

    private static class ParallelHashGen extends Thread {
        String hash;
        char[] alphabet;
        int[] startWordIndex;
        int[] endWordIndex;

        public static boolean getmFinish() {
            return mFinish;
        }

        public static void setmFinish(boolean mFinish) {
            ParallelHashGen.mFinish = mFinish;
        }

        private static volatile boolean mFinish;

        public static String getResult() {
            return result;
        }

        public static void setResult(String result) {
            ParallelHashGen.result = result;
        }

        private static volatile String result = "";

        @Override
        public void run() {

            String tmpWord;
            do {
                tmpWord = "";
                startWordIndex[startWordIndex.length - 1] += 1;
                for (int charIndex = startWordIndex.length - 1; charIndex > 0; charIndex--) {
                    if (startWordIndex[charIndex] > alphabet.length) {
                        startWordIndex[charIndex - 1] += 1;
                        startWordIndex[charIndex] = 1;
                    }
                }
                for (int index:
                        startWordIndex) {
                    if (index > 0) {
                        tmpWord += alphabet[index - 1];
                    }
                }
                if (genHash(tmpWord).equals(hash)) {
                    synchronized (ParallelHashGen.class) {
                        result = tmpWord;
                        mFinish = true;
                    }
                }
                if (ParallelHashGen.getmFinish() != mFinish && !mFinish) { mFinish = ParallelHashGen.getmFinish(); }
            } while (!Arrays.equals(startWordIndex, endWordIndex) && !mFinish);
        }
    }
}
