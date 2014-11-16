package de.meldanor.dailyprogrammer.challenge188;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BoxPlotGenerator {

    public static void main(String[] args) {
        BoxPlotGenerator plotGenerator = new BoxPlotGenerator();


        List<Integer> numbers = plotGenerator.parseString("7 12 21 28 28 29 30 32 34 35 35 36 38 39 40 40 42 44 45 46 47 49 50 53 55 56 59 63 80 191");
        String s = plotGenerator.createBoxPlot(numbers);
        System.out.println(s);
    }


    public BoxPlotGenerator() {

    }

    private static final Pattern PARSE_PATTERN = Pattern.compile("\\s");

    public List<Integer> parseString(String s) {
        List<Integer> res = new ArrayList<>();
        String[] split = PARSE_PATTERN.split(s);
        for (int i = 0; i < split.length; ++i) {
            res.add(Integer.valueOf(split[i]));
        }

        return res;
    }

    public String createBoxPlot(List<Integer> numbers) {
        numbers.sort(Integer::compareTo);
        int firstQuartile = getQuartileIndex(1, numbers);
        int median = getQuartileIndex(2, numbers);
        int secondQuartile = getQuartileIndex(3, numbers);
        return drawBoxPlot(numbers, firstQuartile, median, secondQuartile);
    }

    private String drawBoxPlot(List<Integer> numbers, int... quartiles) {

        StringBuilder numberString = new StringBuilder();
        StringBuilder quartileString = new StringBuilder();

        int border = quartiles.length;
        printQuartile(1, numbers.subList(0, quartiles[0]), numberString, quartileString);
        for (int i = 1; i < border; ++i) {
            printQuartile(i + 1, numbers.subList(quartiles[i - 1], quartiles[i]), numberString, quartileString);
        }
        for (int i = quartiles[border - 1]; i < numbers.size(); ++i) {
            String s = String.valueOf(numbers.get(i));
            numberString.append(s).append(' ');
            appendPlaceholder(s, quartileString);
        }
        return numberString.append(System.lineSeparator()).append(quartileString.toString()).toString();
    }

    private void printQuartile(int quartile, List<Integer> subList, StringBuilder numberString, StringBuilder quartileString) {
        String s;
        int border = subList.size() - 1;
        for (int i = 0; i < border; ++i) {
            s = String.valueOf(subList.get(i));
            numberString.append(s).append(' ');
            appendPlaceholder(s, quartileString);
        }
        s = String.valueOf(subList.get(border));
        numberString.append(s).append(' ');

        if (s.length() == 1)
            quartileString.append(quartile);
        else
            quartileString.append('Q').append(quartile);
    }

    private void appendPlaceholder(String numberString, StringBuilder quartileString) {
        int len = numberString.length() + 1;
        for (int i = 0; i < len; ++i)
            quartileString.append('-');
    }

    private int getQuartileIndex(int quartil, List<Integer> numbers) {

        return (int) (Math.ceil(numbers.size() / 4.0) * quartil);
    }


}
