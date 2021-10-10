package be.jorisgulinck.filecomparator.validation;

import be.jorisgulinck.filecomparator.utilities.ParseUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validation helper class for the incoming csv files.
 */
@Component
@RequiredArgsConstructor
public class CsvValidator {

    private final ParseUtilities parseUtilities;

    /**
     * Validates the header names of the csv file.
     *
     * @param validationResult {@link CsvValidationResult} containing the validation data of previous validations.
     * @param headers          Collection of header Strings.
     * @return {@link CsvValidationResult} containing the updated validation data, including the validation data of
     * previous validations.
     */
    public CsvValidationResult validateCsvHeaders(List<String> headers, CsvValidationResult validationResult) {

        for (String header : headers) {
            Pattern pattern = Pattern.compile("(^ProfileName$|^TransactionDate$|^TransactionAmount$|^TransactionNarrative$|" +
                    "^TransactionDescription$|^TransactionID$|^TransactionType$|^WalletReference$)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(header);

            if (!matcher.find()) {
                validationResult.setValidationError(true);
                validationResult.addErrorMessage("Please upload a csv file with correct headers: " + header + " is " +
                        "not a valid header name.");
            }
        }
        return validationResult;
    }

    /**
     * Validates the date string of the csv file.
     *
     * @param validationResult {@link CsvValidationResult} containing the validation data of previous validations.
     * @param date             String representing the TransactionDate.
     * @return {@link CsvValidationResult} containing the updated validation data, including the validation data of
     * previous validations.
     */
    public CsvValidationResult validateTransactionDate(String date, CsvValidationResult validationResult) {
        if (!parseUtilities.tryParseDate(date)) {
            validationResult.setValidationError(true);
            validationResult.addErrorMessage("There was a problem reading the transaction date.");
        }
        return validationResult;
    }

    /**
     * Validates the TransactionId string of the csv file.
     *
     * @param validationResult {@link CsvValidationResult} containing the validation data of previous validations.
     * @param id               String representing the TransactionID.
     * @return {@link CsvValidationResult} containing the updated validation data, including the validation data of
     * previous validations.
     */
    public CsvValidationResult validateTransactionId(String id, CsvValidationResult validationResult) {
        Pattern pattern = Pattern.compile("\\d{16}");
        Matcher matcher = pattern.matcher(id);
        if (!matcher.find()) {
            validationResult.setValidationError(true);
            validationResult.addErrorMessage("There was a problem reading the transaction id.");
        }
        return validationResult;
    }

}
