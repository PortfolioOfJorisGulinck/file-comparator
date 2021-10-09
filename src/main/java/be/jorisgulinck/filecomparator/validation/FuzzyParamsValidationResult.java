package be.jorisgulinck.filecomparator.validation;

import java.util.ArrayList;
import java.util.List;

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
