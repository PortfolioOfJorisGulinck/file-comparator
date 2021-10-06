package be.jorisgulinck.filecomparator.comparators;

import be.jorisgulinck.filecomparator.models.Transaction;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.ArrayList;
import java.util.List;

public class FuzzyTokenSetPartialRatioComparator implements FuzzyComparator{
    @Override
    public List<Transaction> compareTransactionsFuzzy(Transaction transaction, List<Transaction> transactionsToCompare, int ratio) {
        List<Transaction> filteredList = new ArrayList<>();

        for (Transaction transactionToCompare : transactionsToCompare) {
            int idRatio = FuzzySearch.tokenSetPartialRatio(transaction.getTransactionId(), transactionToCompare.getTransactionId());
            int nameRatio = FuzzySearch.tokenSetPartialRatio(transaction.getProfileName(), transactionToCompare.getProfileName());
            int dateRatio = FuzzySearch.tokenSetPartialRatio(transaction.getTransactionDate(), transactionToCompare.getTransactionDate());
            int amountRatio = FuzzySearch.tokenSetPartialRatio(transaction.getTransactionAmount(), transactionToCompare.getTransactionAmount());
            int narrativeRatio = FuzzySearch.tokenSetPartialRatio(transaction.getTransactionNarrative(), transactionToCompare.getTransactionNarrative());
            int descriptionRatio = FuzzySearch.tokenSetPartialRatio(transaction.getTransactionDescription(), transactionToCompare.getTransactionDescription());
            int typeRatio = FuzzySearch.tokenSetPartialRatio(transaction.getTransactionType(), transactionToCompare.getTransactionType());
            int referenceRatio = FuzzySearch.tokenSetPartialRatio(transaction.getWalletReference(), transactionToCompare.getWalletReference());

            int totalRatio = (idRatio + nameRatio + dateRatio + amountRatio + narrativeRatio + descriptionRatio +
                    typeRatio + referenceRatio) / 8;

            transactionToCompare.setRatio(String.valueOf(totalRatio));

            if (totalRatio > ratio) {
                filteredList.add(transactionToCompare);
            }
        }
        return filteredList;
    }
}
