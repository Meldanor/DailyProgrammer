package de.meldanor.dailyprogrammer.challenge189;


public class CharCounter {

    public static void main(String[] args) {
        String input = "The quick brown fox jumps over the lazy dog and the sleeping cat early in the day.";
        int[] counter = new int[26];
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                ++counter[Character.toLowerCase(c) - 'a'];
            }
        }
        for (int i = 0; i < counter.length; ++i) {
            System.out.print((char) ('a' + i) + ": " + counter[i] + " ");
        }
    }
}
