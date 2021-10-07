package be.jorisgulinck.filecomparator.services;

import be.jorisgulinck.filecomparator.comparison.fuzzy.FuzzySimpleRatioComparator;
import be.jorisgulinck.filecomparator.comparison.strict.StrictEqualsComparator;
import be.jorisgulinck.filecomparator.models.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComparisonServiceTest {

    // TODO fix the bug in ComparisonServiceTest

    @Mock
    private FuzzySimpleRatioComparator fuzzySimpleRatioComparator;

    @Mock
    private StrictEqualsComparator strictEqualsComparator;

    @InjectMocks
    private ComparisonService comparisonService;

    private List<Transaction> listOfTransactions1;
    private List<Transaction> listOfTransactions2;
    private List<Transaction> filteredList;

    @BeforeEach
    void setUp() {
       listOfTransactions1 = new ArrayList<>(Arrays.asList(new Transaction("ID1"), new Transaction("ID2"), new Transaction("ID3")));
       listOfTransactions2 = new ArrayList<>(Arrays.asList(new Transaction("ID1"), new Transaction("ID 2"), new Transaction("ID 3")));
       filteredList = new ArrayList<>(Arrays.asList(new Transaction("ID 2")));
    }

    @Test
    void compareStrict() {
        when(strictEqualsComparator.compareTransactionsStrict(listOfTransactions1, listOfTransactions2)).thenReturn(filteredList);
        List<Transaction> comparedList = comparisonService.compareStrict(listOfTransactions1, listOfTransactions2);

        assertEquals(comparedList.get(0).getTransactionId(), filteredList.get(0).getTransactionId());
    }

    @Test
    void compareFuzzy() {
        Transaction transaction = new Transaction("ID2");
        when(fuzzySimpleRatioComparator.compareTransactionsFuzzy(transaction, listOfTransactions2, 80)).thenReturn(filteredList);
        List<Transaction> comparedList = comparisonService.compareStrict(listOfTransactions1, listOfTransactions2);

        assertEquals(comparedList.get(0).getTransactionId(), filteredList.get(0).getTransactionId());
    }

}