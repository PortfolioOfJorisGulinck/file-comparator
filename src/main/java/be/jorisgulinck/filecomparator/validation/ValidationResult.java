package be.jorisgulinck.filecomparator.validation;

import be.jorisgulinck.filecomparator.models.Transaction;

import java.util.List;

public interface ValidationResult {

    void addErrorMessage(String message);

    void addTransaction(Transaction transaction);

    List<Transaction> getValidatedListOfTransactions();

    List<String> getErrorMessages();

    boolean isValidationError();

    void setValidationError(boolean validationError);
}
