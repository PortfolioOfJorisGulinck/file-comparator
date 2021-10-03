package be.jorisgulinck.filecomparator.services;

import be.jorisgulinck.filecomparator.comparators.TransactionComparator;
import be.jorisgulinck.filecomparator.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComparisonService {

    private final TransactionComparator transactionComparator;

    /** Compares two collections of Transaction for similarity using the TransactionComparator class */
    public List<Transaction> compareStrict(List<Transaction> listOfTransactions1, List<Transaction> listOfTransactions2) {
        return transactionComparator.compareTransactionsStrict(listOfTransactions1, listOfTransactions2);
    }

    /**
     * Compares a collection of Transaction with a Transaction for similarity in a fuzzy way using
     * the TransactionComparator class
     */
    public List<Transaction> compareFuzzy(Transaction transaction, List<Transaction> listOfTransactions) {
        return transactionComparator.compareTransactionsFuzzy(transaction, listOfTransactions);
    }
}
