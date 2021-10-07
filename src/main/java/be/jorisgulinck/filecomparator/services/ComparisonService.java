package be.jorisgulinck.filecomparator.services;

import be.jorisgulinck.filecomparator.comparison.fuzzy.FuzzyComparator;
import be.jorisgulinck.filecomparator.comparison.fuzzy.FuzzyComparatorFactory;
import be.jorisgulinck.filecomparator.comparison.strict.StrictComparator;
import be.jorisgulinck.filecomparator.comparison.strict.StrictEqualsComparator;
import be.jorisgulinck.filecomparator.models.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComparisonService {

    /** Compares two collections of Transaction for similarity using the TransactionComparator class */
    public List<Transaction> compareStrict(List<Transaction> listOfTransactions1, List<Transaction> listOfTransactions2) {
        StrictComparator strictComparator = new StrictEqualsComparator();
        return strictComparator.compareTransactionsStrict(listOfTransactions1, listOfTransactions2);
    }

    /**
     * Compares a collection of Transaction with a Transaction for similarity in a fuzzy way using
     * the TransactionComparator class
     */
    public List<Transaction> compareFuzzy(Transaction transaction, List<Transaction> listOfTransactions, String matchingRoutine, int ratio) {
        FuzzyComparatorFactory fuzzyComparatorFactory = new FuzzyComparatorFactory();
        FuzzyComparator fuzzyComparator = fuzzyComparatorFactory.createFuzzyComparator(matchingRoutine);
        return fuzzyComparator.compareTransactionsFuzzy(transaction, listOfTransactions, ratio);
    }
}
