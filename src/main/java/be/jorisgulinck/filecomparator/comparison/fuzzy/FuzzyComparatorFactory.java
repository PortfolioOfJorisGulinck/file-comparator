package be.jorisgulinck.filecomparator.comparison.fuzzy;

/**
 * Factory class for the creation of a {@link FuzzyComparator} implementation.
 */
public class FuzzyComparatorFactory {

    /**
     * Factory method for the creation of a {@link FuzzyComparator} implementation.
     *
     * @param matchingRoutine A string value that defines the which {@link FuzzyComparator} implementation will be created.
     * @return An implementation class of {@link FuzzyComparator}.
     */
    public FuzzyComparator createFuzzyComparator(String matchingRoutine) {
        if (matchingRoutine == null) {
            return null;
        }

        if (matchingRoutine.equalsIgnoreCase("Simple Ratio")) {
            return new FuzzySimpleRatioComparator();
        } else if (matchingRoutine.equalsIgnoreCase("Partial Ratio")) {
            return new FuzzyPartialRatioComparator();
        } else if (matchingRoutine.equalsIgnoreCase("Token Sort Ratio")) {
            return new FuzzyTokenSortRatioComparator();
        } else if (matchingRoutine.equalsIgnoreCase("Token Sort Partial Ratio")) {
            return new FuzzyTokenSortPartialRatioComparator();
        } else if (matchingRoutine.equalsIgnoreCase("Token Set Ratio")) {
            return new FuzzyTokenSetRatioComparator();
        } else if (matchingRoutine.equalsIgnoreCase("Token Set Partial Ratio")) {
            return new FuzzyTokenSetPartialRatioComparator();
        }

        return null;
    }
}
