package be.jorisgulinck.filecomparator.validation;

import be.jorisgulinck.filecomparator.utilities.ParseUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvValidator {

    // TODO can i refactor this class?

    private final ParseUtilities parseUtilities;

    public boolean validateCsvHeaders(List<String> headers) {
        // TODO fix this bug
        boolean error = false;

        for (String header : headers) {
            if (!header.equals("ProfileName") || !header.equals("TransactionDate") || !header.equals("TransactionAmount") ||
                    !header.equals("TransactionNarrative") || !header.equals("TransactionDescription") || !header.equals("TransactionID")
                    || !header.equals("TransactionType") || !header.equals("WalletReference")) {
                error = true;
            }
        }
        return error;
    }


    /**
     * Validates the csv file data for the valid properties. Not included: transactionNarrative
     */
    public CsvValidationResult validateCsvFile(CsvValidationResult csvValidationResult) {

        csvValidationResult.getValidatedListOfTransactions().forEach(transaction -> {
            if (validateProfileName(transaction.getProfileName())) {
                csvValidationResult.setValidationError(true);
                csvValidationResult.addErrorMessage("There was a problem reading the profile name.");
            }

            if (validateTransactionDate(transaction.getTransactionDate())) {
                csvValidationResult.setValidationError(true);
                csvValidationResult.addErrorMessage("There was a problem reading the transaction date.");
            }

            if (validateTransactionAmount(transaction.getTransactionAmount())) {
                csvValidationResult.setValidationError(true);
                csvValidationResult.addErrorMessage("There was a problem reading the transaction amount.");
            }

            if (validateTransactionDescription(transaction.getTransactionDescription())) {
                csvValidationResult.setValidationError(true);
                csvValidationResult.addErrorMessage("There was a problem reading the transaction description.");
            }

            if (validateTransactionID(transaction.getTransactionId())) {
                csvValidationResult.setValidationError(true);
                csvValidationResult.addErrorMessage("There was a problem reading the transaction id.");
            }

            if (validateTransactionType(transaction.getTransactionType())) {
                csvValidationResult.setValidationError(true);
                csvValidationResult.addErrorMessage("There was a problem reading the transaction type.");
            }

            if (validateWalletReference(transaction.getWalletReference())) {
                csvValidationResult.setValidationError(true);
                csvValidationResult.addErrorMessage("There was a problem reading the wallet reference.");
            }
        });

        return csvValidationResult;
    }

    private boolean validateProfileName(String name) {
        return !(name.equals("Card Campaign"));
    }

    private boolean validateTransactionDate(String date) {
        return !(parseUtilities.tryParseDate(date));
    }

    private boolean validateTransactionAmount(String amount) {
        return !(parseUtilities.tryParseInt(amount));
    }

    private boolean validateTransactionDescription(String description) {
        // TODO fix this bug
        //return !(description.equals("DEDUCT"));
        return false;
    }

    private boolean validateTransactionID(String id) {
        // TODO fix this bug
        //return (!(parseUtilities.tryParseInt(id) && !(id.length() == 16)));
        return false;
    }

    private boolean validateTransactionType(String type) {
        return !(type.equals("0") || type.equals("1"));
    }

    private boolean validateWalletReference(String reference) {
        // TODO fix this bug
        // return !(reference.startsWith("P_N") && !(reference.length() == 34));
        return false;
    }
}
