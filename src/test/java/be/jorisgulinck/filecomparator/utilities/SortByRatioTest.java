package be.jorisgulinck.filecomparator.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.jorisgulinck.filecomparator.dto.TransactionDto;
import be.jorisgulinck.filecomparator.models.Transaction;
import org.junit.jupiter.api.Test;

class SortByRatioTest {
    @Test
    void testCompare() {
        SortByRatio sortByRatio = new SortByRatio();
        Transaction transaction = mock(Transaction.class);
        when(transaction.getRatio()).thenReturn("42");
        assertEquals(0, sortByRatio.compare(transaction, new Transaction("HJKHKJHF", "42", "foo.txt", "2020-03-01", "10",
                "Transaction Narrative", "Transaction Description", "Transaction Type", "Wallet Reference", "foo.txt", "42")));
        verify(transaction).getRatio();
    }

    @Test
    void testCompare2() {
        SortByRatio sortByRatio = new SortByRatio();
        Transaction transaction = mock(Transaction.class);
        when(transaction.getRatio()).thenReturn("42");
        Transaction otherTransaction = mock(Transaction.class);
        when(otherTransaction.getRatio()).thenReturn("42");
        assertEquals(0, sortByRatio.compare(transaction, otherTransaction));
        verify(transaction).getRatio();
        verify(otherTransaction).getRatio();
    }
}

