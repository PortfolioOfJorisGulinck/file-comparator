package be.jorisgulinck.filecomparator.comparators;

import be.jorisgulinck.filecomparator.models.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FuzzySimpleRatioComparatorTest {

    // TODO fix the bug in FuzzyRatioComparatorTest

    @Test
    void compareTransactionsFuzzy() {
        FuzzySimpleRatioComparator fuzzySimpleRatioComparator = new FuzzySimpleRatioComparator();
        Transaction transaction = new Transaction("ID2");
        List<Transaction> listOfTransactions = new ArrayList<>(Arrays.asList(new Transaction("ID 1"), new Transaction("ID 2"), new Transaction("ID 3")));

        List<Transaction> comparedList = fuzzySimpleRatioComparator.compareTransactionsFuzzy(transaction, listOfTransactions, 70);

        assertEquals(comparedList.get(0).getTransactionId(), "ID 2");
    }
}