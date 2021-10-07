package be.jorisgulinck.filecomparator.comparison.fuzzy;

import be.jorisgulinck.filecomparator.models.Transaction;

import java.util.List;

/**
 * An interface for implementations of fuzzy searches algorithms.
 */
public interface FuzzyComparator {
    List<Transaction> compareTransactionsFuzzy(Transaction transaction, List<Transaction> transactionsToCompare, int ratio);
}
