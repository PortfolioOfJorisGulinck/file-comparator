package be.jorisgulinck.filecomparator.comparison.strict;

import be.jorisgulinck.filecomparator.models.Transaction;

import java.util.List;

/**
 * Interface for implementations of strict comparing algorithms.
 */
public interface StrictComparator {
    List<Transaction> compareTransactionsStrict(List<Transaction> list1, List<Transaction> list2);
}
