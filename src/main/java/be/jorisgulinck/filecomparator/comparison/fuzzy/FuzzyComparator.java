package be.jorisgulinck.filecomparator.comparison.fuzzy;

import be.jorisgulinck.filecomparator.models.Transaction;

import java.util.List;

/**
 * Interface for implementations of fuzzy search algorithms.
 */
public interface FuzzyComparator {
    List<Transaction> compareTransactionsFuzzy(Transaction transaction, List<Transaction> transactionsToCompare, int ratio);
}
