package be.jorisgulinck.filecomparator.comparison.strict;

import be.jorisgulinck.filecomparator.models.Transaction;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Compares two collections of the type {@link Transaction} for similarity using the equals method of the {@link Transaction} class
 */
public class StrictEqualsComparator implements StrictComparator {

    @Override
    public List<Transaction> compareTransactionsStrict(List<Transaction> list1, List<Transaction> list2) {
        return new ArrayList<>((CollectionUtils.removeAll(list1, list2)));
    }

}
