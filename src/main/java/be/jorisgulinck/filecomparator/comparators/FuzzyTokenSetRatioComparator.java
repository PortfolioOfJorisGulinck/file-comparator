package be.jorisgulinck.filecomparator.comparators;

import be.jorisgulinck.filecomparator.models.Transaction;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.ArrayList;
import java.util.List;

public class FuzzyTokenSetRatioComparator implements FuzzyComparator{
    @Override
    public List<Transaction> compareTransactionsFuzzy(Transaction transaction, List<Transaction> transactionsToCompare, int ratio) {
        List<Transaction> filteredList = new ArrayList<>();

        for (Transaction transactionToCompare : transactionsToCompare) {
            int idRatio = FuzzySearch.tokenSetRatio(transaction.getTransactionId(), transactionToCompare.getTransactionId());
            int nameRatio = FuzzySearch.tokenSetRatio(transaction.getProfileName(), transactionToCompare.getProfileName());
            int dateRatio = FuzzySearch.tokenSetRatio(transaction.getTransactionDate(), transactionToCompare.getTransactionDate());
            int amountRatio = FuzzySearch.tokenSetRatio(transaction.getTransactionAmount(), transactionToCompare.getTransactionAmount());
            int narrativeRatio = FuzzySearch.tokenSetRatio(transaction.getTransactionNarrative(), transactionToCompare.getTransactionNarrative());
            int descriptionRatio = FuzzySearch.tokenSetRatio(transaction.getTransactionDescription(), transactionToCompare.getTransactionDescription());
            int typeRatio = FuzzySearch.tokenSetRatio(transaction.getTransactionType(), transactionToCompare.getTransactionType());
            int referenceRatio = FuzzySearch.tokenSetRatio(transaction.getWalletReference(), transactionToCompare.getWalletReference());

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
