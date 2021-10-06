package be.jorisgulinck.filecomparator.services;

import be.jorisgulinck.filecomparator.comparators.FuzzyComparator;
import be.jorisgulinck.filecomparator.comparators.FuzzyComparatorFactory;
import be.jorisgulinck.filecomparator.comparators.StrictComparator;
import be.jorisgulinck.filecomparator.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComparisonService {

    private final StrictComparator strictComparator;

    /** Compares two collections of Transaction for similarity using the TransactionComparator class */
    public List<Transaction> compareStrict(List<Transaction> listOfTransactions1, List<Transaction> listOfTransactions2) {
        return strictComparator.compareTransactionsStrict(listOfTransactions1, listOfTransactions2);
    }

    /**
     * Compares a collection of Transaction with a Transaction for similarity in a fuzzy way using
     * the TransactionComparator class
     */
    public List<Transaction> compareFuzzy(Transaction transaction, List<Transaction> listOfTransactions, String matchingRoutine, int ratio) {
        FuzzyComparatorFactory fuzzyComparatorFactory = new FuzzyComparatorFactory();
        FuzzyComparator fuzzyComparator = fuzzyComparatorFactory.getFuzzyComparator(matchingRoutine);
        return fuzzyComparator.compareTransactionsFuzzy(transaction, listOfTransactions, ratio);
    }
}
