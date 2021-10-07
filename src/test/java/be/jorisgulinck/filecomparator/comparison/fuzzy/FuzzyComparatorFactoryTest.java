package be.jorisgulinck.filecomparator.comparison.fuzzy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuzzyComparatorFactoryTest {

    @Test
    void getFuzzyComparator() {
        FuzzyComparatorFactory factory = new FuzzyComparatorFactory();
        FuzzyComparator comparator = factory.createFuzzyComparator("Partial Ratio");

        assertEquals(comparator.getClass(), FuzzyPartialRatioComparator.class);
    }
}