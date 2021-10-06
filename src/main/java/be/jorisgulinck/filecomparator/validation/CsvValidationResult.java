package be.jorisgulinck.filecomparator.validation;

import be.jorisgulinck.filecomparator.models.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CsvValidationResult {

    public List<Transaction> validatedListOfTransactions;
    private List<String> errorMessages;
    private boolean validationError;

    public CsvValidationResult() {
        this.errorMessages = new ArrayList<>();
        this.validatedListOfTransactions = new ArrayList<>();
    }

    public void addErrorMessage(String message) {
        this.errorMessages.add(message);
    }

    public void addTransaction(Transaction transaction) {
        validatedListOfTransactions.add(transaction);
    }
}
