package de.meldanor.dailyprogrammer.challenge188;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Locale;

public class UnifiedDateFormat {

    public static void main(String[] args) throws Exception {
        UnifiedDateFormat formatter = new UnifiedDateFormat();
        Files.lines(new File("src/main/resources/challenge188/dates.txt").toPath()).forEach(s -> {
            LocalDate date = formatter.parse(s);
            System.out.println(date);
        });
        System.out.println(swapCounter);
        System.out.println(Arrays.toString(hitcounter));
    }

    private DateTimeFormatter[] formats;

    public UnifiedDateFormat() {
        formats = new DateTimeFormatter[6];
        formats[0] = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formats[1] = DateTimeFormatter.ofPattern("MM'/'dd'/'yy");
        formats[2] = DateTimeFormatter.ofPattern("MM'#'yy'#'dd");
        formats[3] = DateTimeFormatter.ofPattern("dd'*'MM'*'yyyy");
        formats[4] = DateTimeFormatter.ofPattern("MMM' 'dd', 'yy").withLocale(Locale.ENGLISH);
        formats[5] = DateTimeFormatter.ofPattern("MMM' 'dd', 'yyyy").withLocale(Locale.ENGLISH);
    }

    private static int[] hitcounter = new int[6];

    public LocalDate parse(String s) {
        for (int i = 0; i < formats.length; ++i) {
            DateTimeFormatter formatter = formats[i];
            try {
                LocalDate d = LocalDate.parse(s, formatter);
                if (i != 0)
                    swap(i - 1, i);
                if (d.getYear() > 2049)
                    d = d.minusYears(100);
                ++hitcounter[i];
                return d;
            } catch (DateTimeParseException ignore) {
                // DO NOTING
            }
        }
        throw new IllegalArgumentException("Unsupported format for " + s);
    }

    private static int swapCounter = 0;

    private void swap(int i, int j) {
        ++swapCounter;
        DateTimeFormatter tmp = formats[i];
        formats[i] = formats[j];
        formats[j] = tmp;
    }
}
