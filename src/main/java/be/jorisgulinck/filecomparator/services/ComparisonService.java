package be.jorisgulinck.filecomparator.services;

import be.jorisgulinck.filecomparator.comparators.TransactionComparator;
import be.jorisgulinck.filecomparator.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComparisonService {

    private final TransactionComparator transactionComparator;

    public List<Transaction> compareStrict(List<Transaction> listOfTransactions1, List<Transaction> listOfTransactions2) {
        return transactionComparator.compareTransactionsStrict(listOfTransactions1, listOfTransactions2);
    }

    public List<Transaction> compareFuzzy(Transaction transaction, List<Transaction> listOfTransactions) {
        // TODO: implement logic
        return new ArrayList<>();
    }
}
