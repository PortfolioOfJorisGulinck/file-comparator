package be.jorisgulinck.filecomparator.services;

import be.jorisgulinck.filecomparator.models.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComparisonService {

    public List<Transaction> compareStrict(List<Transaction> listOfTransactions1, List<Transaction> listOfTransactions2) {
        List<Transaction> filteredList = new ArrayList<>();
        // TODO: implement logic
        return filteredList;
    }

    public List<Transaction> compareFuzzy(Transaction transaction, List<Transaction> listOfTransactions) {
        List<Transaction> filteredList = new ArrayList<>();
        // TODO: implement logic
        return filteredList;
    }
}
