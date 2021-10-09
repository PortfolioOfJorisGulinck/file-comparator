package be.jorisgulinck.filecomparator.validation;

import be.jorisgulinck.filecomparator.comparison.fuzzy.FuzzyComparator;
import be.jorisgulinck.filecomparator.comparison.fuzzy.FuzzyComparatorFactory;
import org.springframework.stereotype.Component;

/**
 * Validation helper class for the incoming parameters used by {@link FuzzyComparator}.
 */
@Component
public class FuzzyParamsValidator {

    /**
     * Validates the incoming parameters used by {@link FuzzyComparator}.
     *
     * @param matchingRoutine The name of the <i>matching strategy</i> used by {@link FuzzyComparatorFactory} for the creation
     *                        of the correct implementation of {@link FuzzyComparator}.
     * @param ratio           The value that determines the precision of the search algorithm.
     * @return {@link FuzzyParamsValidationResult}
     */
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

    /**
     * Validates the ratio strings.
     *
     * @param ratio String representing the ratio.
     * @return Boolean value. Returns true if the ratio matches the predetermined ratios.
     */
    private boolean validateRatio(String ratio) {
        return ratio.equals("75") || ratio.equals("80") || ratio.equals("85") ||
                ratio.equals("90") || ratio.equals("95");
    }

    /**
     * Validates the matching routine strings.
     *
     * @param matchingRoutine String representing the matching routine.
     * @return Boolean value. Returns true if the matching routine matches the predetermined matching routines.
     */
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
