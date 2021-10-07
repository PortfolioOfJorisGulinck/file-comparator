package be.jorisgulinck.filecomparator.validation;

import org.springframework.stereotype.Component;

@Component
public class FuzzyParamsValidator {

    public FuzzyParamsValidationResult validateFuzzyParams(String matchingRoutine, String ratio) {
        FuzzyParamsValidationResult validationResult = new FuzzyParamsValidationResult();
        if (validateMatchingRoutine(matchingRoutine) && validateRatio(ratio)) {
            validationResult.setValidationError(false);
        } else {
            validationResult.setValidationError(true);
            validationResult.addErrorMessage("There was a problem with parsing of the Fuzzy search parameters.");
        }
        return validationResult;
    }

    private boolean validateRatio(String ratio) {
        return ratio.contains("75") || ratio.contains("80") || ratio.contains("85") ||
                ratio.contains("90") || ratio.contains("95");
    }

    private boolean validateMatchingRoutine(String matchingRoutine) {
        return matchingRoutine.contains("Simple Ratio") ||
                matchingRoutine.contains("Partial Ratio") ||
                matchingRoutine.contains("Token Sort Ratio") ||
                matchingRoutine.contains("Token Sort Partial Ratio") ||
                matchingRoutine.contains("Token Set Ratio") ||
                matchingRoutine.contains("Token Set Partial Ratio") ||
                matchingRoutine.contains("Weighted Ratio");
    }
}
