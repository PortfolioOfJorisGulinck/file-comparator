package be.jorisgulinck.filecomparator.validation;

import be.jorisgulinck.filecomparator.models.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Summarizes the results of the validation of a csv file, created by {@link CsvValidator}.
 */
public class CsvValidationResult implements ValidationResult {

    private List<Transaction> validatedListOfTransactions;
    private List<String> errorMessages;
    private boolean validationError;

    public CsvValidationResult() {
        this.errorMessages = new ArrayList<>();
        this.validatedListOfTransactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        validatedListOfTransactions.add(transaction);
    }

    public List<Transaction> getValidatedListOfTransactions() {
        return validatedListOfTransactions;
    }

    @Override
    public void addErrorMessage(String message) {
        this.errorMessages.add(message);
    }

    @Override
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    @Override
    public boolean isValidationError() {
        return validationError;
    }

    @Override
    public void setValidationError(boolean validationError) {
        this.validationError = validationError;
    }
}
