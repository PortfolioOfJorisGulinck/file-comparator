package be.jorisgulinck.filecomparator.comparison.fuzzy;

import be.jorisgulinck.filecomparator.models.Transaction;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Compares a collection of {@link Transaction} with a given {@link Transaction} for similarity using the FuzzyWuzzy
 * Python algorithm. The Partial Ratio function compares two strings by measuring the difference between two sequences.
 * For this it uses the Levenshtein distance algorithm.</p>
 *
 * <p>The problem with the Simple Ratio function is that inconsistent substrings sometimes give an unjustified low score.
 * To get around it, you can use the Partial Ratio or best partial algorithm, when two strings are of noticeably
 * different lengths.</p>
 */
public class FuzzyPartialRatioComparator implements FuzzyComparator {

    protected FuzzyPartialRatioComparator() {
    }

    /**
     * Compares a collection of {@link Transaction} with a given {@link Transaction} object for similarity using the
     * <i>Partial Ratio</i> strategy of FuzzyWuzzy.
     *
     * @param transaction           Transaction object that compares itself with a given collection of {@link Transaction}.
     * @param transactionsToCompare Collection of {@link Transaction} to be compared with.
     * @param ratio                 The value that determines the precision of the search algorithm. The higher the ratio,
     *                              the scarier the search results.
     * @return Collection of {@link Transaction} objects that meets the matching strategy criteria.
     */
    @Override
    public List<Transaction> compareTransactionsFuzzy(Transaction transaction, List<Transaction> transactionsToCompare, int ratio) {
        List<Transaction> filteredList = new ArrayList<>();

        for (Transaction transactionToCompare : transactionsToCompare) {
            int idRatio = 100;
            if (transactionToCompare.getTransactionId() != null && transaction.getTransactionId() != null){
                idRatio = FuzzySearch.partialRatio(transaction.getTransactionId(), transactionToCompare.getTransactionId());
            }

            int nameRatio = 100;
            if (transactionToCompare.getProfileName() != null && transaction.getProfileName() != null){
                nameRatio = FuzzySearch.partialRatio(transaction.getProfileName(), transactionToCompare.getProfileName());
            }

            int dateRatio = 100;
            if (transactionToCompare.getTransactionDate() != null && transaction.getTransactionDate() != null) {
                dateRatio = FuzzySearch.partialRatio(transaction.getTransactionDate(), transactionToCompare.getTransactionDate());
            }

            int amountRatio = 100;
            if (transactionToCompare.getTransactionAmount() != null && transaction.getTransactionAmount() != null) {
                amountRatio = FuzzySearch.partialRatio(transaction.getTransactionAmount(), transactionToCompare.getTransactionAmount());
            }

            int narrativeRatio = 100;
            if (transactionToCompare.getTransactionNarrative() != null && transaction.getTransactionNarrative() != null) {
                narrativeRatio = FuzzySearch.partialRatio(transaction.getTransactionNarrative(), transactionToCompare.getTransactionNarrative());
            }

            int descriptionRatio = 100;
            if (transactionToCompare.getTransactionDescription() != null && transaction.getTransactionDescription() != null) {
                descriptionRatio = FuzzySearch.partialRatio(transaction.getTransactionDescription(), transactionToCompare.getTransactionDescription());
            }

            int typeRatio = 100;
            if (transactionToCompare.getTransactionType() != null && transaction.getTransactionType() != null) {
                typeRatio = FuzzySearch.partialRatio(transaction.getTransactionType(), transactionToCompare.getTransactionType());
            }

            int referenceRatio = 100;
            if (transactionToCompare.getWalletReference() != null && transaction.getWalletReference() != null) {
                referenceRatio = FuzzySearch.partialRatio(transaction.getWalletReference(), transactionToCompare.getWalletReference());
            }

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
