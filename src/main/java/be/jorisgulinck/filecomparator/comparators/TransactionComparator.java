package be.jorisgulinck.filecomparator.comparators;

import be.jorisgulinck.filecomparator.models.Transaction;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionComparator {

    /**
     * Compares two collections of the type Transaction for similarity using the equals method of the Transaction class
     */
    public List<Transaction> compareTransactionsStrict(List<Transaction> list1, List<Transaction> list2) {

        return new ArrayList<>((CollectionUtils.removeAll(list1, list2)));
    }

    /**
     * Compares a collection of Transaction with a given Transaction for similarity using the
     * Fuzzy string matching for java based on the FuzzyWuzzy Python algorithm.
     * The algorithm uses Levenshtein distance to calculate similarity between strings.
     */
    public List<Transaction> compareTransactionsFuzzy(Transaction transaction, List<Transaction> transactionsToCompare) {
        List<Transaction> filteredList = new ArrayList<>();

        for (Transaction transactionToCompare : transactionsToCompare) {
            int idRatio = FuzzySearch.ratio(transaction.getTransactionId(), transactionToCompare.getTransactionId());
            int nameRatio = FuzzySearch.ratio(transaction.getProfileName(), transactionToCompare.getProfileName());
            int dateRatio = FuzzySearch.ratio(transaction.getTransactionDate(), transactionToCompare.getTransactionDate());
            int amountRatio = FuzzySearch.ratio(transaction.getTransactionAmount(), transactionToCompare.getTransactionAmount());
            int narrativeRatio = FuzzySearch.ratio(transaction.getTransactionNarrative(), transactionToCompare.getTransactionNarrative());
            int descriptionRatio = FuzzySearch.ratio(transaction.getTransactionDescription(), transactionToCompare.getTransactionDescription());
            int typeRatio = FuzzySearch.ratio(transaction.getTransactionType(), transactionToCompare.getTransactionType());
            int referenceRatio = FuzzySearch.ratio(transaction.getWalletReference(), transactionToCompare.getWalletReference());

            int totalRatio = (idRatio + nameRatio + dateRatio + amountRatio + narrativeRatio + descriptionRatio +
                    typeRatio + referenceRatio) / 8;

            if (totalRatio > 80) {
                filteredList.add(transactionToCompare);
            }
        }
        return filteredList;
    }
}
