package kaim.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DateTime {
    private static final List<DateTimeFormatter> INPUT_FORMATS = List.of(
        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),   // 2023-12-31 2359
        DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),     // 31/12/2023 2359
        DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma") // Dec 31 2023, 11:59PM
    );

    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    // Parses a string into LocalDateTime using multiple formats
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        for (DateTimeFormatter formatter : INPUT_FORMATS) {
            try {
                return LocalDateTime.parse(dateTimeStr, formatter);
            } catch (DateTimeParseException ignored) {
                // Try next format
            }
        }
        throw new IllegalArgumentException("Invalid date format");
    }

    // Formats LocalDateTime into a readable string
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(OUTPUT_FORMAT);
    }
}
