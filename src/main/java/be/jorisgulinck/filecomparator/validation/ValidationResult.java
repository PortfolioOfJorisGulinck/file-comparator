package be.jorisgulinck.filecomparator.validation;

import java.util.List;

/**
 * Interface for implementations of validation results.
 */
public interface ValidationResult {

    void addErrorMessage(String message);

    List<String> getErrorMessages();

    boolean isValidationError();

    void setValidationError(boolean validationError);
}
