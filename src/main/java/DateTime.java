import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTime {
    private static final DateTimeFormatter INPUT_FORMAT1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter INPUT_FORMAT2 = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    // Add possible input format
    
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    // Parses a string into LocalDateTime
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, INPUT_FORMAT1); // yyyy-MM-dd HHmm
        } catch (DateTimeParseException e1) {
            try {
                return LocalDateTime.parse(dateTimeStr, INPUT_FORMAT2); // d/M/yyyy HHmm
            } catch (DateTimeParseException e2) {
                throw new IllegalArgumentException("Invalid date format. Use 'yyyy-MM-dd HHmm' or 'd/M/yyyy HHmm'.");
            }
        }
    }

    // Formats LocalDateTime into a readable string
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(OUTPUT_FORMAT);
    }
}
