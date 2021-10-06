package be.jorisgulinck.filecomparator.comparators;

public class FuzzyComparatorFactory {

    public FuzzyComparator getFuzzyComparator(String matchingRoutine) {
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
        } else if (matchingRoutine.equalsIgnoreCase("Weighted Ratio")) {
            return new FuzzyTokenSetPartialRatioComparator();
        }

        return null;
    }
}
