package be.jorisgulinck.filecomparator.validation;

import be.jorisgulinck.filecomparator.comparison.fuzzy.FuzzyComparator;

import java.util.ArrayList;
import java.util.List;

/**
 * Summarizes the results of the validation of the incoming parameters used by {@link FuzzyComparator},
 * created by {@link FuzzyParamsValidator}.
 */
public class FuzzyParamsValidationResult implements ValidationResult {
    private List<String> errorMessages;
    private boolean validationError;

    public FuzzyParamsValidationResult() {
        this.errorMessages = new ArrayList<>();
    }

    @Override
    public void addErrorMessage(String message) {
        errorMessages.add(message);
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
