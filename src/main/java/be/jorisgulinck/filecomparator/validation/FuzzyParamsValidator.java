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
        return ratio.equals("75") || ratio.equals("80") || ratio.equals("85") ||
                ratio.equals("90") || ratio.equals("95");
    }

    private boolean validateMatchingRoutine(String matchingRoutine) {
        return matchingRoutine.equals("Simple Ratio") ||
                matchingRoutine.equals("Partial Ratio") ||
                matchingRoutine.equals("Token Sort Ratio") ||
                matchingRoutine.equals("Token Sort Partial Ratio") ||
                matchingRoutine.equals("Token Set Ratio") ||
                matchingRoutine.equals("Token Set Partial Ratio") ||
                matchingRoutine.equals("Weighted Ratio");
    }
}
