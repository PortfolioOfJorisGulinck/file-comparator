package be.jorisgulinck.filecomparator.comparison.fuzzy;

import be.jorisgulinck.filecomparator.models.Transaction;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Compares a collection of {@link Transaction} with a given {@link Transaction} object for similarity using the FuzzyWuzzy
 * Python algorithm. The Token Sort Ratio function compares two strings by measuring the difference between two sequences. For
 * this it uses the Levenshtein distance algorithm.</p>
 *
 * <p>The problem with the Simple Ratio & Partial Ratio function is that not only inconsistent substrings give an unjustified
 * low score, but we also have to deal with differences in string construction.
 * To get around it, you can use the Token Sort Ratio algorithm. The token sort approach involves tokenizing the string in
 * question, sorting the tokens alphabetically, and then joining them back into a string.</p>
 *
 * <p>The token set approach is similar, but a little bit more flexible. Here, it tokenizes both strings, but instead of
 * immediately sorting and comparing, it splits the tokens into two groups: intersection and remainder. It uses those sets to build up
 * a comparison string. Then it compares the transformed strings with a Partial Ratio.</p>
 */
public class FuzzyTokenSetPartialRatioComparator implements FuzzyComparator {

    protected FuzzyTokenSetPartialRatioComparator() {
    }

    /**
     * Compares a collection of {@link Transaction} with a given {@link Transaction} for similarity using the <i>Token Set
     * Partial Ratio</i> strategy of FuzzyWuzzy.
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
