package be.jorisgulinck.filecomparator.utilities;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utilities class that tries to parse Strings of data.
 */
@Getter
@Component
public class ParseUtilities {
    /**
     * This is a string that the DateTimeFormatter uses to parse a string to a LocalDateTime object
     * The string can be build upon so more formatting styles, coming from the csv file, can be parsed
     */
    private final String dateTimeFormatterString = "[M/dd/yyyy H:mm][yyyy-MM-dd HH:mm:ss]";

    /**
     * Tries to parse a String to an Integer.
     *
     * @param integerString The String that represents a numeric value.
     * @return A boolean value. Returns true if the string can be parsed to an Integer object.
     */
    public boolean tryParseInt(String integerString) {
        if (integerString == null) {
            return false;
        }
        try {
            int n = Integer.parseInt(integerString);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * Tries to parse a String to {@link LocalDateTime}.
     *
     * @param dateString The String that represents a date and time.
     * @return A boolean value. Returns true if the string can be parsed to a {@link LocalDateTime} object.
     */
    public boolean tryParseDate(String dateString) {
        try {
            LocalDateTime t = LocalDateTime.parse((dateString), DateTimeFormatter.ofPattern(dateTimeFormatterString));
        } catch (DateTimeParseException ex) {
            return false;
        }
        return true;
    }

    /*
    public boolean tryParseEnotation(String numberString) {
        if (numberString == null) {
            return false;
        }
        try {
            double formattedNumber = Double.valueOf(numberString).longValue();
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
    */
}
