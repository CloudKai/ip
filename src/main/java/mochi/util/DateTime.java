package mochi.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import mochi.exception.MochiException;

/**
 * Utility class for parsing and formatting dates and times in multiple formats.
 * It provides methods to parse a string into a LocalDateTime object and
 * format a LocalDateTime object into a (MM dd yyyy) string.
 */
public class DateTime {
    private static final List<DateTimeFormatter> INPUT_FORMATS = List.of(
        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"), // 2023-12-31 2359
        DateTimeFormatter.ofPattern("d/M/yyyy HHmm"), // 31/12/2023 2359
        DateTimeFormatter.ofPattern("d-M-yyyy HHmm"), // 31-12-2023 2359
        DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma")
    );

    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    /**
     * Parses a date-time string into a LocalDateTime object.
     * The method tries to parse the input string using multiple formats.
     *
     * @param dateTimeStr The date-time string to parse.
     * @return The corresponding LocalDateTime object.
     * @throws IllegalArgumentException If the string does not match any of the supported formats.
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) throws MochiException {
        for (DateTimeFormatter formatter : INPUT_FORMATS) {
            try {
                return LocalDateTime.parse(dateTimeStr, formatter);
            } catch (DateTimeParseException ignored) {
                // Try next format
            }
        }
        throw new MochiException("I can't recognise this date format");
    }

    /**
     * Formats a LocalDateTime object into a (MM dd yyyy) string.
     * The method formats the given LocalDateTime object using a predefined format (MMM dd yyyy, h:mma)
     *
     * @param dateTime The LocalDateTime object to format.
     * @return A string representing the formatted date and time.
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(OUTPUT_FORMAT);
    }
}
