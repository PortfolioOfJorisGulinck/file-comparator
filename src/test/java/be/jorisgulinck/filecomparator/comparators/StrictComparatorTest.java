package be.jorisgulinck.filecomparator.comparators;

import be.jorisgulinck.filecomparator.models.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StrictComparatorTest {

    @Test
    void compareTransactionsStrict() {
        StrictComparator strictComparator = new StrictComparator();
        List<Transaction> listOfTransactions1 = new ArrayList<>(Arrays.asList(new Transaction("ID1"), new Transaction("ID2"), new Transaction("ID3")));
        List<Transaction> listOfTransactions2 = new ArrayList<>(Arrays.asList(new Transaction("ID1"), new Transaction("ID 2"), new Transaction("ID3")));

        List<Transaction> comparedList = strictComparator.compareTransactionsStrict(listOfTransactions1, listOfTransactions2);

        assertEquals(comparedList.get(0).getTransactionId(), "ID2");
    }
}