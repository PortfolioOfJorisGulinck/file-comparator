package be.jorisgulinck.filecomparator.validation;

import be.jorisgulinck.filecomparator.utilities.ParseUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class CsvValidator {

    // TODO can i refactor this class?

    private final ParseUtilities parseUtilities;

    public ValidationResult validateCsvHeaders(List<String> headers, ValidationResult validationResult) {

        for (String header : headers) {
            Pattern pattern = Pattern.compile("(ProfileName|TransactionDate|TransactionAmount|TransactionNarrative|" +
                    "TransactionDescription|TransactionID|TransactionType|WalletReference)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(header);

            if (!matcher.find()){
                validationResult.setValidationError(true);
                validationResult.addErrorMessage("Please upload a csv file with correct headers: " + header + " is not a valid header name.");
            }
        }
        return validationResult;
    }

    public ValidationResult validateTransactionDate(String date, ValidationResult validationResult) {
        if (!parseUtilities.tryParseDate(date)){
            validationResult.setValidationError(true);
            validationResult.addErrorMessage("There was a problem reading the transaction date.");
        }
        return validationResult;
    }

    public ValidationResult validateTransactionId(String id, ValidationResult validationResult) {
        Pattern pattern = Pattern.compile("\\d{16}");
        Matcher matcher = pattern.matcher(id);
        if (!matcher.find()){
            validationResult.setValidationError(true);
            validationResult.addErrorMessage("There was a problem reading the transaction id.");
        }
        return validationResult;
    }

}
