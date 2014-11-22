package de.meldanor.dailyprogrammer.challenge189;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RomanNumbers {

    public static void main(String[] args) {
        RomanNumbers r = new RomanNumbers();
        System.out.println(r.parseRoman("XII"));
        System.out.println(r.parseRoman("MDCCLXXVI"));
        System.out.println(r.parseRoman("IX"));
        System.out.println(r.parseRoman("XCIV"));
        System.out.println(r.parseRoman("IV"));
        System.out.println(r.parseRoman("XXXIV"));
        System.out.println(r.parseRoman("CCLXVII"));
        System.out.println(r.parseRoman("DCCLXIV"));
        System.out.println(r.parseRoman("CMLXXXVII"));
        System.out.println(r.parseRoman("MCMLXXXIII"));
        System.out.println(r.parseRoman("MMXIV"));
        System.out.println(r.parseRoman("MMMM"));
        System.out.println(r.parseRoman("MMMMCMXCIX"));
    }

    private static final Map<Character, Integer> romanLetterValues;

    static {
        Map<Character, Integer> tmp = new HashMap<>();
        tmp.put('I', 1);
        tmp.put('V', 5);
        tmp.put('X', 10);
        tmp.put('L', 50);
        tmp.put('C', 100);
        tmp.put('D', 500);
        tmp.put('M', 1000);
        romanLetterValues = Collections.unmodifiableMap(tmp);
    }

    public RomanNumbers() {

    }

    public int parseRoman(String input) {
        int sum = 0;
        int max = input.length() - 1;
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            if (i < max && canSubtract(c)) {
                char n = input.charAt(i + 1);
                if (isSubtraction(c, n)) {
                    sum += (valueOfRomanLetter(n) - valueOfRomanLetter(c));
                    ++i;
                } else {
                    sum += valueOfRomanLetter(c);
                }
            } else {
                sum += valueOfRomanLetter(c);
            }
        }
        return sum;
    }

    private int valueOfRomanLetter(char letter) {
        return romanLetterValues.get(letter);
    }

    private boolean canSubtract(char letter) {
        return letter == 'I' || letter == 'X' || letter == 'C';
    }

    private boolean isSubtraction(char cur, char next) {
        return (cur == 'I' && (next == 'X' || next == 'V')) ||
                (cur == 'X' && (next == 'L' || next == 'C')) ||
                (cur == 'C' && (next == 'D' || next == 'M'));
    }
}
