package be.jorisgulinck.filecomparator.validation;

import java.util.List;

public interface ValidationResult {

    void addErrorMessage(String message);

    List<String> getErrorMessages();

    boolean isValidationError();

    void setValidationError(boolean validationError);
}
