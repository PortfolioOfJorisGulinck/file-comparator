package be.jorisgulinck.filecomparator.utilities;

import be.jorisgulinck.filecomparator.models.Transaction;

import java.util.Comparator;

/**
 * Sorts {@link Transaction} objects by its <i>ratio</i> property.
 */
public class SortByRatio implements Comparator<Transaction> {

    @Override
    public int compare(Transaction a, Transaction b) {
        return Integer.parseInt(a.getRatio()) - Integer.parseInt(b.getRatio());
    }
}
